package com.vincentcodes.vincentplayer;

public class SongIdCounter {
    private int nextSongId = 0;

    public int getNextSongId() {
        return nextSongId++;
    }
}
