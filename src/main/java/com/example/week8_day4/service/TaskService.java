package com.example.week8_day4.service;

import com.example.week8_day4.dto.*;
import com.example.week8_day4.exception.TagAlreadyExistsException;
import com.example.week8_day4.exception.TaskNotFoundException;
import com.example.week8_day4.model.Task;
import com.example.week8_day4.model.TaskPriority;
import com.example.week8_day4.model.TaskStatus;
import com.example.week8_day4.projection.PriorityCount;
import com.example.week8_day4.projection.StatusCount;
import com.example.week8_day4.repository.TaskRepository;
import com.example.week8_day4.repository.TaskSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskResponse> getTasks(TaskStatus status, TaskPriority priority, String tag) {
        Specification<Task> spec = Specification.allOf(
                TaskSpecifications.hasStatus(status),
                TaskSpecifications.hasPriority(priority),
                TaskSpecifications.hasTag(tag)
        );

        return taskRepository.findAll(spec).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public TaskResponse create(CreateTaskRequest request) {
        Task newTask = taskRepository.save(
                new Task(request.title(), request.description(), request.priority()));
        return toResponse(newTask);
    }

    @Transactional
    public TaskResponse updateStatus(Long id, UpdateTaskStatusRequest request) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        task.setTaskStatus(request.status());
        return toResponse(task);
    }

    @Transactional
    public TaskResponse updatePriority(Long id, UpdateTaskPriorityRequest request) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        task.setTaskPriority(request.priority());
        return toResponse(task);
    }

    public TaskResponse findById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        return toResponse(task);
    }

    @Transactional
    public void deleteById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
    }

    @Transactional
    public TaskResponse addTag(Long id, String tag) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        if (task.getTags().contains(tag.trim().toLowerCase())) {
            throw new TagAlreadyExistsException(tag);
        }
        task.addTag(tag);
        return toResponse(task);
    }

    public TaskStatisticsResponse getStatistics() {
        Map<TaskStatus, Long> byStatus = new EnumMap<>(TaskStatus.class);
        for (TaskStatus status : TaskStatus.values()) {
            byStatus.put(status, 0L);
        }
        for (StatusCount statusCount : taskRepository.countByStatus()) {
            byStatus.put(statusCount.getStatus(), statusCount.getTotal());
        }

        Map<TaskPriority, Long> byPriority = new EnumMap<>(TaskPriority.class);
        for (TaskPriority priority : TaskPriority.values()) {
            byPriority.put(priority, 0L);
        }
        for (PriorityCount priorityCount : taskRepository.countByPriority()) {
            byPriority.put(priorityCount.getPriority(), priorityCount.getTotal());
        }

        return new TaskStatisticsResponse(byStatus, byPriority);
    }

    //SCENARIO 1
    @Transactional
    public TaskResponse create2TagsWithException(CreateTaskRequest request, String tag1, String tag2, boolean flag) {
        Task task = new Task(request.title(), request.description(), request.priority());
        taskRepository.save(task);
        task.addTag(tag1);
        taskRepository.flush();
        if (flag) {
            throw new IllegalStateException("Exception between steps");
        }
        task.addTag(tag2);
        return toResponse(task);
    }


    private TaskResponse toResponse(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getTaskPriority(), task.getTaskStatus(), new HashSet<>(task.getTags()));
    }

    /*
    //SCENARIO 4
    // BAD: lazy collection leaks into the DTO -> LazyInitializationException outside the transaction
    private TaskResponse toResponse(Task task) {
    return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(),
            task.getTaskPriority(), task.getTaskStatus(),
            task.getTags());
}
     */

}
