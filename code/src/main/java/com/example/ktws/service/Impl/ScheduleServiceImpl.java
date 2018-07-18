package com.example.ktws.service.Impl;

import com.example.ktws.schedule.SendMsgJob;
import com.example.ktws.service.ScheduleService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private Scheduler scheduler;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean add(Long courseId, String camera, Integer interval, List<String> cronExpression, Integer duration) throws Exception {
        logger.info("Add: Adding job...");
        if (cronExpression.isEmpty()){
            logger.error("ERROR: cronExpression can not be empty");
            return false;
        }

        JobKey jobKey = new JobKey(Long.toString(courseId), Long.toString(courseId));
        if (scheduler.getJobDetail(jobKey) != null){
            logger.error("ERROR: Job(" + Long.toString(courseId) + ") already exists");
            return false;
        }
        JobDataMap newJobDateMap = new JobDataMap();
        newJobDateMap.put("courseId", courseId);
        newJobDateMap.put("camera", camera);
        newJobDateMap.put("interval",interval);
        newJobDateMap.put("duration",duration);
        JobDetail jobDetail = JobBuilder.newJob(SendMsgJob.class)
                .withIdentity(jobKey)
                .usingJobData(newJobDateMap)
                .storeDurably()
                .build();
        scheduler.addJob(jobDetail,false);
        logger.info("Add: Successfully added a job {}", courseId);
        logger.info("Add: Scheduling job {}...", courseId);
        int jobId = 1;
        for (String each:cronExpression){
            Trigger trigger = newTrigger()
                    .withIdentity("Trigger"+Integer.toString(jobId) , Long.toString(courseId))
                    .startNow()
                    .withSchedule(cronSchedule(each))
                    .forJob(jobKey)
                    .build();
            scheduler.scheduleJob(trigger);
            jobId++;
        }
        logger.info("Add: Successfully scheduled job {}", courseId);
        return true;
    }

    @Override
    public boolean modify(Long courseId, String camera, Integer interval, List<String> cronExpression, Integer duration) throws Exception {
        logger.info("Modify: Modifying...");
        if (cronExpression.isEmpty()){
            logger.error("ERROR: cronExpression can not be empty");
            return false;
        }
        JobKey jobKey = new JobKey(Long.toString(courseId), Long.toString(courseId));
        JobDataMap newJobDateMap = new JobDataMap();
        newJobDateMap.put("courseId", courseId);
        newJobDateMap.put("camera", camera);
        newJobDateMap.put("interval",interval);
        newJobDateMap.put("duration",duration);
        JobDetail jobDetail = JobBuilder.newJob(SendMsgJob.class)
                .withIdentity(jobKey)
                .usingJobData(newJobDateMap)
                .storeDurably()
                .build();
        if (scheduler.getJobDetail(jobKey) != null){
            logger.info("Modify: Delete existing Job");
            scheduler.deleteJob(jobKey);
        }
        else{
            logger.info("Modify: Job(" + Long.toString(courseId) + ") not found");
        }
        scheduler.addJob(jobDetail,false);
        logger.info("Modify: Successfully added job {}", courseId);
        logger.info("Modify: Scheduling job {}", courseId);
        int jobId = 1;
        for (String each:cronExpression){
            Trigger trigger = newTrigger()
                    .withIdentity("Trigger"+Integer.toString(jobId) , Long.toString(courseId))
                    .startNow()
                    .withSchedule(cronSchedule(each))
                    .forJob(jobKey)
                    .build();
            scheduler.scheduleJob(trigger);
            jobId++;
        }
        logger.info("Modify: Successfully scheduled job {}", courseId);
        return true;
    }

    @Override
    public boolean delete(Long courseId) throws Exception {
        logger.info("Delete: Deleting...");
        JobKey jobKey = new JobKey(Long.toString(courseId), Long.toString(courseId));
        if (scheduler.getJobDetail(jobKey) != null){
            scheduler.deleteJob(jobKey);
            logger.info("Delete: Successfully deleted job {}", courseId);
            return true;
        }
        else{
            logger.error("ERROR: Job(" + Long.toString(courseId) + ") not found.");
            return false;
        }
    }
}
