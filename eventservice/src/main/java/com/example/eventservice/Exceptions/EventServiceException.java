package com.example.eventservice.Exceptions;

public class EventServiceException extends RuntimeException {
    public EventServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}