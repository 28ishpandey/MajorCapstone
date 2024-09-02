package com.food.restaurant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RestaurantCreateDTO {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Pattern(regexp = ".*\\.com$", message = "Email should end with 'domain.com'")
    private String email;

    @NotBlank(message = "contact number cannot be blank")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String contactNumber;
    @NotBlank(message = "password cannot be blank")
    private String password;
    private String name;
    private String address;
}
