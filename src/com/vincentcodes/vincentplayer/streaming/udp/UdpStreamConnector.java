package com.vincentcodes.vincentplayer.streaming.udp;

import java.io.IOException;
import java.net.SocketException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import com.vincentcodes.io.UdpSocket;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.streaming.StreamConnector;

public class UdpStreamConnector extends StreamConnector{
    private UdpSocket socket;
    private SourceDataLine currentDataLine = null;

    public UdpStreamConnector(String ip, int port) throws SocketException{
        socket = UdpSocket.create(ip, port);
    }

    @Override
    public void run(){
        try{
            // TODO: implement a lock to disable some commands
            VincentPlayer.logger.warn("Connecting to " + socket.getRemoteFullAddr() + " via udp");
            VincentPlayer.logger.warn("You should not play another song while you are in the stream");
            VincentPlayer.logger.warn("A command lock will be introduced later to prevent you from doing so");
            socket.send("Hello".getBytes());
            System.out.println("Starting");

            byte[] buf = null;
            while(!socket.isClosed()){
                buf = socket.recv();
                if(currentDataLine == null){
                    //TODO: send format through the connection as well
                    AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                    currentDataLine = (SourceDataLine) AudioSystem.getLine(info);
                    currentDataLine.open();
                    currentDataLine.start();
                }
                currentDataLine.write(buf, 0, buf.length + buf.length % 4);
            }
            System.out.println("Connection is closed");
        }catch(IOException | LineUnavailableException e){
            e.printStackTrace();
            VincentPlayer.logger.err("Error occured, disconnecting from the stream");
        }
        return;
    }

    public void close(){
        currentDataLine.close();
        try{
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
