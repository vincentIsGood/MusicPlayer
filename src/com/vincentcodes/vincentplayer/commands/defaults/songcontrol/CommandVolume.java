package com.vincentcodes.vincentplayer.commands.defaults.songcontrol;

import com.vincentcodes.vincentplayer.Song;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandVolume implements Command {
    @Handler
    public void onVolumeCommand(CommandEvent event){
        Song currentSong = event.getPlayer().getState().getCurrentSong();
        String[] inputArgs = event.getParsedCommand();
        if(event.hasMultipleArgs()){
            VincentPlayer.State.volume = Float.parseFloat(inputArgs[1]);
            if(currentSong != null){
                if(currentSong.isReady() && currentSong.isActive()){
                    currentSong.play(VincentPlayer.State.volume);
                }
            }
        }else if(currentSong != null)
            VincentPlayer.logger.println("Current volume: " + currentSong.getVolume() + "dB");
    }

    @Override
    public String getName() {
        return "volume";
    }

    @Override
    public String getUsage() {
        return "volume <-80 to 6.0206>";
    }

    @Override
    public String getDescription() {
        return "Set the volume or view it (in dB)";
    }
}