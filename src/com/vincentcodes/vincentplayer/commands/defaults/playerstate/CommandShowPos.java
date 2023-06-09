package com.vincentcodes.vincentplayer.commands.defaults.playerstate;

import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandShowPos implements Command {
    @Handler
    public void onShowPos(CommandEvent event){
        VincentPlayer.logger.println("Time lapsed(sec): " + event.getPlayer().getState().getCurrentSong().getPosition());
    }

    @Override
    public String getName() {
        return "pos";
    }

    @Override
    public String getUsage() {
        return "pos";
    }

    @Override
    public String getDescription() {
        return "Show the current position in a song";
    }
    
}