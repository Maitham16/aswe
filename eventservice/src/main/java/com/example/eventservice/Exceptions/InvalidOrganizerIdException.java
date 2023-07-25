package com.example.eventservice.Exceptions;

public class InvalidOrganizerIdException extends RuntimeException {
    public InvalidOrganizerIdException(String message) {
        super(message);
    }
}
