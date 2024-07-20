package com.fsoft.lecture5.lecture5.exception;

public class NoSuchEmployeeExistsException extends RuntimeException{
    public NoSuchEmployeeExistsException(String message) {
        super(message);
    }
}
