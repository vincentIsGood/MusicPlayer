package com.vincentcodes.vincentplayer.commands.defaults.playerstate;

import com.vincentcodes.vincentplayer.Song;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandShowProgress implements Command {
    private final int PROGRESS_BAR_LENGTH = 100;

    @Handler
    public void onShowProgress(CommandEvent event){
        Song currentSong = event.getPlayer().getState().getCurrentSong();

        StringBuilder progress = new StringBuilder("");
        double ratio = currentSong.getPosition()/currentSong.getDuration();
        int number = (int)Math.round((ratio) * PROGRESS_BAR_LENGTH);
        for(int i = 0; i < PROGRESS_BAR_LENGTH; i++){
            if(i < number)
                progress.append("=");
            else
                progress.append(" ");
        }
        System.out.print("\r[" + progress.toString() + "]"+ Math.round(ratio*100) +"%");
        progress.delete(0, progress.length());
    }

    @Override
    public String getName() {
        return "progress";
    }

    @Override
    public String getUsage() {
        return "progress";
    }

    @Override
    public String getDescription() {
        return "Show the progress of playing a song with a progress bar";
    }

}