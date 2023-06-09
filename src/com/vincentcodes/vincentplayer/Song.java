package com.vincentcodes.vincentplayer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import com.vincentcodes.vincentplayer.exceptions.SongNotSupportedException;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

public class Song{
    private static final int MICRO_TO_ONE_SEC = 1000 * 1000;

    private final int songId;

    private FloatControl volControl;
    private AudioFormat format;
    private File file;

    // Ref: https://stackoverflow.com/questions/21874361/clip-plays-wav-file-with-bad-lag-in-java
    // SourceDataLine, and it will start much faster than the Clip will for larger files
    private SourceDataLine lineIn;
    private byte[] rawAudio;
    private double durationInSec = -1;
    private int songPuasedAtSec = 0;
    
    public Song(String pathToSong) throws SongNotSupportedException{
        this.songId = VincentPlayer.ID_COUNTER.getNextSongId();
        this.file = new File(pathToSong);
        if(!this.file.exists()){
            throw new SongNotSupportedException("Song file not found: " + pathToSong);
        }
        
        //initialize early costs a lot of memory
    }
    
    /**
     * This is a blocking operation. Blocks until the whole song is 
     * loaded being converted into wav
     */
    private SourceDataLine getFilledSourceDataLine(float volume) throws LineUnavailableException, IOException{
        if(isReady())
            return this.lineIn;
        
        if(rawAudioFound()){
            SourceDataLine newLineIn = getEmptySourceDataLine(volume);
            new Thread(){
                public void run(){
                    newLineIn.write(rawAudio, 0, rawAudio.length);
                    return;
                }
            }.start();
            return newLineIn;
        }
        
        PipedInputStream pipedInputStream = new PipedInputStream();
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(this.file);
        grabber.start();

        //Set the sample rate according to the original file
        this.format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, grabber.getSampleRate(), 16, 2, 4, grabber.getSampleRate(), false);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        SourceDataLine newLineIn = (SourceDataLine) AudioSystem.getLine(info);

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(new PipedOutputStream(pipedInputStream), grabber.getAudioChannels());
        recorder.setSampleRate(grabber.getSampleRate());
        recorder.setAudioChannels(grabber.getAudioChannels());
        recorder.setAudioBitrate(grabber.getAudioBitrate());
        recorder.setFormat("wav");
        recorder.start();
        
        //Gonna wait for the bytes to come and play it
        new Thread("Audio loading"){
            public void run(){
                try{
                    // for the time being... readAllBytes() is used.
                    //pipedInputStream.readAllBytes(); gonna read until all incoming bytes are done. (waiting for EOF) Then, it returns the value
                    byte[] allBytes = pipedInputStream.readAllBytes();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    baos.write(allBytes);
                    baos.write(new byte[allBytes.length % 4]); // Padding; 4 bytes per frame
                    rawAudio = baos.toByteArray();

                    // Use clip to get duration only
                    Clip clip = AudioSystem.getClip();
                    clip.open(format, rawAudio, 0, rawAudio.length);
                    // Math.round(*1000) / 1000 == round to 3 significant digits
                    durationInSec = Math.round((double)clip.getMicrosecondLength()/1000/1000 * 1000) / 1000;
                    clip.close();

                    newLineIn.open();
                    newLineIn.start();

                    volControl = (FloatControl)newLineIn.getControl(FloatControl.Type.MASTER_GAIN);
                    volControl.setValue(volume);

                    newLineIn.write(rawAudio, 0, rawAudio.length);
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    try{
                        pipedInputStream.close();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
                return;
            }
        }.start();
        
        try{
            Frame frame;
            //boolean doAudio, boolean doVideo, boolean doProcessing, boolean keyFrames
            while((frame = grabber.grabSamples()) != null){
                recorder.record(frame);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            //System.out.println("STOPPING grabber AND recorder");
            grabber.stop();
            recorder.stop();
            //System.out.println("CLOSING grabber AND recorder");
            grabber.close();
            recorder.close();
        }
        return newLineIn;
    }
    /**
     * You need to write that SourceDataLine yourself
     * @param volume the initial volume
     * @return
     */
    private SourceDataLine getEmptySourceDataLine(float volume) throws LineUnavailableException{
        // if(rawAudioFound()){
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        SourceDataLine newLineIn = (SourceDataLine) AudioSystem.getLine(info);
        newLineIn.open();
        newLineIn.start();

        volControl = (FloatControl)newLineIn.getControl(FloatControl.Type.MASTER_GAIN);
        volControl.setValue(volume);

        return newLineIn;
    }
    
    // This is called "seek" in media terms
    public void setPosSec(int sec){
        if(sec > this.getDuration() || sec < 0)
            VincentPlayer.logger.println("Input invalid: "+ sec +"(s) exceeds limit");
        else{
            int pos = getByteIndexFromSec(sec);

            try{
                // VincentPlayer.logger.warn("pos/len: " + pos + "/" + rawAudio.length);
                reset();
                songPuasedAtSec = sec;
                this.lineIn = getEmptySourceDataLine(VincentPlayer.State.volume);
                new Thread(){
                    public void run(){
                        byte[] result = Arrays.copyOfRange(rawAudio, pos, rawAudio.length-rawAudio.length%4);
                        lineIn.write(result, 0, result.length);
                        // lineIn.write(rawAudio, pos, rawAudio.length-pos-(rawAudio.length-pos)%4);
                        return;
                    }
                }.start();
            }catch(LineUnavailableException e){
                e.printStackTrace();
            }
        }
    }

    public int getByteIndexFromSec(double sec){
        AudioFormat format = lineIn.getFormat();
        double rate = format.getSampleRate(); // samples per sec
        double size = format.getSampleSizeInBits(); // bits per sample
        // 
        // Seconds to Byte position:
        // Ref: https://stackoverflow.com/questions/32480602/seconds-to-byte-position-in-audio-file
        // 
        // Frame vs Sample
        // Ref: https://sound.stackexchange.com/questions/41567/difference-between-frame-and-sample-in-waveform
        // 
        // Basic concepts for audio
        // https://developer.mozilla.org/en-US/docs/Web/API/Web_Audio_API/Basic_concepts_behind_Web_Audio_API
        int frameNumber = (int)Math.floor(sec * rate);
        return frameNumber * format.getChannels() * (int)(size/8);
    }

    public void setVolume(float volume){
        if(this.volControl == null)
            this.volControl = (FloatControl)this.lineIn.getControl(FloatControl.Type.MASTER_GAIN);
        
        //6.0206 is max, -80 is min
        if(volume > 6.0206f)
            volume = 6.0206f;
        else if(volume < -80)
            volume = -80;
        
        this.volControl.setValue(volume);
    }

    public AudioFormat getFormat(){
        return format;
    }

    public byte[] getRawAudio(){
        return rawAudio;
    }

    public File getFile(){
        return file;
    }
    
    public String getName(){
        return file.getName();
    }
    
    /**
     * @return in sec
     */
    public double getDuration(){
        return durationInSec;
    }
    
    /**
     * @return time lapsed in sec; -1 if the song is not ready
     */
    public double getPosition(){
        if(!isReady())
            return -1;
        return songPuasedAtSec + ((double)this.lineIn.getMicrosecondPosition())/MICRO_TO_ONE_SEC;
    }
    
    public double getVolume(){
        if(!isReady())
            return VincentPlayer.State.volume;
        return this.volControl.getValue();
    }
    
    public long getMicroSecDuration(){
        if(!isReady()) 
            return -1;
        return (long)(durationInSec * MICRO_TO_ONE_SEC);
    }
    
    public long getMicroSecPosition(){
        return songPuasedAtSec * MICRO_TO_ONE_SEC + this.lineIn.getMicrosecondPosition();
    }

    public int getSongId(){
        return songId;
    }
    
    public boolean isFinished(){
        return isReady() && (Math.ceil(getMicroSecPosition()) >= getMicroSecDuration());
    }
    
    public boolean isActive(){
        return isReady() && this.lineIn.isActive();
    }

    public boolean isOpen(){
        return isReady() && this.lineIn.isOpen();
    }
    
    public boolean isReady(){
        return this.lineIn != null;
    }

    public boolean rawAudioFound(){
        return this.rawAudio != null;
    }
    // End of setters and getters //

    public void play(){
        this.play(VincentPlayer.State.volume);
    }
    
    public void play(float volume){
        try{
            if(!isReady()){
                this.lineIn = getFilledSourceDataLine(volume);
                return;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        if(isReady()){
            if(isOpen()){
                setVolume(volume);

                // this.lineIn.start(); // not as simple when it comes to SourceDataLine
                if(!isActive()){
                    resume();
                }
            }else{
                VincentPlayer.logger.warn("This audio is not opened");
            }
        }else{
            VincentPlayer.logger.warn("This audio is not ready");
        }
    }

    public void resume(){
        setPosSec(songPuasedAtSec);
    }
    
    public void stop(){
        if(isActive()){
            songPuasedAtSec += (int)(getMicroSecPosition()/MICRO_TO_ONE_SEC);
            this.lineIn.stop();
        }
    }
    
    public void reset(){
        stop();
        simpleClose();
    }
    
    public void replay(){
        reset();
        play();
    }
    
    /**
     * Closes source data line (but still preserves raw audio)
     */
    public void simpleClose(){
        try{
            if(this.lineIn != null)
                this.lineIn.close();
            this.volControl = null;
            this.lineIn = null;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Deletes raw audio and format. Requires re-converting the
     * audio from one format to wav format.
     */
    public void fullClose(){
        simpleClose();
        this.rawAudio = null;
        this.durationInSec = -1;
        this.songPuasedAtSec = -1;
        this.format = null;

        //recommend the JVM to gc (this can free the memory that is not referenced/used any more)
        System.gc();
    }
    
    // TODO: improve this
    public String toString(){
        // if(VincentPlayer.VAR_OS.contains("win")){
        //     return songId + " " + getName();
        // }
        return getName();
    }
}