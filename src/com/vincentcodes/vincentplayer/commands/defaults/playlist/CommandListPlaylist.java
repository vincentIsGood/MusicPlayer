package com.vincentcodes.vincentplayer.commands.defaults.playlist;

import java.util.List;

import com.vincentcodes.vincentplayer.Playlist;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandListPlaylist implements Command {
    @Handler
    public void onListPlaylist(CommandEvent event){
        List<Playlist> playlists = event.getPlayer().getPlaylists();
        for(int i = 0; i < playlists.size(); i++){
            VincentPlayer.logger.println(playlists.get(i).toString());
        }
    }

    @Override
    public String getName() {
        return "listplaylist";
    }

    @Override
    public String getUsage() {
        return "listplaylist";
    }

    @Override
    public String getDescription() {
        return "Show all the playlists created";
    }

}