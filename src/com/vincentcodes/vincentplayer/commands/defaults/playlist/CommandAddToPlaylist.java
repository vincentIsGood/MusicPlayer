package com.vincentcodes.vincentplayer.commands.defaults.playlist;

import java.util.List;

import com.vincentcodes.vincentplayer.CommandUtil;
import com.vincentcodes.vincentplayer.Playlist;
import com.vincentcodes.vincentplayer.Song;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;
import com.vincentcodes.vincentplayer.exceptions.InvalidInputException;

public class CommandAddToPlaylist implements Command {
    @Handler
    public void onAddToPlaylist(CommandEvent event) throws InvalidInputException{
        VincentPlayer player = event.getPlayer();
        String[] inputArgs = event.getParsedCommand();
        List<Playlist> playlists = player.getPlaylists();
        List<Song> songs = player.getSongs();
        
        if(event.hasMultipleArgs()){
            if(inputArgs.length > 3) 
                addSongToPlaylist(CommandUtil.searchElement(playlists, inputArgs[1]), CommandUtil.searchElement(songs, inputArgs[2]), Integer.parseInt(inputArgs[3]));
            else if(inputArgs.length > 2)
                addSongToPlaylist(CommandUtil.searchElement(playlists, inputArgs[1]), CommandUtil.searchElement(songs, inputArgs[2]));
        }
    }

    public void addSongToPlaylist(Playlist playlist, Song song, int index){
        playlist.add(index, song);
    }
    
    public void addSongToPlaylist(Playlist playlist, Song song){
        VincentPlayer.logger.println("playlist: " + playlist.toString() + " , song: " + song.toString());
        playlist.add(song);
    }

    @Override
    public String getName() {
        return "addtoplaylist";
    }

    @Override
    public String getUsage() {
        return "addtoplaylist <playlist> <song>";
    }

    @Override
    public String getDescription() {
        return "Add a song to a playlist";
    }

}