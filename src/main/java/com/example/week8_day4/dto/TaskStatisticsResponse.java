package com.example.week8_day4.dto;

import com.example.week8_day4.model.TaskPriority;
import com.example.week8_day4.model.TaskStatus;

import java.util.Map;

public record TaskStatisticsResponse(
        Map<TaskStatus, Long> byStatus,
        Map<TaskPriority, Long> byPriority
) {
}
