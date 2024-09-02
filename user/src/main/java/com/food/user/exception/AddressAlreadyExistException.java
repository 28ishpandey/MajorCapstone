package com.food.user.exception;

public class AddressAlreadyExistException extends RuntimeException{
  public AddressAlreadyExistException(){
    super("This Address title already exist");
  }
}
