/*          Vincent Player          *\

Created By Vincent Ko

Purpose of creation:
This music player is a part of the VincentTools series. 
The series aimed to gain programming experience by initiating a series of projects and to make life easier? (I bet that is why I started programming!)
All projects initiated by Vincent must have at least an application in real life. 
Otherwise, the project will be terminated...

Edit:
All projects initiated by Vincent must serve at least a purpose.
(I acknowledged that there are lots of "test projects", although
I wouldn't count them as projects. Just in case...) 

Date of Creation:
(MM/DD/YYYY)
2/2/2019

Purpose of creation is written on:
3/23/2019

Edit is written on:
10/27/2020
\*                                   */
package com.vincentcodes.vincentplayer;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.vincentcodes.logger.Logger;
import com.vincentcodes.vincentplayer.commands.CommandExecutor;
import com.vincentcodes.vincentplayer.commands.CommandRegister;
import com.vincentcodes.vincentplayer.streaming.StreamConnector;
import com.vincentcodes.vincentplayer.streaming.StreamingDevice;

import org.bytedeco.ffmpeg.global.avutil;

/**
 * The gist of the project
 */
public class VincentPlayer{
    public static final String VERSION = "4.0";
    public static final String VAR_OS = System.getProperty("os.name").toLowerCase();
    public static final Logger logger = new Logger("player", false);
    public static final SongIdCounter ID_COUNTER = new SongIdCounter();
    public static final int DEFAULT_STREAMING_PORT = 12500;
    public static final String DEFAULT_STREAMING_PROTOCOL = "tcp";
    
    public final String[] SUPPORTED_COMMANDS;

    public static final File PLAYER_DIR = new File("./");
    public static final File COMMANDS_DIR = new File("./commands");
    
    public static final CommandRegister COMMAND_REGISTER = new CommandRegister();
    
    private final CommandExecutor commandExecutor = new CommandExecutor(COMMAND_REGISTER, this);
    private final Scanner sn;

    private final List<Song> songs = new ArrayList<>();
    private final List<Playlist> playlists = new ArrayList<>();
    private final State playerState;

    public static class State{
        public static float volume = -15f;

        private File currentDirectory = PLAYER_DIR;
        private File previousDirectory;
        
        private boolean useAutoNext = false;
        private boolean usePlaylist = false;
        private boolean echoEnabled = false;
        private boolean repeatforever = false;
        
        private Playlist currentPlaylist;
        private Song currentSong;

        private StreamConnector streamConnector;
        private StreamingDevice streamingDevice;

        public File getCurrentDirectory() {
            return currentDirectory;
        }
        public void setCurrentDirectory(File currentDirectory) {
            this.currentDirectory = currentDirectory;
        }
        public File getPreviousDirectory() {
            return previousDirectory;
        }
        public void setPreviousDirectory(File previousDirectory) {
            this.previousDirectory = previousDirectory;
        }
        public boolean isUseAutoNext() {
            return useAutoNext;
        }
        public void setUseAutoNext(boolean useAutoNext) {
            this.useAutoNext = useAutoNext;
        }
        public boolean isUsePlaylist() {
            return usePlaylist;
        }
        public void setUsePlaylist(boolean usePlaylist) {
            this.usePlaylist = usePlaylist;
        }
        public boolean isEchoEnabled() {
            return echoEnabled;
        }
        public void setEchoEnabled(boolean echoEnabled) {
            this.echoEnabled = echoEnabled;
        }
        public boolean isRepeatforever() {
            return repeatforever;
        }
        public void setRepeatforever(boolean repeatforever) {
            this.repeatforever = repeatforever;
        }
        public Playlist getCurrentPlaylist() {
            return currentPlaylist;
        }
        public void setCurrentPlaylist(Playlist currentPlaylist) {
            this.currentPlaylist = currentPlaylist;
        }
        public Song getCurrentSong() {
            return currentSong;
        }
        public void setCurrentSong(Song currentSong) {
            this.currentSong = currentSong;
        }
        public StreamConnector getStreamConnector() {
            return streamConnector;
        }
        public void setStreamConnector(StreamConnector streamConnector) {
            this.streamConnector = streamConnector;
        }
        public StreamingDevice getStreamingDevice() {
            return streamingDevice;
        }
        public void setStreamingDevice(StreamingDevice streamingDevice) {
            this.streamingDevice = streamingDevice;
        }
    }
    
    public VincentPlayer() throws IOException{
        // BufferedReader won't work, idk why.
        // https://stackoverflow.com/questions/50854762/reading-characters-with-utf-8-standards-from-console-in-java
        if(VAR_OS.contains("win"))
            sn = new Scanner(new InputStreamReader(System.in, "shift-jis"));
        else sn = new Scanner(new InputStreamReader(System.in));
        SUPPORTED_COMMANDS = COMMAND_REGISTER.getRegisteredCommands().stream().map(c -> c.getName()).toArray(String[]::new);
        playerState = new State();
    }
    
    //currently, VincentPlayer is command line based...
    public void start(){
        if(!System.getProperty("file.encoding").toLowerCase().equals("utf-8")){
            logger.warn("Since file.encoding is not set, I recommend that you run the program");
            logger.warn("with -Dfile.encoding=utf-8 if your music files contain non-ascii characters.");
        }

        autoNextInit();
        
        avutil.av_log_set_level(avutil.AV_LOG_ERROR);

        logger.println("VincentPlayer is now running on " + VAR_OS);
        logger.println("Type \"help\" to show available commands");
        logger.println("Add single quotes(') or double quotes(\") to quote individual argument that has space in it");
        logger.println("(eg. play \"A B C\" where \"play\" is the command, \"A B C\" is the song name)");
        
        // if(VAR_OS.contains("win")){
        //     logger.println("For any windows users, it is impossible to play a non-english-titled song without using scripts (.vpc files) to add them to playlist(s)", "warn");
        //     logger.warn("Windows console do not support special characters inputs");
        //     logger.warn("So, you must use 'addall' command instead of 'add <songname>' command to add songs into VincentPlayer");
        //     logger.warn("Besides, VincentPlayer allows you to select a song by song id without the need to type in the song name");
        // }
        if(VAR_OS.contains("mac") || VAR_OS.contains("linux")){
            logger.setUseColors(true);
        }
        logger.debug("Logger can use colored text: " + logger.getUseColors());

        while(true){
            try{
                System.out.print(">>");
                String input = sn.nextLine();

                if(playerState.isEchoEnabled())
                    System.out.println(input);
                
                executeCommand(input);
                
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void executeCommand(String command) throws Exception{
        commandExecutor.handle(command);
    }
    
    //------------------------------------add------------------------------------//
    public void createNewPlaylist(String name){
        if(CommandUtil.searchIdentical(playlists, name) == null){
            Playlist newPlaylist = new Playlist(name);
            playlists.add(newPlaylist);
        }
    }
    
    public void addSongToPlaylist(Playlist playlist, Song[] songs, int index){
        for(Song song : songs){
            addSongToPlaylist(playlist, song, index);
        }
    }
    
    public void addSongToPlaylist(Playlist playlist, Song[] songs){
        for(Song song : songs){
            addSongToPlaylist(playlist, song);
        }
    }
    
    public void addSongToPlaylist(Playlist playlist, Song song, int index){
        playlist.add(index, song);
    }
    
    public void addSongToPlaylist(Playlist playlist, Song song){
        VincentPlayer.logger.println("playlist: " + playlist.toString() + " , song: " + song.toString());
        playlist.add(song);
    }
    
    public void addAllSongs(){
        if(playerState.currentDirectory.isDirectory()){
            for(int i = 0; i < playerState.currentDirectory.list().length; i++){
                addSong(playerState.currentDirectory.listFiles()[i]);
            }
        }
    }
    
    public void addSong(File pathToSong){
        try{
            addSong(pathToSong.getCanonicalPath());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void addSong(String pathToSong){
        try{
            Song newSong = new Song(pathToSong);
            if(CommandUtil.searchIdentical(songs, newSong.getName()) != null)
                return;
            songs.add(newSong);
            if(songs.size() == 1)
                playerState.currentSong = newSong;
            newSong = null;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //------------------------------------remove------------------------------------//
    public void removePlaylist(String name){
        //remember that for(a : array) uses concurrent - it thorws an exception - to iterate through the array.
        //It's not suitable to use it to delete elements at the same time.
        for(int i = 0; i < playlists.size(); i++){
            if(playlists.get(i).getName().equals(name))
                playlists.remove(playlists.get(i));
        }
    }
    
    public void removeSongFromPlaylist(Playlist playlist, Song song){
        if(playlist.remove(song)){
            VincentPlayer.logger.println("Removal successful");
        }else
            VincentPlayer.logger.println("Cannot remove " + song.toString());
    }
    
    public void removeAllSongs(){
        //TO-BE implemented
    }
    
    public void removeSong(String songName){
        for(int i = 0; i < songs.size(); i++){
            if(songs.get(i).getName().startsWith(songName)){
                VincentPlayer.logger.println("Removing " + songs.get(i));
                songs.get(i).fullClose();
                songs.remove(i);
                break;
            }
        }
    }
    
    public Song getRandomSong(){
        int randNum = (int)Math.round(Math.random()*((songs.size()-1)-1)+1);
        return songs.get(randNum);
    }
    
    public String getFileExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf("."), fileName.length());
    }
    
    public void autoNextInit(){
        new Thread(){
            public void run(){
                VincentPlayer.State state = playerState;
                while(true){
                    if(state.currentSong != null){
                        if(state.repeatforever){
                            if(state.currentSong.isReady() && state.currentSong.isFinished()){
                                state.currentSong.replay();
                            }
                        }else{
                            if(state.currentSong.isReady() && state.useAutoNext){
                                if(state.currentSong.isFinished()){
                                    state.currentSong.fullClose();
                                    if(state.usePlaylist && state.currentPlaylist != null){
                                        state.currentSong = state.currentPlaylist.continueToNewSong();
                                    }else if(songs.size() > 1){
                                        state.currentSong = getRandomSong();
                                    }
                                    state.currentSong.play();
                                }
                            }
                        }
                    }
                    try{
                        //Just to prevent the loop goes too fast and waste computer resources
                        Thread.sleep(5000);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        return;
    }

    public List<Song> getSongs(){
        return songs;
    }

    public List<Playlist> getPlaylists(){
        return playlists;
    }

    public VincentPlayer.State getState(){
        return playerState;
    }
}