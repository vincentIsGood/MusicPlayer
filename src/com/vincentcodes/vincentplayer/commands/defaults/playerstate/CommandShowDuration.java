package com.vincentcodes.vincentplayer.commands.defaults.playerstate;

import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandShowDuration implements Command {
    @Handler
    public void onShowDuration(CommandEvent event){
        VincentPlayer.logger.println("Song duration(sec): " + event.getPlayer().getState().getCurrentSong().getDuration());
    }

    @Override
    public String getName() {
        return "duration";
    }

    @Override
    public String getUsage() {
        return "duration";
    }

    @Override
    public String getDescription() {
        return "Show the duration of the current song in seconds";
    }

}