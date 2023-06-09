package com.vincentcodes.vincentplayer.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.vincentcodes.vincentplayer.VincentPlayer;

//Should have used with tokenizer / interpreter
public class CommandLoader{
    public CommandLoader(){
        if(!VincentPlayer.COMMANDS_DIR.exists()){
            VincentPlayer.logger.warn("Cannot find commands/ folder. This may cause .vpc scripts to malfunction (if you use them).");
        }
    }
    
    /**
     * Getting commands from default command directory <code>./commands</code>
     * @param name A file name with extension at <code>./commands/{name}</code>
     * @return Commands separated by line
     */
    public String[] getCommandsFromFile(String name){
        return getCommandsFromFile(name, VincentPlayer.COMMANDS_DIR);
    }
    
    /**
     * <p>
     * Get commands from <code>./{directory}/{name}</code>
     * <p>
     * Commands must be seperated by lines.
     * ie. # of lines = commands.size()
     * @param name A file name with extension
     * @param directory A directory in which file <code>name</code> is located
     * @return Commands separated by line
     */
    public String[] getCommandsFromFile(String name, File directory){
        ArrayList<String> commands = new ArrayList<>();
        File commandFile = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        
        try{
            commandFile = new File(directory.getCanonicalPath() + "/" + name);
            if(!commandFile.exists())
                throw new FileNotFoundException("File doesn't exist!");
            
            inputStreamReader = new InputStreamReader(new FileInputStream(commandFile), "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            
            String line = null;
            while((line = reader.readLine()) != null)
                commands.add(line);
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            commandFile = null;
            //just check one of them
            if(inputStreamReader != null){
                try{
                    reader.close();
                    inputStreamReader.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            inputStreamReader = null;
            reader = null;
        }
        
        //Just a trick
        return commands.toArray(new String[0]);
    }
}