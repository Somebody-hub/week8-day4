package com.example.week8_day4.dto;

import com.example.week8_day4.model.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskStatusRequest(
        @NotNull(message = "Status is required")
        TaskStatus status) {
}
