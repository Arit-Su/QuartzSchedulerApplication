package com.example.quartz_scheduler_app.service;

import com.example.quartz_scheduler_app.dto.ScheduleRequestDto;
import com.example.quartz_scheduler_app.job.MessagePrintingJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

/**
 * Service responsible for the scheduling of Quartz jobs.
 */
@Service
public class SchedulerService {

    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);

    private final Scheduler scheduler;

    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * Schedules a new job based on the provided request data.
     *
     * @param requestDto The DTO containing the details for the job to be scheduled.
     * @throws SchedulerException if there is an error scheduling the job.
     */
    public void scheduleJob(ScheduleRequestDto requestDto) throws SchedulerException {
        // 1. Build the JobDetail
        JobDetail jobDetail = buildJobDetail(requestDto);

        // 2. Build the Trigger
        Trigger trigger = buildTrigger(jobDetail, requestDto.getScheduledTime());

        // 3. Schedule the job
        scheduler.scheduleJob(jobDetail, trigger);

        log.info("Job '{}' scheduled successfully for user '{}' at {}",
                jobDetail.getKey(), requestDto.getUsername(), requestDto.getScheduledTime());
    }

    /**
     * Builds a JobDetail instance for the MessagePrintingJob.
     * It includes the job's identity and the data it needs to execute.
     */
    private JobDetail buildJobDetail(ScheduleRequestDto requestDto) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("name", requestDto.getName());
        jobDataMap.put("message", requestDto.getMessage());
        jobDataMap.put("username", requestDto.getUsername());

        return JobBuilder.newJob(MessagePrintingJob.class)
                // A JobKey is a unique identifier for a job (name, group)
                .withIdentity(UUID.randomUUID().toString(), "message-jobs")
                .withDescription("A job to print a user-defined message")
                .usingJobData(jobDataMap)
                .storeDurably() // The job will be persisted in the JobStore
                .build();
    }

    /**
     * Builds a simple, one-time Trigger for a given job and start time.
     */
    private Trigger buildTrigger(JobDetail jobDetail, java.time.LocalDateTime startTime) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                // A TriggerKey is a unique identifier for a trigger (name, group)
                .withIdentity(jobDetail.getKey().getName(), "message-triggers")
                .withDescription("A simple one-time trigger")
                // Convert LocalDateTime to java.util.Date for Quartz
                .startAt(Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }
}