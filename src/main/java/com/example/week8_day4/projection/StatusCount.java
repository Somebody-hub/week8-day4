package com.example.week8_day4.projection;

import com.example.week8_day4.model.TaskStatus;

public interface StatusCount {
    TaskStatus getStatus();
    Long getTotal();
}
