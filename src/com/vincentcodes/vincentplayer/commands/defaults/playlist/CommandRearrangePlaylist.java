package com.vincentcodes.vincentplayer.commands.defaults.playlist;

import com.vincentcodes.vincentplayer.Playlist;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandRearrangePlaylist implements Command {
    @Handler
    public void onRearrangePlaylist(CommandEvent event){
        Playlist currentPlaylist = event.getPlayer().getState().getCurrentPlaylist();
        if(currentPlaylist != null){
            VincentPlayer.logger.println("Rearranging this("+ currentPlaylist.toString() +") playlist");
            currentPlaylist.arrange();
        }else
            VincentPlayer.logger.println("No playlist is created", "warn");
    }

    @Override
    public String getName() {
        return "rearrangeplaylist";
    }

    @Override
    public String getUsage() {
        return "rearrangeplaylist";
    }

    @Override
    public String getDescription() {
        return "Arrange the playlist back into original order (which the first song is the first song you add to the playlist)";
    }
    
}