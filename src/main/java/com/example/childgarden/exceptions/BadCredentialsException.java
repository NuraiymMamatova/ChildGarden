package com.example.childgarden.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.FORBIDDEN)
public class BadCredentialsException extends RuntimeException {

    public BadCredentialsException() {
        super();
    }

    public BadCredentialsException(String message) {
        super(message);
    }
}