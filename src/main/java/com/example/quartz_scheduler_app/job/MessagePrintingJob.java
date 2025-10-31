package com.example.quartz_scheduler_app.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * A simple Quartz job that prints a message.
 * It retrieves data from the JobDataMap to perform its work.
 */
@Component
public class MessagePrintingJob implements Job {


    private static final Logger log = LoggerFactory.getLogger(MessagePrintingJob.class);

    /**
     * This method is called by the Quartz scheduler when a trigger fires.
     *
     * @param context The execution context of the job, containing job details and data.
     * @throws JobExecutionException if there is an error during job execution.
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 1. Retrieve the JobDataMap from the execution context
        var jobDataMap = context.getJobDetail().getJobDataMap();

        // 2. Extract the custom data we stored
        String name = jobDataMap.getString("name");
        String message = jobDataMap.getString("message");
        String username = jobDataMap.getString("username");

        // 3. Execute the job's logic
        log.info("Executing job for user: {}", username);
        log.info("Hello, {}! Here is your message: '{}'", name, message);
        log.info("----------------------------------------------------");
    }
}