package com.food.user.dto;

import lombok.Data;

@Data
public class UserCreateDTO {
    private String email;
    private String contactNumber;
    private String password;
    private String firstName;
    private String lastName;
}
