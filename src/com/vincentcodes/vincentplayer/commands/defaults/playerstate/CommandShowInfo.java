package com.vincentcodes.vincentplayer.commands.defaults.playerstate;

import com.vincentcodes.vincentplayer.Playlist;
import com.vincentcodes.vincentplayer.Song;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandShowInfo implements Command {
    @Handler
    public void onShowInfo(CommandEvent event){
        VincentPlayer.State state = event.getPlayer().getState();
        Song currentSong = state.getCurrentSong();
        Playlist currentPlaylist = state.getCurrentPlaylist();

        if(currentSong != null && currentSong.isReady()){
            VincentPlayer.logger.println("Current Song                  : " + currentSong.getName());
            VincentPlayer.logger.println("Current Song is active/playing: " + currentSong.isActive());
            VincentPlayer.logger.println("Current Song is finished      : " + currentSong.isFinished());
            VincentPlayer.logger.println("Current Song duration(sec)    : " + currentSong.getDuration() + "s");
            VincentPlayer.logger.println("Current Sound Volume          : " + currentSong.getVolume() + "db");
        }else
            VincentPlayer.logger.println("No Song is Imported/Playing");
        
        if(currentPlaylist != null && state.isUsePlaylist() && currentPlaylist.getLength() > 0){
            VincentPlayer.logger.println("Current Playlist              : " + currentPlaylist.getName());
            VincentPlayer.logger.println("Upcoming Song                 : " + currentPlaylist.getUpComingSong().getName());
            VincentPlayer.logger.println("Previous Song                 : " + currentPlaylist.getPreviousSong().getName());
        }else{
            VincentPlayer.logger.println("No Playlist is Selected/Created");
        }
        
            VincentPlayer.logger.println("Use Playlist                  : " + state.isUsePlaylist());
            VincentPlayer.logger.println("Auto Play Next Song           : " + state.isUseAutoNext());
            VincentPlayer.logger.println("Repeat Current Song Forever   : " + state.isRepeatforever());
    }

    @Override
    public String getName() {
        return "information";
    }

    @Override
    public String getUsage() {
        return "information";
    }

    @Override
    public String getDescription() {
        return "Show current information of the music player";
    }
    
}