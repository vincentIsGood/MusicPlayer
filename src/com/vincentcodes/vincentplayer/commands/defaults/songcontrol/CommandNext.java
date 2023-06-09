package com.vincentcodes.vincentplayer.commands.defaults.songcontrol;

import com.vincentcodes.vincentplayer.Song;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandNext implements Command {
    @Handler
    public void onNext(CommandEvent event){
        VincentPlayer player = event.getPlayer();
        VincentPlayer.State state = player.getState();

        if(state.isRepeatforever()){
            VincentPlayer.logger.println("Turning off repeatforever", "warn");
            state.setRepeatforever(false);
        }

        Song currentSong = state.getCurrentSong();
        if(currentSong != null){
            if(currentSong.isReady())
                currentSong.fullClose();
        }else{
            VincentPlayer.logger.warn("No song(s) ready");
            return;
        }
        if(state.isUsePlaylist() && state.getCurrentPlaylist() != null){
            currentSong = state.getCurrentPlaylist().continueToNewSong();
        }else{
            //without using playlist
            if(player.getSongs().size() > 1){
                currentSong = player.getRandomSong();
            }
        }
        state.setCurrentSong(currentSong);
        VincentPlayer.logger.println("Playing: " + currentSong.getName());
        currentSong.play();
    }

    @Override
    public String getName() {
        return "next";
    }

    @Override
    public String getUsage() {
        return "next";
    }

    @Override
    public String getDescription() {
        return "Stop the current song and play the next song. If the use of playlist is not enabled or no playlist is available, a random song is selected.";
    }

}