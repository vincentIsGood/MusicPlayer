package com.vincentcodes.vincentplayer.commands.defaults.playerstate;

import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandShowName implements Command {
    @Handler
    public void onShowName(CommandEvent event){
        VincentPlayer.logger.println(event.getPlayer().getState().getCurrentSong().getName());
    }

    @Override
    public String getDescription() {
        return "Show the current song name";
    }

    @Override
    public String getName() {
        return "name";
    }

    @Override
    public String getUsage() {
        return "name";
    }
    
}