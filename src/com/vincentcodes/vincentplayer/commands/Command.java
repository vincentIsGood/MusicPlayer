package com.vincentcodes.vincentplayer.commands;

/**
 * Command line terminology
 * https://unix.stackexchange.com/questions/416945/command-line-terminology-what-are-these-parts-of-a-command-called
 */
public interface Command {
    /**
     * Inside this command line, "addplaylist <playlist> <song>", <code>addplaylist</code> is the command name
     * @return Command name 
     */
    public String getName();
    public String getUsage();
    public String getDescription();
}