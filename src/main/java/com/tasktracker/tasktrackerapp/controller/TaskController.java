package com.tasktracker.tasktrackerapp.controller;

import com.tasktracker.tasktrackerapp.dto.CreateTaskRequest;
import com.tasktracker.tasktrackerapp.dto.UpdateTaskRequest;
import com.tasktracker.tasktrackerapp.entity.Task;
import com.tasktracker.tasktrackerapp.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // CREATE TASK → 201
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody CreateTaskRequest request) {

        log.info("Received create task request title={}, ownerId={}",
                request.title(), request.ownerId());

        Task createdTask = taskService.createTask(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    // GET ALL TASKS → 200
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {

        log.info("Fetching all tasks");

        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // GET TASK BY ID → 200
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {

        log.info("Fetching task id={}", id);

        return ResponseEntity.ok(taskService.getTask(id));
    }

    // UPDATE TASK → 200
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody UpdateTaskRequest request) {

        log.info("Updating task id={}", id);

        return ResponseEntity.ok(taskService.updateTask(id, request));
    }

    // DELETE TASK → 204
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {

        log.info("Deleting task id={}", id);

        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }

    // GET TASKS BY USER → 200
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable Long userId) {

        log.info("Fetching tasks for userId={}", userId);

        return ResponseEntity.ok(taskService.getTasksByUser(userId));
    }
}