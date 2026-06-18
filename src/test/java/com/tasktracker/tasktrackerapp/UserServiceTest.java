package com.tasktracker.tasktrackerapp;


import com.tasktracker.tasktrackerapp.dto.CreateUserRequest;
import com.tasktracker.tasktrackerapp.entity.User;
import com.tasktracker.tasktrackerapp.exception.ResourceNotFoundException;
import com.tasktracker.tasktrackerapp.repository.UserRepository;
import com.tasktracker.tasktrackerapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    // ---------------- CREATE USER ----------------
    @Test
    void createUser_shouldSaveUser() {

        CreateUserRequest request = new CreateUserRequest(
                "Rahul",
                "rahul@gmail.com"
        );

        when(userRepository.save(any(User.class)))
                .thenAnswer(i -> i.getArgument(0));

        User result = userService.createUser(request);

        assertNotNull(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    // ---------------- GET ALL USERS ----------------
    @Test
    void getAllUsers_shouldReturnList() {

        when(userRepository.findAll()).thenReturn(List.of(new User()));

        List<User> result = userService.getAllUsers();

        assertEquals(1, result.size());
    }

    // ---------------- GET USER - SUCCESS ----------------
    @Test
    void getUser_shouldReturnUser() {

        User user = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        User result = userService.getUser(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    // ---------------- GET USER - NOT FOUND ----------------
    @Test
    void getUser_shouldThrowException_whenNotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.getUser(1L));
    }
}
