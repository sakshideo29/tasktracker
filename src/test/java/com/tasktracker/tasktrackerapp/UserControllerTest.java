package com.tasktracker.tasktrackerapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasktracker.tasktrackerapp.entity.User;
import com.tasktracker.tasktrackerapp.repository.TaskRepository;
import com.tasktracker.tasktrackerapp.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    // ---------------- CLEANUP ----------------
    @AfterEach
    void cleanup() {
        taskRepository.deleteAll();   // child first
        userRepository.deleteAll();   // parent later
    }

    // ---------------- CREATE USER ----------------
    @Test
    void createUserShouldReturn201() throws Exception {

        Map<String, Object> request = new HashMap<>();
        request.put("name", "Rahul");
        request.put("email", "rahul_" + UUID.randomUUID() + "@gmail.com");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Rahul"));
    }

    // ---------------- GET USERS ----------------
    @Test
    void getAllUsersShouldReturn200() throws Exception {

        User user = User.builder()
                .name("Test User")
                .email("test_" + UUID.randomUUID() + "@gmail.com")
                .build();

        userRepository.save(user);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }

    // ---------------- GET USER BY ID ----------------
    @Test
    void getUserByIdShouldReturn200() throws Exception {

        User user = User.builder()
                .name("Test User")
                .email("test_" + UUID.randomUUID() + "@gmail.com")
                .build();

        user = userRepository.save(user);

        mockMvc.perform(get("/api/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()));
    }
}