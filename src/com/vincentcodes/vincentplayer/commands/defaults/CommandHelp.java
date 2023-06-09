package com.vincentcodes.vincentplayer.commands.defaults;

import java.util.Optional;

import com.vincentcodes.vincentplayer.CommandUtil;
import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandHelp implements Command {
    @Handler
    public void onRequestHelp(CommandEvent event){
        VincentPlayer player = event.getPlayer();
        VincentPlayer.logger.println("Supported commands:");
        
        // This is looking for eg. "help <cmd> -> description"
        if(event.hasMultipleArgs()){
            String cmd = event.getParsedCommand()[1];
            Optional<Command> filteredCommand =  VincentPlayer.COMMAND_REGISTER.getRegisteredCommands().stream().filter(c -> c.getName().equals(CommandUtil.autoCompleteCommand(player.SUPPORTED_COMMANDS, cmd))).findFirst();
            if(filteredCommand.isPresent()){
                Command requestedCommand = filteredCommand.get();
                VincentPlayer.logger.println("Command   : " + requestedCommand.getUsage());
                VincentPlayer.logger.println("Desciption: " + requestedCommand.getDescription());
            }
        }else{
            VincentPlayer.COMMAND_REGISTER.getRegisteredCommands().forEach(cmd -> VincentPlayer.logger.println(cmd.getUsage()));
        }
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getUsage() {
        return "help [<command>]";
    }

    @Override
    public String getDescription() {
        return "Help!";
    }

}