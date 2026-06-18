package com.tasktracker.tasktrackerapp.repository;

import com.tasktracker.tasktrackerapp.entity.Task;
import com.tasktracker.tasktrackerapp.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository
        extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByOwnerId(Long ownerId);
}