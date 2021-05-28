package edu.eci.ieti.taskplanner.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TaskPlannerHandlerException {

    @ExceptionHandler(TaskPlannerException.class)
    private ResponseEntity<?> exceptionHandler(TaskPlannerException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }
}
