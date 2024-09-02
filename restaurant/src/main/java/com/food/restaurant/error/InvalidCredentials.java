package com.food.restaurant.error;

/**
 * Custom exception for invalid credentials.
 */
public class InvalidCredentials extends RuntimeException {
    public InvalidCredentials() {
        super("Invalid credentials");
    }
}
