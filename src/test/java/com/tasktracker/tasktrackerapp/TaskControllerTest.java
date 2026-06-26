package com.tasktracker.tasktrackerapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasktracker.tasktrackerapp.dto.CreateTaskRequest;
import com.tasktracker.tasktrackerapp.dto.UpdateTaskRequest;
import com.tasktracker.tasktrackerapp.entity.TaskStatus;
import com.tasktracker.tasktrackerapp.entity.User;
import com.tasktracker.tasktrackerapp.repository.TaskRepository;
import com.tasktracker.tasktrackerapp.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @AfterEach
    void cleanup() {
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    // ---------------- helper ----------------
    private User createUser() {
        User user = User.builder()
                .name("Test User")
                .email("user_" + UUID.randomUUID() + "@gmail.com")
                .build();
        return userRepository.save(user);
    }

    // ---------------- CREATE TASK ----------------
    @Test
    void createTaskShouldReturn201() throws Exception {

        User user = createUser();

        CreateTaskRequest request = new CreateTaskRequest(
                "Learn Spring Boot",
                "Practice CRUD",
                LocalDate.now().plusDays(5),
                user.getId()
        );

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Learn Spring Boot"));
    }

    // ---------------- GET ALL TASKS ----------------
    @Test
    void getAllTasksShouldReturn200() throws Exception {

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk());
    }

    // ---------------- GET TASK BY ID ----------------
    @Test
    void getTaskByIdShouldReturn200() throws Exception {

        User user = createUser();

        CreateTaskRequest request = new CreateTaskRequest(
                "Task 1",
                "Desc",
                LocalDate.now().plusDays(3),
                user.getId()
        );

        String response = mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long taskId = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/api/tasks/" + taskId))
                .andExpect(status().isOk());
    }

    // ---------------- UPDATE TASK ----------------
    @Test
    void updateTaskShouldReturn200() throws Exception {

        User user = createUser();

        CreateTaskRequest createRequest = new CreateTaskRequest(
                "Old Task",
                "Old Desc",
                LocalDate.now().plusDays(3),
                user.getId()
        );

        String response = mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long taskId = objectMapper.readTree(response).get("id").asLong();

        UpdateTaskRequest updateRequest = new UpdateTaskRequest(
                "Updated Task",
                "Updated Desc",
                TaskStatus.DONE
        );

        mockMvc.perform(put("/api/tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"));
    }

    // ---------------- DELETE TASK ----------------
    @Test
    void deleteTaskShouldReturn204() throws Exception {

        User user = createUser();

        CreateTaskRequest request = new CreateTaskRequest(
                "Delete Task",
                "Desc",
                LocalDate.now().plusDays(3),
                user.getId()
        );

        String response = mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long taskId = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/api/tasks/" + taskId))
                .andExpect(status().isNoContent());
    }
}