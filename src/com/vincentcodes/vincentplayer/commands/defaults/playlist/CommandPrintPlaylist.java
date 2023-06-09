package com.vincentcodes.vincentplayer.commands.defaults.playlist;

import com.vincentcodes.vincentplayer.CommandUtil;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;
import com.vincentcodes.vincentplayer.exceptions.InvalidInputException;

public class CommandPrintPlaylist implements Command {
    @Handler
    public void onPrintPlaylist(CommandEvent event) throws InvalidInputException{
        if(event.hasMultipleArgs()) 
            CommandUtil.searchElement(event.getPlayer().getPlaylists(), event.getParsedCommand()[1]).printPlaylist();
    }

    @Override
    public String getName() {
        return "printplaylist";
    }

    @Override
    public String getUsage() {
        return "printplaylist <playlist>";
    }

    @Override
    public String getDescription() {
        return "Print a specified playlist";
    }

}