package com.vincentcodes.vincentplayer.commands.defaults.streaming;

import java.io.IOException;

import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;
import com.vincentcodes.vincentplayer.streaming.StreamConnector;
import com.vincentcodes.vincentplayer.streaming.TcpStreamConnector;
import com.vincentcodes.vincentplayer.streaming.udp.UdpStreamConnector;

public class CommandConnectStream implements Command {
    @Handler
    public void onConnectStream(CommandEvent e){
        if(!e.hasMultipleArgs()) return;
        VincentPlayer.State state = e.getPlayer().getState();

        String[] args = e.getParsedCommand();
        String ip = null;
        String protocol = VincentPlayer.DEFAULT_STREAMING_PROTOCOL;
        int port = VincentPlayer.DEFAULT_STREAMING_PORT;
        if(args.length == 2){
            if(args[1].contains(":")){
                String[] splited = args[1].split(":");
                protocol = splited[0];
                ip = splited[1];
            }else
                ip = args[1];
        }
        if(args.length == 3){
            port = Integer.parseInt(args[2]);
        }

        try{
            if(state.getStreamConnector() != null){
                VincentPlayer.logger.warn("Previous stream connection is not closed, closing...");
                state.getStreamConnector().close();
            }
            StreamConnector streamConnector = null;
            if(protocol.equals("udp")) streamConnector = new UdpStreamConnector(ip, port);
            else streamConnector = new TcpStreamConnector(ip, port);
            streamConnector.start();
            VincentPlayer.logger.log("Attempting to connect to streaming device " + makeIpString(protocol, ip, port));
            state.setStreamConnector(streamConnector);
        }catch(IOException ex){
            ex.printStackTrace();
            VincentPlayer.logger.err("Cannot connect to streaming device: " + makeIpString(protocol, ip, port));
        }
    }
    private String makeIpString(String protocol, String ip, int port){
        return  protocol + ":" + ip + ":" + port;
    }

    @Override
    public String getName() {
        return "connectstream";
    }

    @Override
    public String getUsage() {
        return "connectstream <ip> [<port>]";
    }

    @Override
    public String getDescription() {
        return "Connect to a stream. The default port is 12500. Note that <ip> can be 'tcp:127.0.0.1' or '127.0.0.1' (defaults to tcp) or 'udp:127.0.0.1'";
    }
    
}
