package com.vincentcodes.vincentplayer.commands.defaults.playlist;

import com.vincentcodes.vincentplayer.CommandUtil;
import com.vincentcodes.vincentplayer.Playlist;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandCreatePlayList implements Command {
    @Handler
    public void onAddPlaylist(CommandEvent event){
        if(event.hasMultipleArgs())
            addNewPlaylist(event.getParsedCommand()[1], event.getPlayer());
    }

    public void addNewPlaylist(String name, VincentPlayer player){
        if(CommandUtil.searchIdentical(player.getPlaylists(), name) == null){
            Playlist newPlaylist = new Playlist(name);
            player.getPlaylists().add(newPlaylist);
        }
    }

    @Override
    public String getName() {
        return "createplaylist";
    }

    @Override
    public String getUsage() {
        return "createplaylist <name>";
    }

    @Override
    public String getDescription() {
        return "Create a new playlist";
    }

}