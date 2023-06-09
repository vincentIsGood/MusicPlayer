package com.vincentcodes.vincentplayer.exceptions;

public class InvalidInputException extends Exception{
    private static final long serialVersionUID = 1519698411719902131L;

    public InvalidInputException(String msg) {
        super(msg);
    }
    
    public InvalidInputException(String msg, Throwable cause){
        super(msg, cause);
    }
}