package com.vladislavgolovkin.taskfromnatlex.exception_handling;

public class NoSuchSectionException extends RuntimeException{
    public NoSuchSectionException(String message) {
        super(message);
    }

}
