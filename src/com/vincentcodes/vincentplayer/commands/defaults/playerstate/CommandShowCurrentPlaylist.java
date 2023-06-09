package com.vincentcodes.vincentplayer.commands.defaults.playerstate;

import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandShowCurrentPlaylist implements Command {
    @Handler
    public void onShowCurrentPlaylist(CommandEvent event){
        VincentPlayer.State state = event.getPlayer().getState();
        if(!state.isUsePlaylist())
            VincentPlayer.logger.println("useplaylist function is off! Type: 'useplaylist' to enable it.", "warn");
        VincentPlayer.logger.println("Current playlist: " + state.getCurrentPlaylist().toString());
    }

    @Override
    public String getName() {
        return "currentplaylist";
    }

    @Override
    public String getUsage() {
        return "currentplaylist";
    }

    @Override
    public String getDescription() {
        return "Show the current playlist";
    }

}