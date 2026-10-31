package com.example.week8_day4.projection;

import com.example.week8_day4.model.TaskPriority;

public interface PriorityCount {
    TaskPriority getPriority();
    Long getTotal();
}
