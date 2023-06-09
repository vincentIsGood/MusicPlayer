package com.vincentcodes.vincentplayer.commands.defaults;

import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandExit implements Command {
    @Handler
    public void onExit(CommandEvent event){
        VincentPlayer.logger.close();
        System.exit(0);
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getUsage() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "Exit the program";
    }

}