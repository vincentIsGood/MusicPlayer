package com.vincentcodes.vincentplayer.commands.defaults.system;

import java.io.File;
import java.util.List;

import com.vincentcodes.vincentplayer.CommandUtil;
import com.vincentcodes.vincentplayer.Song;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;
import com.vincentcodes.vincentplayer.exceptions.InvalidInputException;

public class CommandAddSong implements Command {
    private VincentPlayer player;
    private List<Song> songs;

    @Handler
    public void onAddSong(CommandEvent event) throws InvalidInputException{
        player = event.getPlayer();
        VincentPlayer.State state = player.getState();
        songs = player.getSongs();
        if(event.hasMultipleArgs())
            addSong(new File(state.getCurrentDirectory().getName() + "/" + CommandUtil.autoCompleteFromFolder(state.getCurrentDirectory(), event.getParsedCommand()[1])));
    }

    public void addSong(File pathToSong){
        try{
            addSong(pathToSong.getCanonicalPath());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void addSong(String pathToSong){
        try{
            Song newSong = new Song(pathToSong);
            if(CommandUtil.searchIdentical(songs, newSong.getName()) != null)
                return;
            songs.add(newSong);
            VincentPlayer.logger.log("Adding " + newSong.getName());
            if(songs.size() == 1)
                player.getState().setCurrentSong(newSong);
            newSong = null;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getUsage() {
        return "add <song file>";
    }

    @Override
    public String getDescription() {
        return "Add a song into the music player";
    }

}