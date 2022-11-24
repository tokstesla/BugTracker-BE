package com.bugtracker.bugtracker.exception;

import org.springframework.http.HttpStatus;

public class BugTrackerException extends RuntimeException{

    private HttpStatus status;

    private String message;

    public BugTrackerException() {
    }

    public BugTrackerException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BugTrackerException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }
}
