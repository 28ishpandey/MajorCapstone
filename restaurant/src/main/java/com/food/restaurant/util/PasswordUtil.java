package com.food.restaurant.util;


import java.util.Base64;

public class PasswordUtil {

    public static String encryptPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public static String decryptPassword(String encryptedPassword) {
        return new String(Base64.getDecoder().decode(encryptedPassword));
    }
}
