package com.food.user.userTests;

import com.food.user.dto.UserCreateDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserCreateDTOTest {
    @Test
    public void testUserCreateDTO() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("test@example.com");
        userCreateDTO.setContactNumber("1234567890");
        userCreateDTO.setPassword("password");
        userCreateDTO.setFirstName("Ish");
        userCreateDTO.setLastName("Pandey");

        assertEquals("test@example.com", userCreateDTO.getEmail());
        assertEquals("1234567890", userCreateDTO.getContactNumber());
        assertEquals("password", userCreateDTO.getPassword());
        assertEquals("Ish", userCreateDTO.getFirstName());
        assertEquals("Pandey", userCreateDTO.getLastName());
    }
}
