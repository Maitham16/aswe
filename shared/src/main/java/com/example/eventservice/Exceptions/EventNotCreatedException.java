package com.example.eventservice.Exceptions;

public class EventNotCreatedException extends RuntimeException {
    public EventNotCreatedException(String message) {
        super(message);
    }
}
