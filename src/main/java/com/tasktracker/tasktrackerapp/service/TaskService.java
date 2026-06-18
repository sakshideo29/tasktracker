package com.tasktracker.tasktrackerapp.service;

import com.tasktracker.tasktrackerapp.dto.CreateTaskRequest;
import com.tasktracker.tasktrackerapp.dto.UpdateTaskRequest;
import com.tasktracker.tasktrackerapp.entity.Task;
import com.tasktracker.tasktrackerapp.entity.TaskStatus;
import com.tasktracker.tasktrackerapp.entity.User;
import com.tasktracker.tasktrackerapp.exception.ResourceNotFoundException;
import com.tasktracker.tasktrackerapp.repository.TaskRepository;
import com.tasktracker.tasktrackerapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Task createTask(CreateTaskRequest request) {

        log.info("Creating task for ownerId={}, title={}",
                request.ownerId(), request.title());

        User owner = userRepository
                .findById(request.ownerId())
                .orElseThrow(() -> {
                    log.error("User not found with id={}", request.ownerId());
                    return new ResourceNotFoundException(
                            "User not found with id=" + request.ownerId());
                });

        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .dueDate(request.dueDate())
                .status(TaskStatus.TODO)
                .createdAt(LocalDateTime.now())
                .owner(owner)
                .build();

        Task savedTask = taskRepository.save(task);

        log.info("Task created successfully with id={}, ownerId={}",
                savedTask.getId(), owner.getId());

        return savedTask;
    }

    public List<Task> getAllTasks() {

        log.info("Fetching all tasks");

        List<Task> tasks = taskRepository.findAll();

        log.info("Total tasks found={}", tasks.size());

        return tasks;
    }

    public Task getTask(Long id) {

        log.info("Fetching task with id={}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Task not found with id={}", id);
                    return new ResourceNotFoundException(
                            "Task not found with id=" + id);
                });

        log.info("Task found: id={}, title={}",
                task.getId(), task.getTitle());

        return task;
    }

    public List<Task> getTasksByUser(Long userId) {

        log.info("Fetching tasks for userId={}", userId);

        List<Task> tasks = taskRepository.findByOwnerId(userId);

        log.info("Tasks found for userId={} count={}",
                userId, tasks.size());

        return tasks;
    }

    public Task updateTask(Long id, UpdateTaskRequest request) {

        log.info("Updating task id={}", id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Task not found for update id={}", id);
                    return new ResourceNotFoundException(
                            "Task not found with id=" + id);
                });

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());

        Task updatedTask = taskRepository.save(task);

        log.info("Task updated successfully id={}",
                updatedTask.getId());

        return updatedTask;
    }

    public void deleteTask(Long id) {

        log.info("Deleting task id={}", id);

        if (!taskRepository.existsById(id)) {
            log.error("Task not found for deletion id={}", id);
            throw new ResourceNotFoundException(
                    "Task not found with id=" + id);
        }

        taskRepository.deleteById(id);

        log.info("Task deleted successfully id={}", id);
    }
}