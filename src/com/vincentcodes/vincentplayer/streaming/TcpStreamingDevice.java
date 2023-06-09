package com.vincentcodes.vincentplayer.streaming;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vincentcodes.vincentplayer.Song;
import com.vincentcodes.vincentplayer.VincentPlayer;

public class TcpStreamingDevice extends StreamingDevice{
    private VincentPlayer player;
    private ServerSocket server;
    private List<Socket> ongoingConnections;
    
    public TcpStreamingDevice(VincentPlayer player, int port) throws IOException{
        this.player = player;
        server = new ServerSocket(port);
        ongoingConnections = new ArrayList<>();
    }

    @Override
    public void run(){
        if(player.getState().getCurrentSong() == null){
            close();
            VincentPlayer.logger.err("You do not have any songs loaded, closing the stream");
            return;
        }

        VincentPlayer.logger.warn("Start streaming on tcp port " + server.getLocalPort());
        initWorker();
        try{
            // Accept connections
            Socket socket;
            while((socket = server.accept()) != null){
                VincentPlayer.logger.log("Connection from: " + socket.getRemoteSocketAddress().toString());
                ongoingConnections.add(socket);
            }
        }catch(IOException e){
            VincentPlayer.logger.err("Error occured, server is closed");
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
                    }catch(InterruptedException e){
                        e.printStackTrace();
                        VincentPlayer.logger.err("Thread interrupt detected stopping the stream");
                        close();
                    }
                }
                return;
            }

            public void sendToAllSockets(byte[] payload){
                for(int i = ongoingConnections.size()-1; i >= 0; i--){
                    Socket socket = ongoingConnections.get(i);
                    try{
                        OutputStream os = socket.getOutputStream();
                        os.write(payload);
                    }catch(IOException e){
                        VincentPlayer.logger.err("Error occured while sending data, trying to close the involved connection");
                        try{
                            socket.close();
                            VincentPlayer.logger.warn("Disconnecting " + socket.getRemoteSocketAddress().toString());
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
        try{
            server.close();
        }catch(IOException e){
            e.printStackTrace();
            VincentPlayer.logger.err("Error occured while trying to close server");
        }
    }
}
