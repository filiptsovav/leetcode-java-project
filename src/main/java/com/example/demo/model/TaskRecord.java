package com.example.demo.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.demo.exceptions.TaskIsAlreadyEndedException;
import com.example.demo.exceptions.TaskIsNotEndedException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TaskRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskName;

    @CreationTimestamp
    private LocalDateTime startTime;

    private LocalDateTime endTime = null;

    public TaskRecord() {}

    public TaskRecord(String taskName) {
        this.taskName = taskName;
    }

    public Boolean isEnded() {
        return endTime != null; 
    }

    public String getTaskName() {
        return taskName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        if (!isEnded()) {
            throw new TaskIsNotEndedException("Task isn't ended");
        }

        return endTime;
    }

    public void end() {
        if (isEnded()) {
            throw new TaskIsAlreadyEndedException("Task is already ended");
        }

        endTime = LocalDateTime.now();
    }
}
