package edu.eci.ieti.taskplanner.exceptions;

import org.springframework.http.HttpStatus;

public class TaskPlannerException extends Exception {

    private final HttpStatus status;

    public TaskPlannerException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
