package com.vincentcodes.vincentplayer.commands.defaults.songcontrol;

import com.vincentcodes.vincentplayer.Song;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandStop implements Command {
    @Handler
    public void onStop(CommandEvent event){
        Song song = event.getPlayer().getState().getCurrentSong();
        if(song != null)
            song.stop();
    }

    @Override
    public String getDescription() {
        return "Stop the current song";
    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getUsage() {
        return "stop";
    }

}