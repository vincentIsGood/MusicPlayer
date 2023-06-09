package com.vincentcodes.vincentplayer.streaming.udp;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vincentcodes.io.UdpServerSocket;
import com.vincentcodes.io.UdpSocket;
import com.vincentcodes.vincentplayer.Song;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.streaming.StreamingDevice;

/**
 * Runs on UDP and it requires other people to connect to you. 
 * StreamingDevice must be closed manually by {@link #close()}
 */
public class UdpStreamingDevice extends StreamingDevice{
    private VincentPlayer player;
    private UdpServerSocket server;
    private List<UdpSocket> ongoingConnections;
    
    public UdpStreamingDevice(VincentPlayer player, int port) throws SocketException{
        this.player = player;
        server = new UdpServerSocket(port);
        ongoingConnections = new ArrayList<>();
    }

    @Override
    public void run(){
        if(player.getState().getCurrentSong() == null){
            close();
            VincentPlayer.logger.err("You do not have any songs loaded, closing the stream");
            return;
        }

        VincentPlayer.logger.warn("Start streaming on udp port " + server.getLocalPort());
        server.startListening();
        initWorker();
        try{
            // Accept connections
            UdpSocket socket;
            while((socket = server.accept()) != null){
                VincentPlayer.logger.log("Connection from: " + socket.getRemoteFullAddr());
                socket.recv(); // client will send "Hello"
                ongoingConnections.add(socket);
            }
        }catch(IOException e){
            e.printStackTrace();
            VincentPlayer.logger.err("Error occured, closing the stream");
        }
        return;
    }

    private void initWorker(){
        new Thread("Bytes sender"){
            public void run(){
                Song song = null;
                boolean wholeSongSent = false;
                VincentPlayer.State state = player.getState();
                while(!server.isClosed()){
                    try{
                        // sent != finished playing on your pc
                        while(wholeSongSent){
                            // Wait for the song to end
                            while(song.isFinished()){
                                if(state.isRepeatforever())
                                    break;
                                // wait for the next song (it should give false on isFinished())
                                song = state.getCurrentSong();
                                // TODO: I should do something like onNewSongPlay event listeners or some sort
                                Thread.sleep(1000);
                            }
                            song = state.getCurrentSong();
                            if(song.isReady() && song.getPosition() < 10){
                                wholeSongSent = false;
                                break;
                            }
                            Thread.sleep(1000);
                        }

                        song = state.getCurrentSong();
                        song.replay();
                        byte[] rawAudio = song.getRawAudio();

                        int byteLen = song.getByteIndexFromSec(0.2);
                        int bytesSent = 0;
                        // wait for the audio to be prepared
                        while((rawAudio = song.getRawAudio()) == null) {
                            Thread.sleep(2000);
                        }

                        // TODO: create event dispatcher to dispatch command events to streaming
                        // TODO: device so that the client has the same effects? eg. setPos
                        VincentPlayer.logger.warn("Streaming song: " + song.getName());
                        while(!server.isClosed()){
                            if(song.isFinished()){
                                // just in case someone tries to end the song early
                                wholeSongSent = true;
                                break;
                            }
                            if(song.isActive()){
                                if(bytesSent + byteLen > rawAudio.length){
                                    sendToAllSockets(Arrays.copyOfRange(rawAudio, bytesSent, rawAudio.length));
                                    wholeSongSent = true;
                                    break;
                                }else{
                                    byte[] payload = Arrays.copyOfRange(rawAudio, bytesSent, bytesSent + byteLen);
                                    sendToAllSockets(payload);
                                    bytesSent += byteLen;
                                }
                            }
                            Thread.sleep(150);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        VincentPlayer.logger.err("Thread interrupt detected stopping the stream");
                        close();
                    }
                }
                return;
            }

            public void sendToAllSockets(byte[] payload){
                for(int i = ongoingConnections.size()-1; i >= 0; i--){
                    UdpSocket socket = ongoingConnections.get(i);
                    try{
                        socket.send(payload);
                    }catch(IOException e){
                        VincentPlayer.logger.err("Error occured while sending data, trying to close the involved connection");
                        try{
                            socket.close();
                            VincentPlayer.logger.warn("Disconnecting " + socket.getRemoteFullAddr());
                            ongoingConnections.remove(i);
                        }catch(IOException ex){
                            ex.printStackTrace();
                            VincentPlayer.logger.warn("Error occured while closing the connection");
                        }
                    }
                }
            }
        }.start();
    }
    
    public void close(){
        server.close();
    }
}
