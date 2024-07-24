package com.vou.sessions.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SchedulerService {
    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);
    private final TaskScheduler taskScheduler;

    public void initializeNewTask(String cronExpression, Runnable task) {
        taskScheduler.schedule(task, new CronTrigger(cronExpression));
    }
}