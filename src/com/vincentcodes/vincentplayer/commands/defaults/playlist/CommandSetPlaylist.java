package com.vincentcodes.vincentplayer.commands.defaults.playlist;

import com.vincentcodes.vincentplayer.CommandUtil;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;
import com.vincentcodes.vincentplayer.exceptions.InvalidInputException;

public class CommandSetPlaylist implements Command {
    @Handler
    public void onSetPlaylist(CommandEvent event) throws InvalidInputException{
        VincentPlayer player = event.getPlayer();
        if(event.hasMultipleArgs()) 
            player.getState().setCurrentPlaylist(CommandUtil.searchElement(player.getPlaylists(), event.getParsedCommand()[1]));
    }

    @Override
    public String getName() {
        return "setplaylist";
    }

    @Override
    public String getUsage() {
        return "setplaylist <name>";
    }

    @Override
    public String getDescription() {
        return "Set the playlist to be played (when the next song comes)";
    }

}