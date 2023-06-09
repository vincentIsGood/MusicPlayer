package com.vincentcodes.vincentplayer.commands.defaults.system;

import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandSetLog implements Command {
    @Handler
    public void onToggleLog(CommandEvent event){
        String[] inputArgs = event.getParsedCommand();
        if(event.hasMultipleArgs()){
            System.out.println("Setting log to screen to: " + Boolean.parseBoolean(inputArgs[1]));
            VincentPlayer.logger.doPrint(Boolean.parseBoolean(inputArgs[1]));
        }
    }

    @Override
    public String getName() {
        return "log";
    }

    @Override
    public String getUsage() {
        return "log <true / false>";
    }

    @Override
    public String getDescription() {
        return "Set if logging is allowed. (Including console logs)";
    }

}