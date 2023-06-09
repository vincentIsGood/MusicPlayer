package com.vincentcodes.vincentplayer.commands;

import java.lang.reflect.Method;

import com.vincentcodes.vincentplayer.CommandUtil;
import com.vincentcodes.vincentplayer.VincentPlayer;

public class CommandExecutor {
    private final CommandRegister register;
    private final VincentPlayer player;

    /**
     * Constructor
     * @param register A command register used for recognizing what command is valid
     */
    public CommandExecutor(CommandRegister register, VincentPlayer player){
        this.register = register;
        this.player = player;
    }

    /**
     * Handle a command that has not been not parsed yet
     * @param command Unparsed command
     */
    public void handle(String command){
        String[] parsedCommand = CommandParser.parse(command);
        String commandName = CommandUtil.autoCompleteCommand(player.SUPPORTED_COMMANDS, parsedCommand[0].toLowerCase());
        try{
            for(Command cmd : register.getRegisteredCommands()){
                if(cmd.getName().equals(commandName)){
                    Method[] methods = cmd.getClass().getDeclaredMethods();
                    for(Method meth : methods){
                        // execute the first @Handler method
                        if(meth.getAnnotation(Handler.class) != null){
                            meth.invoke(cmd, new CommandEvent(player, parsedCommand));
                            break;
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}