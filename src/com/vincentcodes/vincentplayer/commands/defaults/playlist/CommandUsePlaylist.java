package com.vincentcodes.vincentplayer.commands.defaults.playlist;

import java.util.List;

import com.vincentcodes.vincentplayer.Playlist;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandUsePlaylist implements Command {
    @Handler
    public void onUsePlaylist(CommandEvent event){
        VincentPlayer player = event.getPlayer();
        VincentPlayer.State state = player.getState();
        boolean usePlaylist = state.isUsePlaylist();
        List<Playlist> playlists = player.getPlaylists();
        if(usePlaylist || playlists.size() == 0){
            if(playlists.size() == 0)
                VincentPlayer.logger.println("No playlist exists!", "warn");
            usePlaylist = false; 
        }else 
            usePlaylist = true; 
        
        state.setUsePlaylist(usePlaylist);
        VincentPlayer.logger.println("Use playlist: " + state.isUsePlaylist());
    }

    @Override
    public String getName() {
        return "useplaylist";
    }

    @Override
    public String getUsage() {
        return "useplaylist";
    }

    @Override
    public String getDescription() {
        return "Toggle whether you want to use a playlist or play songs randomly";
    }

}