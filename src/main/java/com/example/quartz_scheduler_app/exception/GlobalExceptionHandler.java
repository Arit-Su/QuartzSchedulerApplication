package com.example.quartz_scheduler_app.exception;

import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler to provide structured, consistent error responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors from @Valid annotation.
     *
     * @param ex The exception thrown when validation fails.
     * @return A map of field names to error messages.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> response = Map.of(
            "success", false,
            "errors", errors
        );
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Handles errors thrown by the Quartz Scheduler.
     *
     * @param ex The SchedulerException.
     * @return A generic server error response.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SchedulerException.class)
    public ResponseEntity<Map<String, Object>> handleSchedulerException(SchedulerException ex) {
        Map<String, Object> response = Map.of(
            "success", false,
            "message", "An internal error occurred while scheduling the job."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}