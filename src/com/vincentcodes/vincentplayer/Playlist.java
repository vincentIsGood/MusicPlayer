package com.vincentcodes.vincentplayer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Playlist{
    protected final List<Song> playlist; // songs
    private List<Integer> sequence; // play sequence (stores indexes of the playlist)
    
    private int previousIndex = 0;
    private int sequenceIndex = -1;
    private String name;
    
    public Playlist(String name){
        this.name = name;
        this.playlist = new ArrayList<>();
        this.sequence = new ArrayList<>();
    }
    
    //duplicating a playlist
    public Playlist(String name, LinkedList<Song> playlist){
        this.name = name;
        this.playlist = playlist;
        this.sequence = new ArrayList<>();
        arrange();
    }
    
    /**
     * Append a song
     */
    public void add(Song song){
        playlist.add(song);
        arrange();
    }
    /**
     * Append songs
     */
    public void addSongs(List<Song> songs){
        for(Song song : songs)
            playlist.add(song);
        arrange();
    }
    public void addSongs(Playlist playlist){
        addSongs(playlist.playlist);
    }
    
    /**
     * insert *after* the element at [index]
     */
    public void add(int index, Song song){
        playlist.add(index, song);
        arrange();
    }
    
    /**
     * Removes the first occurance of the song
     */
    public boolean remove(Song song){
        boolean result = playlist.remove(song);
        arrange();
        return result;
    }
    
    public Song getSong(int index){
        return playlist.get(index);
    }
    
    public Song getPreviousSong(){
        return getSong(sequence.get(previousIndex));
    }
    
    public Song getCurrentSong(){
        return getSong(sequence.get(sequenceIndex));
    }
    
    public Song getUpComingSong(){
        return getSong(sequence.get(getNextNumber()));
    }
    
    public String getName(){
        return this.name;
    }
    
    public int getNextNumber(){
        return (sequenceIndex + 1) % playlist.size();
    }
    
    public int getLength(){
        return playlist.size();
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public Song reverseToPreviousSong(){
        sequenceIndex = (sequenceIndex -1) < 0? playlist.size()-1 : (sequenceIndex -1);
        return getSong(sequence.get(sequenceIndex));
    }
    
    public Song continueToNewSong(){
        previousIndex = sequenceIndex == -1 ? 0 : sequenceIndex;
        sequenceIndex = getNextNumber();
        return getSong(sequence.get(sequenceIndex));
    }
    
    /**
     * Arrange the songs to the original sequence, ie. The 
     * first one being added into the playlist has an index 0
     */
    public void arrange(){
        sequence.clear();
        for(int i = 0; i < playlist.size(); i++)
            sequence.add(i);
    }
    
    public void shuffle(){
        arrange();
        List<Integer> tmpSequence = new ArrayList<>(sequence);
        sequence.clear();
        
        int tmp = 0;
        for(int i = 0; i < playlist.size(); i++){
            tmp = getRandomElement(tmpSequence);
            sequence.add(tmpSequence.get(tmp));
            tmpSequence.remove(tmp);
        }
        
        if(tmpSequence.size() == 0)
            VincentPlayer.logger.println("Shuffling Is Done!");
        else
            VincentPlayer.logger.println("Shuffling Has Failed!");
        
        tmpSequence = null;
    }

    public void clear(){
        playlist.clear();
        sequence.clear();
        previousIndex = 0;
        sequenceIndex = -1;
    }
    
    // Range: 1 to array.length
    private <T> int getRandomElement(List<T> array){
        int randNum = (int)Math.floor(Math.random()*array.size());
        return randNum;
    }
    
    public void printPlaylist(){
        VincentPlayer.logger.println("Length: " + playlist.size());
        if(playlist.size() > 0){
                VincentPlayer.logger.println("Seq #     Song name");
            for(int i = 0; i < playlist.size(); i++){
                VincentPlayer.logger.println(String.format("%d: %s", i+1, this.getSong(sequence.get(i)).getName()));
            }
        }
    }
    
    public String toString(){
        return this.name;
    }
}