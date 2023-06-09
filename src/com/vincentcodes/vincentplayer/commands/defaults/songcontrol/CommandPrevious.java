package com.vincentcodes.vincentplayer.commands.defaults.songcontrol;

import com.vincentcodes.vincentplayer.Song;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandPrevious implements Command{
    @Handler
    public void onPrevious(CommandEvent event){
        VincentPlayer.State state = event.getPlayer().getState();

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
            currentSong = state.getCurrentPlaylist().reverseToPreviousSong();
        }
        state.setCurrentSong(currentSong);
        VincentPlayer.logger.println("Playing: " + currentSong.getName());
        currentSong.play();
    }

    @Override
    public String getName() {
        return "previous";
    }

    @Override
    public String getUsage() {
        return "previous";
    }

    @Override
    public String getDescription() {
        return "Play the previous song in the playlist";
    }
    
}