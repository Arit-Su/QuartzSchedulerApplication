package com.example.quartz_scheduler_app.controller;

import com.example.quartz_scheduler_app.dto.ScheduleRequestDto;
import com.example.quartz_scheduler_app.service.SchedulerService;
import jakarta.validation.Valid;
import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/schedule")
public class SchedulingController {

    private final SchedulerService schedulerService;

    public SchedulingController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @PostMapping("/job")
    public ResponseEntity<Map<String, Object>> scheduleJob(@Valid @RequestBody ScheduleRequestDto requestDto)
            throws SchedulerException {
        
        schedulerService.scheduleJob(requestDto);
        Map<String, Object> response = Map.of(
            "success", true,
            "message", "Job scheduled successfully!"
        );
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}