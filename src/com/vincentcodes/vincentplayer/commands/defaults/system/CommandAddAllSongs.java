package com.vincentcodes.vincentplayer.commands.defaults.system;

import java.io.File;
import java.util.List;

import com.vincentcodes.vincentplayer.CommandUtil;
import com.vincentcodes.vincentplayer.Song;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandAddAllSongs implements Command {
    private VincentPlayer player;
    private List<Song> songs;

    @Handler
    public void onAddAllSongs(CommandEvent event){
        player = event.getPlayer();
        File currentDirectory = player.getState().getCurrentDirectory();
        songs = player.getSongs();
        
        if(currentDirectory.isDirectory()){
            for(int i = 0; i < currentDirectory.list().length; i++){
                addSong(currentDirectory.listFiles()[i]);
            }
        }
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
        return "addall";
    }

    @Override
    public String getUsage() {
        return "addall";
    }

    @Override
    public String getDescription() {
        return "Add every song in the current directory (you can view what songs are in your current directory isomg 'ls' command)";
    }

}