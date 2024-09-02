package com.food.restaurant.error;

/**
 * Custom exception for account not found.
 */
public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException() {
        super("Account not found");
    }
}
