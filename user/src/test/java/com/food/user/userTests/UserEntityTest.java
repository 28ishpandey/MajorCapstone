package com.food.user.userTests;

import com.food.user.entity.Users;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserEntityTest {
    @Test
    public void testUserCreation() {
        Users user = new Users();
        user.setEmail("test@example.com");
        user.setContactNumber("1234567890");
        user.setPassword("password");
        user.setFirstName("iSH");
        user.setLastName("pandey");
        user.setWalletBalance(1000.0);

        assertEquals("test@example.com", user.getEmail());
        assertEquals("1234567890", user.getContactNumber());
        assertEquals("password", user.getPassword());
        assertEquals("iSH", user.getFirstName());
        assertEquals("pandey", user.getLastName());
        assertEquals(1000.0, user.getWalletBalance());
    }
}
