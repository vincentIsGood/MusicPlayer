package com.vincentcodes.vincentplayer.commands.defaults.playerstate;

import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandShowSongIsActive implements Command {
    @Handler
    public void onShowSongIsActive(CommandEvent event){
        VincentPlayer.logger.println("Is Song Playing: " + event.getPlayer().getState().getCurrentSong().isActive());
    }

    @Override
    public String getName() {
        return "isactive";
    }

    @Override
    public String getUsage() {
        return "isactive";
    }

    @Override
    public String getDescription() {
        return "Show whether the current song is active or not";
    }

}