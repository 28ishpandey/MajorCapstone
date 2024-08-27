package com.food.user.userTests;

import com.food.user.dto.UserResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserResponseDTOTest {
    @Test
    public void testUserResponseDTO() {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUserId(1L);
        userResponseDTO.setEmail("test@example.com");
        userResponseDTO.setContactNumber("1234567890");
        userResponseDTO.setFirstName("Ish");
        userResponseDTO.setLastName("Pandey");
        userResponseDTO.setWalletBalance(1000.0);

        assertEquals(1L, userResponseDTO.getUserId());
        assertEquals("test@example.com", userResponseDTO.getEmail());
        assertEquals("1234567890", userResponseDTO.getContactNumber());
        assertEquals("Ish", userResponseDTO.getFirstName());
        assertEquals("Pandey", userResponseDTO.getLastName());
        assertEquals(1000.0, userResponseDTO.getWalletBalance());
    }
}
