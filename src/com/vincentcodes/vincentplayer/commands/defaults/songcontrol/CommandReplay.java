package com.vincentcodes.vincentplayer.commands.defaults.songcontrol;

import com.vincentcodes.vincentplayer.Song;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandReplay implements Command {
    @Handler
    public void onReplay(CommandEvent event){
        Song song = event.getPlayer().getState().getCurrentSong();
        if(song != null)
            song.replay();
    }

    @Override
    public String getDescription() {
        return "Play current song once again";
    }

    @Override
    public String getName() {
        return "replay";
    }

    @Override
    public String getUsage() {
        return "replay";
    }
    
}