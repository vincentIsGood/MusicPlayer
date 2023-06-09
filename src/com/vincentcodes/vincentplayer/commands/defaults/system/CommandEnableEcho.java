package com.vincentcodes.vincentplayer.commands.defaults.system;

import com.vincentcodes.vincentplayer.VincentPlayer;
import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.CommandEvent;
import com.vincentcodes.vincentplayer.commands.Handler;

public class CommandEnableEcho implements Command{

    @Handler
    public void onEnableEcho(CommandEvent e){
        VincentPlayer.State player = e.getPlayer().getState();
        player.setEchoEnabled(!player.isEchoEnabled());
        VincentPlayer.logger.log("Echo mode: " + player.isEchoEnabled());
    }

    @Override
    public String getName() {
        return "toggleecho";
    }

    @Override
    public String getUsage() {
        return "toggleecho";
    }

    @Override
    public String getDescription() {
        return "enable/disable echo by toggling";
    }

}
