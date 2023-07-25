package com.example.eventservice.Exceptions;

public class InvalidEventIdException extends RuntimeException {
    public InvalidEventIdException(String message) {
        super(message);
    }
}
