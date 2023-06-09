package com.vincentcodes.vincentplayer.commands.defaults.system;

import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandPathToCurrentDir implements Command {
    @Handler
    public void onPrintPathToCurrentDir(CommandEvent event){
        VincentPlayer player = event.getPlayer();
        VincentPlayer.logger.log(player.getState().getCurrentDirectory().getAbsolutePath());
    }

    @Override
    public String getName() {
        return "pwd";
    }

    @Override
    public String getUsage() {
        return "pwd";
    }

    @Override
    public String getDescription() {
        return "Print Working Directory";
    }
    
}
