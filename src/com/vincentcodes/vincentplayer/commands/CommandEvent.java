package com.vincentcodes.vincentplayer.commands;

import com.vincentcodes.vincentplayer.VincentPlayer;

public class CommandEvent {
    private final VincentPlayer player;
    private final String[] parsedCommand;

    public CommandEvent(VincentPlayer player, String[] parsedCommand){
        this.player = player;
        this.parsedCommand = parsedCommand;
    }

    public VincentPlayer getPlayer(){
        return this.player;
    }

    /**
     * The parsed command includes command name (eg. addtoplaylist)
     * @return An array of tokens which is parsed from a line of command
     */
    public String[] getParsedCommand(){
        return this.parsedCommand;
    }

    /**
     * Common command pattern: <command> <options(eg. --add)> <args>
     * eg. addtoplaylist <playlist> <song>
     * This method takes the first token (ie. addtoplaylist)
     * @return Command name
     */
    public String getCommandName(){
        return this.parsedCommand[0];
    }

    public boolean hasMultipleArgs(){
        return this.parsedCommand.length > 1;
    }
}