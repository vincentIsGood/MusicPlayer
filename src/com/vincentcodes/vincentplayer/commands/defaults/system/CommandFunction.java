package com.vincentcodes.vincentplayer.commands.defaults.system;

import java.io.File;

import com.vincentcodes.vincentplayer.CommandUtil;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.CommandLoader;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandFunction implements Command {
    private CommandLoader commandLoader = new CommandLoader();
    private String currentCommandFileName = "";
    private int currentLineofCommand = 0;

    @Handler
    public void onFunctionCall(CommandEvent event){
        try{
            VincentPlayer player = event.getPlayer();
            File currentDirectory = player.getState().getCurrentDirectory();
            String[] inputArgs = event.getParsedCommand();

            if(event.hasMultipleArgs()){
                String[] commands = commandLoader.getCommandsFromFile(CommandUtil.autoCompleteFromFolder(currentDirectory, inputArgs[1]), currentDirectory);
                currentCommandFileName = CommandUtil.autoCompleteFromFolder(currentDirectory, inputArgs[1]);
                for(int i = 0; i < commands.length; i++){
                    currentLineofCommand = i;
                    player.executeCommand(commands[i]);
                }
                currentLineofCommand = 0;
            }
        }catch(Exception e){
            if(currentLineofCommand != 0)
                System.err.printf("Cannot execute this line of Command at %s, line %d\n", currentCommandFileName, currentLineofCommand);
        }
    }

    @Override
    public String getName() {
        return "function";
    }

    @Override
    public String getUsage() {
        return "function <file>";
    }

    @Override
    public String getDescription() {
        return "Runs a script that contains a series of commands for this music player";
    }

}