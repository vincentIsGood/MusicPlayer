package com.vincentcodes.vincentplayer.commands.defaults.system;

import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandRemoveAllSongs implements Command {
    @Handler
    public void onRemoveAllSongs(CommandEvent event){
        //TODO
    }

    @Override
    public String getName() {
        return "removeall";
    }

    @Override
    public String getUsage() {
        return "removeall";
    }

    @Override
    public String getDescription() {
        return "Remove all songs from the player";
    }

}