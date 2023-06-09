package com.vincentcodes.vincentplayer.commands.defaults.system;

import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandClearScreen implements Command {
    @Handler
    public void onClearScreen(CommandEvent event){
        for(int i = 0; i < 20; i++)
            System.out.println("\n");
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getUsage() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "Clear the screen";
    }

}