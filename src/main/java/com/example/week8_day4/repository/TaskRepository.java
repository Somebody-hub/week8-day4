package com.example.week8_day4.repository;


import com.example.week8_day4.model.Task;
import com.example.week8_day4.projection.PriorityCount;
import com.example.week8_day4.projection.StatusCount;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    @EntityGraph(attributePaths = "tags")
    Optional<Task> findWithTagsById(Long id);

    @Override
    @EntityGraph(attributePaths = "tags")
    List<Task> findAll(Specification<Task> spec);

    @Query("select t.taskStatus as status, count(t) as total from Task t group by t.taskStatus")
    List<StatusCount> countByStatus();

    @Query("select t.taskPriority as priority, count(t) as total from Task t group by t.taskPriority")
    List<PriorityCount> countByPriority();

}
