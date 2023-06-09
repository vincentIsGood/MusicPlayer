package com.vincentcodes.vincentplayer;

import java.io.File;
import java.util.List;

import com.vincentcodes.vincentplayer.exceptions.InvalidInputException;

public class CommandUtil {
    public static void printFilesInDirectory(File directory){
        if(directory.isDirectory()){
            for(int i = 0; i < directory.list().length; i++){
                VincentPlayer.logger.println(directory.list()[i]);
            }
        }
    }

    public static String autoCompleteFromFolder(File folder, String name) throws InvalidInputException{
        //Exception...
        if(name.equals(".."))
            return name;
        if(name.trim().equals(""))
            return "";
        
        //Normal procedure...
        String tmp = "";
        for(int i = 0; i < folder.list().length; i++){
            if(folder.list()[i].startsWith(name))
                tmp = folder.list()[i];
        }
        
        if(!tmp.equals("")){
            VincentPlayer.logger.println("Selecting " + tmp);
            return tmp;
        }else
            throw new InvalidInputException(name + " -- cannot be found");
    }

    //For now, method toString() returns the name of the Song/Playlist
    public static <T> String autoCompleteName(List<T> array, String name){
        if(!name.trim().equals("")){
            for(T tmp : array){
                if(tmp.toString().startsWith(name)){
                    VincentPlayer.logger.println("Selecting " + tmp.toString());
                    return tmp.toString();
                }
            }
        }
        return "";
    }
    
    // private <T> String autoCompleteName(T[] array, String name){
    //     return autoCompleteName(array, name, true);
    // }
    
    public static <T> String autoCompleteName(T[] array, String name, boolean printResult){
        if(!name.trim().equals("")){
            for(T tmp : array){
                if(tmp.toString().startsWith(name)){
                    if(printResult)
                        VincentPlayer.logger.println("Selecting " + tmp.toString());
                    return tmp.toString();
                }
            }
        }
        return "";
    }
    
    //Using toString() method to search (NOT ACCURATE, Object.toString() may not always give the thing you want)
    public static <T> T searchIdentical(List<T> array, String name){
        if(!name.trim().equals("")){
            for(T tmp : array){
                if(tmp.toString().equals(name)){
                    return tmp;
                }
            }
        }
        return null;
    }
    public static <T> T searchIdentical(T[] array, String name){
        if(!name.trim().equals("")){
            for(T tmp : array){
                if(tmp.toString().equals(name)){
                    return tmp;
                }
            }
        }
        return null;
    }
    
    public static <T> T searchElement(List<T> array, String name) throws InvalidInputException{
        if(!name.trim().equals("")){
            for(T tmp : array){
                if(tmp.toString().startsWith(name)){
                    VincentPlayer.logger.println("Selecting " + tmp.toString());
                    return tmp;
                }
            }
        }
        throw new InvalidInputException("Cannot find requested Element: '" + name + "'");
    }
    
    /**
     * TBH, I can put all of the commands into a HashMap / Map to further increase
     * the performance.
     */
    public static String autoCompleteCommand(String[] commands, String command){
        String[] cmd = new String[commands.length];
        for(int i = 0; i < commands.length; i++){
            cmd[i] = commands[i].split(" ")[0]; // I need the command, not the description
        }

        // identical one takes priority
        String res = searchIdentical(cmd, command);
        if(res != null) 
            return res;
        return autoCompleteName(cmd, command, false);
    }
}