package com.vincentcodes.vincentplayer.exceptions;

public class SongNotSupportedException extends Exception{
    private static final long serialVersionUID = -282700816280592714L;

    public SongNotSupportedException(String msg) {
        super(msg);
    }
    
    public SongNotSupportedException(String msg, Throwable cause){
        super(msg, cause);
    }
}