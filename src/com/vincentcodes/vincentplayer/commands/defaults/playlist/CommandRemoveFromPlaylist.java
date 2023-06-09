package com.vincentcodes.vincentplayer.commands.defaults.playlist;

import com.vincentcodes.vincentplayer.CommandUtil;
import com.vincentcodes.vincentplayer.Playlist;
import com.vincentcodes.vincentplayer.Song;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;
import com.vincentcodes.vincentplayer.exceptions.InvalidInputException;

public class CommandRemoveFromPlaylist implements Command {
    @Handler
    public void onRemoveFromPlaylist(CommandEvent event) throws InvalidInputException{
        VincentPlayer player = event.getPlayer();
        String[] inputArgs = event.getParsedCommand();
        if(event.hasMultipleArgs())
            removeSongFromPlaylist(CommandUtil.searchElement(player.getPlaylists(), inputArgs[1]), CommandUtil.searchElement(player.getSongs(), inputArgs[2]));
    }

    public void removeSongFromPlaylist(Playlist playlist, Song song){
        if(playlist.remove(song)){
            VincentPlayer.logger.println("Removal successful");
        }else
            VincentPlayer.logger.println("Cannot remove " + song.toString(), "warn");
    }

    @Override
    public String getName() {
        return "removefromplaylist";
    }

    @Override
    public String getUsage() {
        return "removefromplaylist <playlist> <song>";
    }

    @Override
    public String getDescription() {
        return "Remove a song from a playlist";
    }

}