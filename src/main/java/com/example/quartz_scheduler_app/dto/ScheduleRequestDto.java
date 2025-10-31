package com.example.quartz_scheduler_app.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * A Data Transfer Object representing the request to schedule a new job.
 * This class includes validation constraints to ensure data integrity.
 */
public class ScheduleRequestDto {


    @NotEmpty(message = "Name cannot be empty.")
    @Size(max = 100, message = "Name must not exceed 100 characters.")
    private String name;


    @NotEmpty(message = "Username cannot be empty.")
    @Size(max = 50, message = "Username must not exceed 50 characters.")
    private String username;

    @NotEmpty(message = "Message cannot be empty.")
    @Size(max = 255, message = "Message must not exceed 255 characters.")
    private String message;


    @NotNull(message = "Scheduled time cannot be null.")
    @Future(message = "Scheduled time must be in the future.")
    private LocalDateTime scheduledTime;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
}