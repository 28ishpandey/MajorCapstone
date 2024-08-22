package com.food.user.error;

import com.food.user.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalExceptionHandler {
    // Handle CustomException
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), ex.getStatusCode()), HttpStatus.valueOf(ex.getStatusCode()));
    }

    // Handle Generic Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
