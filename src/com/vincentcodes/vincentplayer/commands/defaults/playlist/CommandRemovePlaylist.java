package com.vincentcodes.vincentplayer.commands.defaults.playlist;

import java.util.List;

import com.vincentcodes.vincentplayer.CommandUtil;
import com.vincentcodes.vincentplayer.Playlist;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandRemovePlaylist implements Command {
    @Handler
    public void onRemovePlaylist(CommandEvent event){
        VincentPlayer player = event.getPlayer();
        if(event.hasMultipleArgs())
            removePlaylist(CommandUtil.autoCompleteName(player.getPlaylists(), event.getParsedCommand()[1]), player);
    }

    public void removePlaylist(String name, VincentPlayer player){
        //remember that for(a : array) uses concurrent - it thorws an exception - to iterate through the array.
        //It's not suitable to use it to delete elements at the same time.
        List<Playlist> playlists = player.getPlaylists();
        for(int i = 0; i < playlists.size(); i++){
            if(playlists.get(i).getName().equals(name))
                playlists.remove(playlists.get(i));
        }
    }

    @Override
    public String getName() {
        return "removeplaylist";
    }

    @Override
    public String getUsage() {
        return "removeplaylist <name>";
    }

    @Override
    public String getDescription() {
        return "Remove a playlist";
    }

}