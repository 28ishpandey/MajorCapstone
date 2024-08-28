package com.food.user.exception;

public class AddressNotFoundException extends RuntimeException{
    public AddressNotFoundException(){
        super("Address not found");
    }
}
