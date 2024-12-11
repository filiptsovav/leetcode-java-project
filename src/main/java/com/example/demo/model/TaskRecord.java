package com.example.demo.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
            throw new RuntimeException("a horse with no name"); //TODO
        }

        return endTime;
    }

    public void end() {
        if (isEnded()) {
            throw new RuntimeException("revolving doors"); //TODO
        }

        endTime = LocalDateTime.now();
    }
}
