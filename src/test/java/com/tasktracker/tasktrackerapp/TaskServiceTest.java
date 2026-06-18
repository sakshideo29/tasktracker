package com.tasktracker.tasktrackerapp;

import com.tasktracker.tasktrackerapp.dto.CreateTaskRequest;
import com.tasktracker.tasktrackerapp.dto.UpdateTaskRequest;
import com.tasktracker.tasktrackerapp.entity.Task;
import com.tasktracker.tasktrackerapp.entity.TaskStatus;
import com.tasktracker.tasktrackerapp.entity.User;
import com.tasktracker.tasktrackerapp.exception.ResourceNotFoundException;
import com.tasktracker.tasktrackerapp.repository.TaskRepository;
import com.tasktracker.tasktrackerapp.repository.UserRepository;
import com.tasktracker.tasktrackerapp.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    // ---------------- CREATE TASK ----------------
    @Test
    void createTask_shouldThrowUserNotFound() {

        CreateTaskRequest request = new CreateTaskRequest(
                "Title",
                "Desc",
                LocalDate.now(),
                1L
        );

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> taskService.createTask(request));
    }

    // ---------------- GET TASK NOT FOUND ----------------
    @Test
    void getTask_shouldThrowNotFound() {

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> taskService.getTask(1L));
    }

    // ---------------- UPDATE TASK NOT FOUND ----------------
    @Test
    void updateTask_shouldThrowNotFound() {

        UpdateTaskRequest request = new UpdateTaskRequest(
                "Title",
                "Desc",
                TaskStatus.DONE
        );

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> taskService.updateTask(1L, request));
    }

    // ---------------- DELETE TASK NOT FOUND ----------------
    @Test
    void deleteTask_shouldThrowNotFound() {

        when(taskRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> taskService.deleteTask(1L));
    }

    // ---------------- SUCCESS PATH (COVERS BRANCHES) ----------------
    @Test
    void createTask_shouldWork() {

        User user = User.builder().id(1L).build();

        CreateTaskRequest request = new CreateTaskRequest(
                "Title",
                "Desc",
                LocalDate.now(),
                1L
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArgument(0));

        Task result = taskService.createTask(request);

        assertNotNull(result);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void getAllTasks_shouldReturnList() {

        when(taskRepository.findAll()).thenReturn(List.of(new Task()));

        List<Task> result = taskService.getAllTasks();

        assertEquals(1, result.size());
    }

    @Test
    void getTasksByUser_shouldReturnList() {

        when(taskRepository.findByOwnerId(1L)).thenReturn(List.of(new Task()));

        List<Task> result = taskService.getTasksByUser(1L);

        assertEquals(1, result.size());
    }
}