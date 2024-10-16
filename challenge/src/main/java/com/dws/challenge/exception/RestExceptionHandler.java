package com.dws.challenge.exception;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Centralized Rest based Exception handling 
 */
@ControllerAdvice
public class RestExceptionHandler {

     // Handle server error
     @ExceptionHandler(AccountNotFoundException.class)
     public ResponseEntity<Object> handleInvalidAccountIdException(DuplicateAccountIdException ex, WebRequest request) {
         return new ResponseEntity<>("Invalid Account Id", HttpStatus.BAD_REQUEST);
     }
   
     // Handle server error
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
    }

    // handle generic errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleCommonException(Exception ex, WebRequest request) {
        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
