package com.example.week8_day4.controller;

import com.example.week8_day4.dto.*;
import com.example.week8_day4.model.TaskPriority;
import com.example.week8_day4.model.TaskStatus;
import com.example.week8_day4.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(TaskController.BASE_PATH)
public class TaskController {
    public static final String BASE_PATH = "/api/tasks";
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) String tag
    ) {
        return ResponseEntity.ok(taskService.getTasks(status, priority, tag));
    }

    @GetMapping("/statistics")
    public ResponseEntity<TaskStatisticsResponse> getStatistics() {
        return ResponseEntity.ok(taskService.getStatistics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest request) {
        TaskResponse createdTask = taskService.create(request);
        return ResponseEntity
                .created(URI.create(BASE_PATH + "/" + createdTask.id()))
                .body(createdTask);
    }

    @PostMapping("/{id}/tags")
    public ResponseEntity<TaskResponse> addTag(
            @PathVariable Long id,
            @Valid @RequestBody AddTagRequest request
    ) {
        TaskResponse updateTask = taskService.addTag(id, request.tag());
        return ResponseEntity.ok(updateTask);
    }

    @PatchMapping("/{id}/priority")
    public ResponseEntity<TaskResponse> updatePriority(@PathVariable Long id,
                                                       @Valid @RequestBody UpdateTaskPriorityRequest request
    ) {
        TaskResponse updateTask = taskService.updatePriority(id, request);
        return ResponseEntity.ok(updateTask);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateStatus(@PathVariable Long id,
                                                     @Valid @RequestBody UpdateTaskStatusRequest request
    ) {
        TaskResponse updateTask = taskService.updateStatus(id, request);
        return ResponseEntity.ok(updateTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/scenario1")
    public ResponseEntity<TaskResponse> scenario1(
            @Valid @RequestBody CreateTaskRequest request,
            @RequestParam boolean flag
    ) {
        return ResponseEntity.ok(taskService.create2TagsWithException(request, "1", "2", flag));
    }
}
