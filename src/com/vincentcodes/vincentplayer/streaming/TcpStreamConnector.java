package com.vincentcodes.vincentplayer.streaming;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import com.vincentcodes.vincentplayer.VincentPlayer;

public class TcpStreamConnector extends StreamConnector{
    private Socket socket;
    private SourceDataLine currentDataLine = null;

    public TcpStreamConnector(String ip, int port) throws IOException{
        socket = new Socket(ip, port);
    }

    @Override
    public void run(){
        try{
            // TODO: implement a lock to disable some commands
            VincentPlayer.logger.warn("Connecting to " + socket.getRemoteSocketAddress().toString() + " via tcp");
            VincentPlayer.logger.warn("You should not play another song while you are in the stream");
            VincentPlayer.logger.warn("A command lock will be introduced later to prevent you from doing so");

            byte[] buf = new byte[1024];
            InputStream os = socket.getInputStream();
            int bytesRead = 0;
            while((bytesRead = os.read(buf)) != -1){
                if(currentDataLine == null){
                    //TODO: send format through the connection as well
                    AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                    currentDataLine = (SourceDataLine) AudioSystem.getLine(info);
                    currentDataLine.open();
                    currentDataLine.start();
                }
                currentDataLine.write(buf, 0, bytesRead + bytesRead % 4);
            }
        }catch(IOException | LineUnavailableException e){
            e.printStackTrace();
            VincentPlayer.logger.err("Error occured, disconnecting from the stream");
        }
        return;
    }

    public void close(){
        if(currentDataLine != null)
            currentDataLine.close();
        try{
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
