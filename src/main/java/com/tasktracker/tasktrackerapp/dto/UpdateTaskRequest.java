package com.tasktracker.tasktrackerapp.dto;
import com.tasktracker.tasktrackerapp.entity.TaskStatus;

public record UpdateTaskRequest(
        String title,
        String description,
        TaskStatus status
) {
}