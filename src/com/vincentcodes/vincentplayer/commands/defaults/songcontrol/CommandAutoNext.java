package com.vincentcodes.vincentplayer.commands.defaults.songcontrol;

import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandAutoNext implements Command {
    @Handler
    public void onAutoNext(CommandEvent event){
        VincentPlayer.State state = event.getPlayer().getState();

        if(state.isRepeatforever()){
            VincentPlayer.logger.println("Turning off repeatforever", "warn");
            state.setRepeatforever(false);
        }
        state.setUseAutoNext(!state.isUseAutoNext());
        VincentPlayer.logger.println("Auto next: " + state.isUseAutoNext());
    }

    @Override
    public String getName() {
        return "autonext";
    }

    @Override
    public String getUsage() {
        return "autonext";
    }

    @Override
    public String getDescription() {
        return "Automatically continue to the next song after the current song ends";
    }

}