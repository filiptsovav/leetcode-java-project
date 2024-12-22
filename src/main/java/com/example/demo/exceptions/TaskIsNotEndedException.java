package com.example.demo.exceptions;

public class TaskIsNotEndedException extends RuntimeException {
    public TaskIsNotEndedException(String message) {
        super(message);
    }
}
