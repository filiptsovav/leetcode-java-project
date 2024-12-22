package com.example.demo.exceptions;

public class TaskIsAlreadyEndedException extends RuntimeException {
    public TaskIsAlreadyEndedException(String message) {
        super(message);
    }
}
