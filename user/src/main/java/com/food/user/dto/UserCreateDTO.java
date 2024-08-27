package com.food.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserCreateDTO {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Pattern(regexp = ".*\\.com$", message = "Email should end with '.com'")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String contactNumber;

    private String password;

    private String firstName;

    private String lastName;
}
