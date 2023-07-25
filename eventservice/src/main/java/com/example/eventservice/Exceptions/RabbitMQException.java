package com.example.eventservice.Exceptions;


public class RabbitMQException extends RuntimeException {
    public RabbitMQException(String message, Throwable cause) {
        super(message, cause);
    }
}