package com.vincentcodes.vincentplayer.commands.defaults.playlist;

import com.vincentcodes.vincentplayer.Playlist;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandShufflePlaylist implements Command {
    @Handler
    public void onShufflePlaylist(CommandEvent event){
        Playlist currentPlaylist = event.getPlayer().getState().getCurrentPlaylist();
        if(currentPlaylist != null){
            VincentPlayer.logger.println("Shuffling this("+ currentPlaylist.toString() +") playlist");
            currentPlaylist.shuffle();
        }else
            VincentPlayer.logger.println("No playlist is created", "warn");
    }

    @Override
    public String getName() {
        return "shuffleplaylist";
    }

    @Override
    public String getUsage() {
        return "shuffleplaylist";
    }

    @Override
    public String getDescription() {
        return "Shuffle the current playlist";
    }
    
}