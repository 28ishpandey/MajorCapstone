package com.food.restaurant.error;

public class RestaurantExistException extends RuntimeException {
    public RestaurantExistException() {
        super("Account already exists");
    }
}
