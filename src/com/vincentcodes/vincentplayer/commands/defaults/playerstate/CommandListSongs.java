package com.vincentcodes.vincentplayer.commands.defaults.playerstate;

import java.util.List;

import com.vincentcodes.vincentplayer.Song;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandListSongs implements Command {
    @Handler
    public void onListSongs(CommandEvent event){
        VincentPlayer player = event.getPlayer();
        List<Song> songs = player.getSongs();
        for(int i = 0; i < songs.size(); i++){
            VincentPlayer.logger.println(songs.get(i).toString());
        }
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getUsage() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "Show all the songs that are imported into the music player";
    }

}