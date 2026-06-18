package com.tasktracker.tasktrackerapp.controller;

import com.tasktracker.tasktrackerapp.dto.CreateUserRequest;
import com.tasktracker.tasktrackerapp.entity.User;
import com.tasktracker.tasktrackerapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // CREATE USER → 201
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {

        log.info("Received request to create user: name={}, email={}",
                request.name(), request.email());

        User createdUser = userService.createUser(request);

        log.info("User created successfully with id={}", createdUser.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // GET ALL USERS → 200
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        log.info("Fetching all users");

        List<User> users = userService.getAllUsers();

        log.info("Total users found: {}", users.size());

        return ResponseEntity.ok(users);
    }

    // GET USER BY ID → 200
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {

        log.info("Fetching user with id={}", id);

        User user = userService.getUser(id);

        log.info("User fetched successfully: id={}, email={}",
                user.getId(), user.getEmail());

        return ResponseEntity.ok(user);
    }
}