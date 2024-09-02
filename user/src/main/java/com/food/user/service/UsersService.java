package com.food.user.service;

import com.food.user.dto.ForgotPasswordDTO;
import com.food.user.dto.LoginDTO;
import com.food.user.dto.UserCreateDTO;
import com.food.user.dto.UserResponseDTO;
import com.food.user.entity.Users;
import com.food.user.exception.AccountExistException;
import com.food.user.exception.AccountNotFoundException;
import com.food.user.exception.InvalidCredentials;
import com.food.user.repository.UserRepository;
import com.food.user.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing user operations such as creation, retrieval, update, deletion, and authentication.
 */
@Slf4j
@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    /**
     * Creates a new user in the system.
     *
     * @param userCreateDTO the user details for creation
     * @return the created user details
     */

    public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
        log.info("Creating user with email: {}", userCreateDTO.getEmail());
        Optional<Users> existingUser = userRepository.findByEmail(userCreateDTO.getEmail());
        if (existingUser.isEmpty()) {
            Users user = new Users();
            user.setEmail(userCreateDTO.getEmail());
            user.setContactNumber(userCreateDTO.getContactNumber());
            String encryptedPassword = PasswordUtil.encryptPassword(userCreateDTO.getPassword());
            user.setPassword(encryptedPassword);
            user.setFirstName(userCreateDTO.getFirstName());
            user.setLastName(userCreateDTO.getLastName());
            user.setWalletBalance(1000.0);

            Users savedUser = userRepository.save(user);
            log.info("User created with ID: {}", savedUser.getUserId());
            return convertToResponseDTO(savedUser);
        }
        throw new AccountExistException();
    }
    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user
     * @return the user details
     */
    public UserResponseDTO getUserById(Long userId) {
        log.info("Fetching user with ID: {}", userId);
        Users user = userRepository.findById(userId)
                .orElseThrow(AccountNotFoundException::new);
        return convertToResponseDTO(user);
    }

    /**
     * Retrieves all users in the system.
     *
     * @return a list of user details
     */
    public List<UserResponseDTO> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates a user's details.
     *
     * @param userId        the ID of the user to update
     * @param userCreateDTO the updated user details
     * @return the updated user details
     */

    public UserResponseDTO updateUser(Long userId, UserCreateDTO userCreateDTO) {
        log.info("Updating user with ID: {}", userId);
        Users user = userRepository.findById(userId)
                .orElseThrow(AccountNotFoundException::new);

        user.setEmail(userCreateDTO.getEmail());
        user.setContactNumber(userCreateDTO.getContactNumber());
        user.setPassword(userCreateDTO.getPassword());
        user.setFirstName(userCreateDTO.getFirstName());
        user.setLastName(userCreateDTO.getLastName());

        Users savedUser = userRepository.save(user);
        log.info("User updated with ID: {}", savedUser.getUserId());
        return convertToResponseDTO(savedUser);
    }
    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to delete
     */
    public void deleteUser(Long userId) {
        log.info("Deleting user with ID: {}", userId);
        Users user = userRepository.findById(userId)
                .orElseThrow(AccountNotFoundException::new);
        userRepository.delete(user);
        log.info("User deleted with ID: {}", userId);
    }

    /**
     * Authenticates a user.
     *
     * @param loginDTO the login credentials
     * @return the user details if authentication is successful
     */
    public UserResponseDTO login(LoginDTO loginDTO) {
        log.info("Attempting login for email: {}", loginDTO.getEmail());
        Users user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(AccountNotFoundException::new);

        String decryptedPassword = PasswordUtil.decryptPassword(user.getPassword());

        if (!decryptedPassword.equals(loginDTO.getPassword())) {
            throw new InvalidCredentials();
        }

        log.info("Login successful for user ID: {}", user.getUserId());
        return convertToResponseDTO(user);
    }

    /**
     * Handles forgot password requests by sending a reset email.
     *
     * @param forgotPasswordDTO the details for forgot password
     */
    public void forgotPassword(ForgotPasswordDTO forgotPasswordDTO) {
        log.info("Processing forgot password for email: {}", forgotPasswordDTO.getEmail());
        Users user = userRepository.findByEmail(forgotPasswordDTO.getEmail())
                .orElseThrow(AccountNotFoundException::new);

        String decryptedPassword = PasswordUtil.decryptPassword(user.getPassword());

        sendForgotPasswordEmail(user.getEmail(), decryptedPassword);
        log.info("Forgot password email sent successfully");
    }

    /**
     * Sends a reset password email to the user.
     *
     * @param email the email address to send the reset email to
     */
    private void sendForgotPasswordEmail(String email, String password) {
        log.info("Sending forgot password email to: {}", email);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Forgot Password");
        message.setText("Your password is: " + password);

        javaMailSender.send(message);
    }

    /**
     * Changes the user's password.
     *
     * @param userId      the ID of the user
     * @param newPassword the new password
     * @return the updated user details
     */

    public UserResponseDTO changePassword(Long userId, String newPassword) {
        log.info("Changing password for user ID: {}", userId);
        Users user = userRepository.findById(userId)
                .orElseThrow(AccountNotFoundException::new);

        String encryptedPassword = PasswordUtil.encryptPassword(newPassword);
        user.setPassword(encryptedPassword);

        Users updatedUser = userRepository.save(user);
        log.info("Password changed successfully for user ID: {}", userId);
        return convertToResponseDTO(updatedUser);
    }

    private UserResponseDTO convertToResponseDTO(Users user) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setUserId(user.getUserId());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setContactNumber(user.getContactNumber());
        responseDTO.setFirstName(user.getFirstName());
        responseDTO.setLastName(user.getLastName());
        responseDTO.setWalletBalance(user.getWalletBalance());
        return responseDTO;
    }
}