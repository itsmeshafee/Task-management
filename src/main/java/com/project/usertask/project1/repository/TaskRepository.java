package com.project.usertask.project1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.usertask.project1.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    
}
