package com.food.user.dto;


import lombok.Data;


@Data
public class UserResponseDTO {
    private Long userId;
    private String email;
    private String contactNumber;
    private String firstName;
    private String lastName;
    private Double walletBalance;
}
