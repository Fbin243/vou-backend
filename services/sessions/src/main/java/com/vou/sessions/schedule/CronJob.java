package com.vou.sessions.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class CronJob implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Runnable runnable = (Runnable) context.getJobDetail().getJobDataMap().get("runnable");
        if (runnable != null) {
            runnable.run();
        } else {
            throw new JobExecutionException("CronJob is null");
        }
    }
}
