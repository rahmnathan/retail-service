package com.github.rahmnathan.retail.redsky.data.exception;

public class RedSkyServiceException extends Exception {

    public RedSkyServiceException(String message){
        super(message);
    }

    public RedSkyServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
