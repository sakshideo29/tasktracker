package com.tasktracker.tasktrackerapp.dto;

public record CreateUserRequest(
        String name,
        String email
) {
}
