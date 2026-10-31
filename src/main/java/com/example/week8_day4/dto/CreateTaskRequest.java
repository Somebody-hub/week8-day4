package com.example.week8_day4.dto;

import com.example.week8_day4.model.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTaskRequest(
        @NotBlank(message = "Title is required and cannot be empty")
        @Size(max = 100, message = "Title cannot exceed 100 characters")
        String title,

        String description,

        @NotNull(message = "Priority is required")
        TaskPriority priority) {
}
