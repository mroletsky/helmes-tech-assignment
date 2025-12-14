package com.example.helmestechassignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidSectorException extends RuntimeException {
    public InvalidSectorException(String message) {
        super(message);
    }
}

