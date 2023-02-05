package com.example.springapp.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequesException extends RuntimeException {
    public BadRequesException(String message) {
        super(message);
    }
}
