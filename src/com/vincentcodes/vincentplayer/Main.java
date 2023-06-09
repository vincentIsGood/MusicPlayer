package com.vincentcodes.vincentplayer;

import com.vincentcodes.vincentplayer.commands.Command;
import com.vincentcodes.vincentplayer.commands.defaults.CommandExit;
import com.vincentcodes.vincentplayer.commands.defaults.CommandHelp;
import com.vincentcodes.vincentplayer.commands.defaults.playerstate.CommandListSongs;
import com.vincentcodes.vincentplayer.commands.defaults.playerstate.CommandShowCurrentPlaylist;
import com.vincentcodes.vincentplayer.commands.defaults.playerstate.CommandShowDuration;
import com.vincentcodes.vincentplayer.commands.defaults.playerstate.CommandShowInfo;
import com.vincentcodes.vincentplayer.commands.defaults.playerstate.CommandShowName;
import com.vincentcodes.vincentplayer.commands.defaults.playerstate.CommandShowPos;
import com.vincentcodes.vincentplayer.commands.defaults.playerstate.CommandShowProgress;
import com.vincentcodes.vincentplayer.commands.defaults.playerstate.CommandShowSongIsActive;
import com.vincentcodes.vincentplayer.commands.defaults.playerstate.CommandShowSongIsFinished;
import com.vincentcodes.vincentplayer.commands.defaults.playlist.CommandAddToPlaylist;
import com.vincentcodes.vincentplayer.commands.defaults.playlist.CommandCreatePlayList;
import com.vincentcodes.vincentplayer.commands.defaults.playlist.CommandListPlaylist;
import com.vincentcodes.vincentplayer.commands.defaults.playlist.CommandPrintPlaylist;
import com.vincentcodes.vincentplayer.commands.defaults.playlist.CommandRearrangePlaylist;
import com.vincentcodes.vincentplayer.commands.defaults.playlist.CommandRemoveFromPlaylist;
import com.vincentcodes.vincentplayer.commands.defaults.playlist.CommandRemovePlaylist;
import com.vincentcodes.vincentplayer.commands.defaults.playlist.CommandSetPlaylist;
import com.vincentcodes.vincentplayer.commands.defaults.playlist.CommandShufflePlaylist;
import com.vincentcodes.vincentplayer.commands.defaults.playlist.CommandUsePlaylist;
import com.vincentcodes.vincentplayer.commands.defaults.songcontrol.CommandAutoNext;
import com.vincentcodes.vincentplayer.commands.defaults.songcontrol.CommandNext;
import com.vincentcodes.vincentplayer.commands.defaults.songcontrol.CommandPlay;
import com.vincentcodes.vincentplayer.commands.defaults.songcontrol.CommandPrevious;
import com.vincentcodes.vincentplayer.commands.defaults.songcontrol.CommandRepeatForever;
import com.vincentcodes.vincentplayer.commands.defaults.songcontrol.CommandReplay;
import com.vincentcodes.vincentplayer.commands.defaults.songcontrol.CommandSetPos;
import com.vincentcodes.vincentplayer.commands.defaults.songcontrol.CommandStop;
import com.vincentcodes.vincentplayer.commands.defaults.songcontrol.CommandVolume;
import com.vincentcodes.vincentplayer.commands.defaults.streaming.CommandConnectStream;
import com.vincentcodes.vincentplayer.commands.defaults.streaming.CommandHostStream;
import com.vincentcodes.vincentplayer.commands.defaults.streaming.CommandStopStream;
import com.vincentcodes.vincentplayer.commands.defaults.system.CommandAddAllSongs;
import com.vincentcodes.vincentplayer.commands.defaults.system.CommandAddSong;
import com.vincentcodes.vincentplayer.commands.defaults.system.CommandChangeDirectory;
import com.vincentcodes.vincentplayer.commands.defaults.system.CommandClearScreen;
import com.vincentcodes.vincentplayer.commands.defaults.system.CommandEnableEcho;
import com.vincentcodes.vincentplayer.commands.defaults.system.CommandFunction;
import com.vincentcodes.vincentplayer.commands.defaults.system.CommandListDirectory;
import com.vincentcodes.vincentplayer.commands.defaults.system.CommandPathToCurrentDir;
import com.vincentcodes.vincentplayer.commands.defaults.system.CommandRemoveSong;
import com.vincentcodes.vincentplayer.commands.defaults.system.CommandSetLog;

public class Main {
    public static void main(String[] args) throws Exception{
        // StreamSongPoC.playSongPartially();
        
        // ---------- TCP ---------- //
        // registerCommands();
        // VincentPlayer player = new VincentPlayer();
        // player.executeCommand("add test.mp3");
        // StreamingDevice stream = new TcpStreamingDevice(player, 1234);
        // stream.start();

        // StreamConnector connector = new TcpStreamConnector("127.0.0.1", 1234);
        // connector.start();

        // ---------- UDP ---------- //
        // registerCommands();
        // VincentPlayer player = new VincentPlayer();
        // player.executeCommand("add test2.mp3");
        // player.executeCommand("add test.mp3");
        // StreamingDevice stream = new UdpStreamingDevice(player, 1234);
        // stream.start();

        // StreamConnector connector = new UdpStreamConnector("127.0.0.1", 1234);
        // connector.start();

        registerCommands();
        VincentPlayer player = new VincentPlayer();
        player.start();
    }

    public static void registerCommands(){
        // Player Controls
        registerItem(new CommandPlay());
        registerItem(new CommandStop());
        registerItem(new CommandReplay());
        registerItem(new CommandRepeatForever());
        registerItem(new CommandNext());
        registerItem(new CommandPrevious());
        registerItem(new CommandSetPos());
        registerItem(new CommandAutoNext());
        registerItem(new CommandVolume());

        // Show Player States
        registerItem(new CommandShowSongIsFinished());
        registerItem(new CommandShowSongIsActive());
        registerItem(new CommandListSongs());
        registerItem(new CommandShowDuration());
        registerItem(new CommandShowPos());
        registerItem(new CommandShowInfo());
        registerItem(new CommandShowName());
        registerItem(new CommandShowCurrentPlaylist());
        registerItem(new CommandShowProgress());

        // Playlist
        registerItem(new CommandUsePlaylist());
        registerItem(new CommandShufflePlaylist());
        registerItem(new CommandRearrangePlaylist());
        registerItem(new CommandListPlaylist());
        registerItem(new CommandPrintPlaylist());
        registerItem(new CommandCreatePlayList());
        registerItem(new CommandRemovePlaylist());
        registerItem(new CommandSetPlaylist());
        registerItem(new CommandAddToPlaylist());
        registerItem(new CommandRemoveFromPlaylist());

        // Streaming
        registerItem(new CommandStopStream());
        registerItem(new CommandHostStream());
        registerItem(new CommandConnectStream());

        // System (IO stuff)
        registerItem(new CommandAddSong());
        registerItem(new CommandAddAllSongs());
        registerItem(new CommandRemoveSong());
        registerItem(new CommandEnableEcho());
        // Not yet implemented:
        //registerItem(new CommandRemoveAllSongs());
        registerItem(new CommandClearScreen());
        registerItem(new CommandListDirectory());
        registerItem(new CommandChangeDirectory());
        registerItem(new CommandPathToCurrentDir());
        registerItem(new CommandFunction());
        registerItem(new CommandSetLog());
        registerItem(new CommandExit());

        // Help
        VincentPlayer.COMMAND_REGISTER.register(new CommandHelp());
    }

    private static void registerItem(Command cmd){
        VincentPlayer.COMMAND_REGISTER.register(cmd);
    }
}