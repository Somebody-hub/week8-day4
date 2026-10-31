package com.example.week8_day4.model;

public enum TaskStatus {
    NEW("New"),
    IN_PROGRESS("In progress"),
    DONE("Done"),
    CANCELED("Canceled");

    private final String taskStatusTitle;

    TaskStatus(String taskStatusTitle) {
        this.taskStatusTitle = taskStatusTitle;
    }

    public String getTaskStatusTitle() {
        return taskStatusTitle;
    }
}
