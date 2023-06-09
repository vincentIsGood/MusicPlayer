package com.vincentcodes.vincentplayer.commands.defaults.songcontrol;

import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandRepeatForever implements Command {
    @Handler
    public void onRepeatForever(CommandEvent event){
        VincentPlayer.State state = event.getPlayer().getState();
        if(state.isUseAutoNext()){
            state.setUseAutoNext(false);
            VincentPlayer.logger.println("Will not play the next song automatically", "warn");
        }
        state.setRepeatforever(!state.isRepeatforever());
        VincentPlayer.logger.println("Repeat Forever: " + state.isRepeatforever());
    }

    @Override
    public String getName() {
        return "repeatforever";
    }

    @Override
    public String getUsage() {
        return "repeatforever";
    }

    @Override
    public String getDescription() {
        return "Repeat a song forever as long as this software / program is still running.";
    }

}