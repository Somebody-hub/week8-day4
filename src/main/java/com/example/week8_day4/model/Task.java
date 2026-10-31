package com.example.week8_day4.model;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30, name = "task_priority")
    private TaskPriority taskPriority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30, name = "task_status")
    private TaskStatus taskStatus;

    @ElementCollection
    @CollectionTable(name = "task_tags", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "tag", nullable = false)
    private Set<String> tags = new HashSet<>();

    protected Task() {}

    public Task(String title, String description, TaskPriority taskPriority) {
        setTitle(title);
        this.description = description;
        setTaskPriority(taskPriority);
        setTaskStatus(TaskStatus.NEW);

    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title cannot be empty or null");
        }
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        if (taskPriority == null) {
            throw new IllegalArgumentException("Task priority cannot be null");
        }
        this.taskPriority = taskPriority;
    }

    public void setTaskStatus(TaskStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Task status cannot be null");
        }
        this.taskStatus = status;
    }

    public void addTag(String tag) {
        if (tag.isBlank()) {
            throw new IllegalArgumentException("The tag cannot be empty");
        }
        this.tags.add(tag.trim().toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o instanceof Task task){
            return Objects.equals(task.id, id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
