package com.tasktracker.tasktrackerapp.service;

import com.tasktracker.tasktrackerapp.dto.CreateUserRequest;
import com.tasktracker.tasktrackerapp.entity.User;
import com.tasktracker.tasktrackerapp.exception.ResourceNotFoundException;
import com.tasktracker.tasktrackerapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User createUser(CreateUserRequest request) {

        log.info("Creating user with name={}, email={}",
                request.name(), request.email());

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .build();

        User savedUser = userRepository.save(user);

        log.info("User created successfully with id={}",
                savedUser.getId());

        return savedUser;
    }

    public List<User> getAllUsers() {

        log.info("Fetching all users");

        List<User> users = userRepository.findAll();

        log.info("Total users found={}", users.size());

        return users;
    }

    public User getUser(Long id) {

        log.info("Fetching user with id={}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with id={}", id);
                    return new ResourceNotFoundException(
                            "User not found with id=" + id);
                });

        log.info("User found: id={}, email={}",
                user.getId(), user.getEmail());

        return user;
    }
}