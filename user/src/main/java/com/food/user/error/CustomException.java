package com.food.user.error;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {
    private String message;
    private int statusCode;

    public CustomException(String message, int statusCode) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }
}
