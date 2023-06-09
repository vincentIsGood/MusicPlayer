package com.vincentcodes.vincentplayer.commands.defaults.streaming;

import java.io.IOException;

import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;
import com.vincentcodes.vincentplayer.streaming.StreamingDevice;
import com.vincentcodes.vincentplayer.streaming.TcpStreamingDevice;
import com.vincentcodes.vincentplayer.streaming.udp.UdpStreamingDevice;

public class CommandHostStream implements Command {
    @Handler
    public void onHostStream(CommandEvent e){
        int port = VincentPlayer.DEFAULT_STREAMING_PORT;
        String protocol = VincentPlayer.DEFAULT_STREAMING_PROTOCOL;

        VincentPlayer player = e.getPlayer();
        VincentPlayer.State state = player.getState();
        if(state.getStreamingDevice() != null){
            VincentPlayer.logger.warn("You have started a stream already, closing it...");
            state.getStreamingDevice().close();
        }

        if(!e.hasMultipleArgs()){
            try{
                StreamingDevice streamingDevice = new TcpStreamingDevice(player, port);
                streamingDevice.start();
                state.setStreamingDevice(streamingDevice);
            }catch(IOException ex){
                ex.printStackTrace();
                VincentPlayer.logger.err("Cannot start the stream on port " + port);
            }
            return;
        }

        String[] args = e.getParsedCommand();
        if(args.length == 2)
            port = Integer.parseInt(args[1]);
        if(args.length == 3)
            protocol = args[2];
        try{
            StreamingDevice streamingDevice = null;
            if(protocol.equals("udp")) streamingDevice = new UdpStreamingDevice(player, port);
            else streamingDevice = new TcpStreamingDevice(player, port);
            streamingDevice.start();
            state.setStreamingDevice(streamingDevice);
        }catch(IOException ex){
            ex.printStackTrace();
            VincentPlayer.logger.err("Cannot start the stream on port " + port);
        }
    }

    @Override
    public String getName() {
        return "startstream";
    }

    @Override
    public String getUsage() {
        return "startstream [<port> <tcp/udp>]";
    }

    @Override
    public String getDescription() {
        return "Start a stream. The stream listens for tcp or udp (defaults to tcp) connections on the specified port or default port 12500";
    }
    
}
