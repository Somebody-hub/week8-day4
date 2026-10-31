package com.example.week8_day4.repository;

import com.example.week8_day4.model.Task;
import com.example.week8_day4.model.TaskPriority;
import com.example.week8_day4.model.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public final class TaskSpecifications {
    private TaskSpecifications() {
    }

    public static Specification<Task> hasStatus(TaskStatus taskStatus) {
        return ((root, query, criteriaBuilder) ->
                taskStatus == null ? null : criteriaBuilder.equal(root.get("taskStatus"), taskStatus));
    }

    public static Specification<Task> hasPriority(TaskPriority priority) {
        return (root, query, criteriaBuilder) ->
                priority == null ? null : criteriaBuilder.equal(root.get("taskPriority"), priority);
    }

    public static Specification<Task> hasTag(String tag) {
        return (root, query, criteriaBuilder) ->
                (tag == null || tag.isBlank())
                        ? null
                        : criteriaBuilder.isMember(tag.trim().toLowerCase(), root.<Set<String>>get("tags"));
    }
}
