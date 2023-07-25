package com.example.eventservice.Controllers;

import com.example.eventservice.Exceptions.EventNotFoundException;
import com.example.eventservice.Exceptions.RabbitMQException;
import com.example.eventservice.Exceptions.EventServiceException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EventControllerAdvice {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<Object> handleEventNotFoundException(EventNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RabbitMQException.class)
    public ResponseEntity<Object> handleRabbitMQException(RabbitMQException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EventServiceException.class)
    public ResponseEntity<Object> handleEventServiceException(EventServiceException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
