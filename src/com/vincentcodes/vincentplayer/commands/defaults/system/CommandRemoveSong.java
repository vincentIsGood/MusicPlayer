package com.vincentcodes.vincentplayer.commands.defaults.system;

import java.util.List;

import com.vincentcodes.vincentplayer.Song;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandRemoveSong implements Command {
    private List<Song> songs;

    @Handler
    public void onRemoveSong(CommandEvent event){
        songs = event.getPlayer().getSongs();
        
        if(event.hasMultipleArgs())
            removeSong(event.getParsedCommand()[1]);
    }

    public void removeSong(String songName){
        for(int i = 0; i < songs.size(); i++){
            if(songs.get(i).getName().startsWith(songName)){
                VincentPlayer.logger.println("Removing " + songs.get(i));
                songs.get(i).fullClose();
                songs.remove(i);
                break;
            }
        }
    }

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getUsage() {
        return "remove <song name>";
    }

    @Override
    public String getDescription() {
        return "Remove a song from music player";
    }

}