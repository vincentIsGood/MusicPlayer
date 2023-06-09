package com.vincentcodes.vincentplayer.commands.defaults.system;

import java.io.File;

import com.vincentcodes.vincentplayer.CommandUtil;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;
import com.vincentcodes.vincentplayer.exceptions.InvalidInputException;

public class CommandChangeDirectory implements Command {
    @Handler
    public void onChangeDirectory(CommandEvent event) throws InvalidInputException{
        VincentPlayer player = event.getPlayer();
        String[] inputArgs = event.getParsedCommand();
        VincentPlayer.State state = player.getState();
        File currentDirectory = state.getCurrentDirectory();
        File previousDirectory = state.getPreviousDirectory();
        if(event.hasMultipleArgs()){
            if(inputArgs[1].equals("[PREVIOUS]")){
                File tmp = currentDirectory;
                state.setCurrentDirectory(previousDirectory);
                state.setPreviousDirectory(tmp);
            }else
                state.setPreviousDirectory(currentDirectory); // if it's not [PREVIOUS], move "forward", set new [PREVIOUS]
            
            if(inputArgs[1].equals("[HOME]"))
                state.setCurrentDirectory(VincentPlayer.PLAYER_DIR);
            else if(inputArgs[1].equals("[COMMANDS]"))
                state.setCurrentDirectory(VincentPlayer.COMMANDS_DIR);
            else if(!inputArgs[1].equals("[PREVIOUS]")) //excluding [PREVIOUS] since it's separated
                state.setCurrentDirectory(new File(state.getCurrentDirectory(), CommandUtil.autoCompleteFromFolder(state.getCurrentDirectory(), inputArgs[1]))); 
        }
        if(!state.getCurrentDirectory().isDirectory()){
            VincentPlayer.logger.warn("You've selected a non-directory. Returning to original position...");
            state.setCurrentDirectory(VincentPlayer.PLAYER_DIR);
        }
    }

    @Override
    public String getName() {
        return "cd";
    }

    @Override
    public String getUsage() {
        return "cd <dir>";
    }

    @Override
    public String getDescription() {
        return "Change directory";
    }

}