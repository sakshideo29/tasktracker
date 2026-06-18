package com.tasktracker.tasktrackerapp.dto;

import java.time.LocalDate;

public record CreateTaskRequest(
        String title,
        String description,
        LocalDate dueDate,
        Long ownerId
) {
}