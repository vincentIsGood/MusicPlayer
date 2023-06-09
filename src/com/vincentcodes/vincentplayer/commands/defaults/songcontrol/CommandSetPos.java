package com.vincentcodes.vincentplayer.commands.defaults.songcontrol;

import com.vincentcodes.vincentplayer.Song;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandSetPos implements Command {
    @Handler
    public void onSetPos(CommandEvent event){
        Song song = event.getPlayer().getState().getCurrentSong();
        if(song != null && event.hasMultipleArgs())
            song.setPosSec(Integer.parseInt(event.getParsedCommand()[1]));
    }

    @Override
    public String getName() {
        return "setpos";
    }

    @Override
    public String getUsage() {
        return "setpos";
    }

    @Override
    public String getDescription() {
        return "Which part of the song do you want to play? Put it in seconds";
    }

}