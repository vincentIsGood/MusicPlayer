package com.vincentcodes.vincentplayer.commands.defaults.playerstate;

import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandShowSongIsFinished implements Command {
    @Handler
    public void onShowSongIsFinished(CommandEvent event){
        VincentPlayer.logger.println("Is song Finished: " + event.getPlayer().getState().getCurrentSong().isFinished());
    }

    @Override
    public String getName() {
        return "isfinished";
    }

    @Override
    public String getUsage() {
        return "isfinished";
    }

    @Override
    public String getDescription() {
        return "Show the whether the current song is finished or not";
    }

}