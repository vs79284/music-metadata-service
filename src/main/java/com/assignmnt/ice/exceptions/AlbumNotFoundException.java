package com.assignmnt.ice.exceptions;

public class AlbumNotFoundException extends RuntimeException{
    public AlbumNotFoundException(String message){
        super(message);
    }
}
