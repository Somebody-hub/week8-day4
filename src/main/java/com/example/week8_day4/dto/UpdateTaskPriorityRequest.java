package com.example.week8_day4.dto;

import com.example.week8_day4.model.TaskPriority;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskPriorityRequest(
        @NotNull(message = "Priority is required")
        TaskPriority priority) {
}
