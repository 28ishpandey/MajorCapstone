package com.food.user.util;


import java.util.Base64;

/**
 * Utility class for password encryption and decryption.
 */
public class PasswordUtil {

    /**
     * Encrypts a password using Base64 encoding.
     *
     * @param password the password to encrypt
     * @return the encrypted password
     */
    public static String encryptPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    /**
     * Decrypts an encrypted password using Base64 decoding.
     *
     * @param encryptedPassword the encrypted password
     * @return the decrypted password
     */
    public static String decryptPassword(String encryptedPassword) {
        return new String(Base64.getDecoder().decode(encryptedPassword));
    }
}