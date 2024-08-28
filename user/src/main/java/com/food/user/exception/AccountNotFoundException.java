package com.food.user.exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(){
        super("Account not found");
    }
}
