package com.food.user.dto;

import lombok.Data;


/**
 * Data Transfer Object (DTO) for user login requests.
 * Contains login credentials for authenticating a user.
 */
@Data
public class LoginDTO {

    /**
     * Email address of the user trying to log in.
     */
    private String email;

    /**
     * Password of the user trying to log in.
     */
    private String password;
}
