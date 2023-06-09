package com.vincentcodes.vincentplayer.commands;

import java.util.ArrayList;

public class CommandParser {
    public static String[] parse(String command){
        ArrayList<String> pieces = new ArrayList<>();
        StringBuilder string = new StringBuilder();
        int index = 0;

        while(index < command.length()){
            String character = Character.toString(command.charAt(index++));
            if(character.matches("\\s")){
                pieces.add(string.toString());
                string = new StringBuilder();
            }else if(character.matches("[\"\']")){
                while(!(character = Character.toString(command.charAt(index++))).matches("[\"\']")){
                    string.append(character);
                }
            }else
                string.append(character);
        }
        pieces.add(string.toString());

        return pieces.toArray(new String[]{});
    }
}