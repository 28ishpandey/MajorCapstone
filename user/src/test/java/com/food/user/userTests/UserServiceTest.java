package com.food.user.userTests;

import com.food.user.dto.UserCreateDTO;
import com.food.user.dto.UserResponseDTO;
import com.food.user.entity.Users;
import com.food.user.exception.CustomException;
import com.food.user.repository.UserRepository;
import com.food.user.service.UsersService;
import com.food.user.util.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @InjectMocks
    private UsersService usersService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("test@example.com");
        userCreateDTO.setContactNumber("1234567890");
        userCreateDTO.setPassword("password");
        userCreateDTO.setFirstName("John");
        userCreateDTO.setLastName("Doe");

        Users user = new Users();
        user.setUserId(1L);
        user.setEmail(userCreateDTO.getEmail());
        user.setContactNumber(userCreateDTO.getContactNumber());
        user.setPassword(PasswordUtil.encryptPassword(userCreateDTO.getPassword()));
        user.setFirstName(userCreateDTO.getFirstName());
        user.setLastName(userCreateDTO.getLastName());
        user.setWalletBalance(1000.0);

        when(userRepository.save(any(Users.class))).thenReturn(user);

        UserResponseDTO responseDTO = usersService.createUser(userCreateDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getUserId());
        assertEquals("test@example.com", responseDTO.getEmail());
        assertEquals("1234567890", responseDTO.getContactNumber());
        assertEquals("John", responseDTO.getFirstName());
        assertEquals("Doe", responseDTO.getLastName());
        assertEquals(1000.0, responseDTO.getWalletBalance());
    }

    @Test
    public void testGetUserById() {
        Users user = new Users();
        user.setUserId(1L);
        user.setEmail("test@example.com");
        user.setContactNumber("1234567890");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setWalletBalance(1000.0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDTO responseDTO = usersService.getUserById(1L);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getUserId());
        assertEquals("test@example.com", responseDTO.getEmail());
        assertEquals("1234567890", responseDTO.getContactNumber());
        assertEquals("John", responseDTO.getFirstName());
        assertEquals("Doe", responseDTO.getLastName());
        assertEquals(1000.0, responseDTO.getWalletBalance());
    }

    @Test
    public void testGetUserById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            usersService.getUserById(1L);
        });

        assertEquals("User not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), exception.getStatusCode());
    }

    @Test
    public void testGetAllUsers() {
        Users user1 = new Users();
        user1.setUserId(1L);
        user1.setEmail("user1@example.com");
        user1.setContactNumber("1234567890");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setWalletBalance(1000.0);

        Users user2 = new Users();
        user2.setUserId(2L);
        user2.setEmail("user2@example.com");
        user2.setContactNumber("0987654321");
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setWalletBalance(1000.0);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserResponseDTO> allUsers = usersService.getAllUsers();

        assertEquals(2, allUsers.size());
        assertEquals("user1@example.com", allUsers.get(0).getEmail());
        assertEquals("user2@example.com", allUsers.get(1).getEmail());
    }

    @Test
    public void testUpdateUser() {
        UserCreateDTO userCreateDTO = new UserCreateDTO();
        userCreateDTO.setEmail("updated@example.com");
        userCreateDTO.setContactNumber("0987654321");
        userCreateDTO.setPassword("newpassword");
        userCreateDTO.setFirstName("Updated");
        userCreateDTO.setLastName("User");

        Users existingUser = new Users();
        existingUser.setUserId(1L);
        existingUser.setEmail("old@example.com");
        existingUser.setContactNumber("1234567890");
        existingUser.setPassword("oldpassword");
        existingUser.setFirstName("Old");
        existingUser.setLastName("User");

        Users updatedUser = new Users();
        updatedUser.setUserId(1L);
        updatedUser.setEmail("updated@example.com");
        updatedUser.setContactNumber("0987654321");
        updatedUser.setPassword(userCreateDTO.getPassword());
        updatedUser.setFirstName("Updated");
        updatedUser.setLastName("User");
        updatedUser.setWalletBalance(1000.0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(Users.class))).thenReturn(updatedUser);

        UserResponseDTO responseDTO = usersService.updateUser(1L, userCreateDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getUserId());
        assertEquals("updated@example.com", responseDTO.getEmail());
        assertEquals("0987654321", responseDTO.getContactNumber());
        assertEquals("Updated", responseDTO.getFirstName());
        assertEquals("User", responseDTO.getLastName());
    }

    @Test
    public void testDeleteUser() {
        Users user = new Users();
        user.setUserId(1L);
        user.setEmail("test@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        usersService.deleteUser(1L);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> {
            usersService.deleteUser(1L);
        });

        assertEquals("User not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), exception.getStatusCode());
    }
}
