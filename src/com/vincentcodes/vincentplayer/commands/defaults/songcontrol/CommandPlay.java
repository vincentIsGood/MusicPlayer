package com.vincentcodes.vincentplayer.commands.defaults.songcontrol;

import com.vincentcodes.vincentplayer.CommandUtil;
import com.vincentcodes.vincentplayer.Song;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;
import com.vincentcodes.vincentplayer.exceptions.InvalidInputException;

public class CommandPlay implements Command{
    @Handler
    public void onPlay(CommandEvent event){
        try{
            VincentPlayer player = event.getPlayer();
            VincentPlayer.State state = player.getState();
            Song currentSong = state.getCurrentSong();
            String[] args = event.getParsedCommand();
            if(event.hasMultipleArgs()){
                if(state.isRepeatforever()){
                    VincentPlayer.logger.println("Turning off repeatforever", "warn");
                    state.setUseAutoNext(false);
                }
                if(currentSong != null)
                    currentSong.fullClose();
                
                Song newSong = CommandUtil.searchElement(player.getSongs(), args[1]);
                state.setCurrentSong(newSong);
                newSong.play();
            }else if(currentSong != null)
                currentSong.play();
        }catch(InvalidInputException e){
            VincentPlayer.logger.err("Have you add the specified song into the player? Use 'add [songname].mp3' to add songs.");
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getUsage() {
        return "play [<songname>]";
    }

    @Override
    public String getDescription() {
        return "Play a song which has been added into the play (by add / addall cmd)";
    }
}