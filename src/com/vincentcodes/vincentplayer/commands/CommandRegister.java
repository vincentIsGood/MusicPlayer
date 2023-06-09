package com.vincentcodes.vincentplayer.commands;

import java.util.ArrayList;

public class CommandRegister {
    private final ArrayList<Command> registeredCommands = new ArrayList<>();

    /**
     * Returns a copy of <code>ArrayList</code> of registeredCommands 
     * @return Registered commands
     */
    public ArrayList<Command> getRegisteredCommands(){
        return new ArrayList<>(registeredCommands);
    }

    /**
     * Register a command object. Command is an interface
     * @param command A class that implements Command
     */
    public void register(Command command){
        registeredCommands.add(command);
    }

    public void clear(){
        registeredCommands.clear();
    }
}