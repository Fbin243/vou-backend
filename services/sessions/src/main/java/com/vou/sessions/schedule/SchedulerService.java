package com.vou.sessions.schedule;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
@AllArgsConstructor
public class SchedulerService {
    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);
    private final SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private Scheduler scheduler;

    @PostConstruct
    private void init() throws SchedulerException {
        scheduler = schedulerFactory.getScheduler();
    }

    public void createCronJobs(Runnable runnable, String startDateStr, String endDateStr, String startTimeStr, String endTimeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);
        while (!startDate.isAfter(endDate)) {
            log.info("startDate: {}, endDate: {}", startDate, endDate);
            createOneCronJob(runnable, startDate.toString(), startTimeStr, endTimeStr);
            startDate = startDate.plusDays(1);
        }
    }

    public void createOneCronJob(Runnable runnable, String dateStr, String startTimeStr, String endTimeStr) {
        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("runnable", runnable);
            log.info("cronjob for startDate: {}", dateStr);
            // Define Job detail
            JobDetail jobDetail = JobBuilder.newJob(CronJob.class)
                    .usingJobData(jobDataMap)
                    .build();

            // Define Start and End Time
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = sdf.parse(dateStr + " " + startTimeStr);
            Date endTime = sdf.parse(dateStr + " " + endTimeStr);

            // Define Trigger
            Trigger trigger = TriggerBuilder.newTrigger()
                    .startAt(startTime)
                    .endAt(endTime)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(1) // Specify the interval
                            .repeatForever())
                    .build();

            // Schedule Job
            scheduler.scheduleJob(jobDetail, trigger);

            // Start Scheduler
            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}




