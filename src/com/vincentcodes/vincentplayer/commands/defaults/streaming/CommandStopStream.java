package com.vincentcodes.vincentplayer.commands.defaults.streaming;

import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandStopStream implements Command{
    
    @Handler
    public void onStopStream(CommandEvent e){
        VincentPlayer.State state = e.getPlayer().getState();
        if(state.getStreamConnector() != null){
            VincentPlayer.logger.warn("You are connected to a stream, disconnecting...");
            state.getStreamConnector().close();
            state.setStreamConnector(null);
        }
        if(state.getStreamingDevice() != null){
            VincentPlayer.logger.warn("Streaming device is on, stopping it.");
            state.getStreamingDevice().close();
            state.setStreamingDevice(null);
        }
    }

    @Override
    public String getName() {
        return "stopstream";
    }

    @Override
    public String getUsage() {
        return "stopstream";
    }

    @Override
    public String getDescription() {
        return "Stops all streaming related operations";
    }
    
}
