package com.vincentcodes.vincentplayer.commands.defaults.system;

import com.vincentcodes.vincentplayer.CommandUtil;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandListDirectory implements Command {
    @Handler
    public void onPrintFilesInDirectory(CommandEvent event){
        CommandUtil.printFilesInDirectory(event.getPlayer().getState().getCurrentDirectory());
    }

    @Override
    public String getName() {
        return "ls";
    }

    @Override
    public String getUsage() {
        return "ls";
    }

    @Override
    public String getDescription() {
        return "Print everything in this directory";
    }

}