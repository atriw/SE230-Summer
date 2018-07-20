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
        logger.info("AddJob: Adding job [jobKey=({}, {})]...", courseId, courseId);
        if (cronExpression.isEmpty()){
            logger.error("CronExpression can not be empty");
            return false;
        }

        JobKey jobKey = new JobKey(Long.toString(courseId), Long.toString(courseId));
        if (scheduler.getJobDetail(jobKey) != null){
            logger.error("Job [jobKey=({}, {})] already exists", courseId, courseId);
            return false;
        }
        JobDataMap newJobDateMap = new JobDataMap();
        newJobDateMap.put("courseId", courseId.toString());
        newJobDateMap.put("camera", camera);
        newJobDateMap.put("interval",interval.toString());
        newJobDateMap.put("duration",duration.toString());
        JobDetail jobDetail = JobBuilder.newJob(SendMsgJob.class)
                .withIdentity(jobKey)
                .usingJobData(newJobDateMap)
                .storeDurably()
                .build();
        scheduler.addJob(jobDetail,false);
        logger.info("AddJob: Added job [jobKey=({}, {})]", courseId, courseId);
        logger.info("AddJob: Scheduling job [jobKey=({}, {})]...", courseId, courseId);
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
        logger.info("AddJob: Scheduled job [jobKey=({}, {})]", courseId, courseId);
        return true;
    }

    @Override
    public boolean modify(Long courseId, String camera, Integer interval, List<String> cronExpression, Integer duration) throws Exception {
        logger.info("ModifyJob: Modifying job [jobKey=({}, {})]...", courseId, courseId);
        if (cronExpression.isEmpty()){
            logger.error("CronExpression can not be empty");
            return false;
        }
        JobKey jobKey = new JobKey(Long.toString(courseId), Long.toString(courseId));
        JobDataMap newJobDateMap = new JobDataMap();
        newJobDateMap.put("courseId", courseId.toString());
        newJobDateMap.put("camera", camera);
        newJobDateMap.put("interval",interval.toString());
        newJobDateMap.put("duration",duration.toString());
        JobDetail jobDetail = JobBuilder.newJob(SendMsgJob.class)
                .withIdentity(jobKey)
                .usingJobData(newJobDateMap)
                .storeDurably()
                .build();
        if (scheduler.getJobDetail(jobKey) != null){
            logger.info("ModifyJob: Deleted existing Job");
            scheduler.deleteJob(jobKey);
        }
        else{
            logger.info("ModifyJob: Job [jobKey=({}, {})] not found", courseId, courseId);
        }
        scheduler.addJob(jobDetail,false);
        logger.info("ModifyJob: Added job [jobKey=({}, {})]", courseId, courseId);
        logger.info("ModifyJob: Scheduling job [jobKey=({}, {})]", courseId, courseId);
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
        logger.info("ModifyJob: Scheduled job [jobKey=({}, {})]", courseId, courseId);
        return true;
    }

    @Override
    public boolean delete(Long courseId) throws Exception {
        logger.info("DeleteJob: Deleting [jobKey=({}, {})]...", courseId, courseId);
        JobKey jobKey = new JobKey(Long.toString(courseId), Long.toString(courseId));
        if (scheduler.getJobDetail(jobKey) != null){
            scheduler.deleteJob(jobKey);
            logger.info("DeleteJob: Deleted job [jobKey=({}, {})]", courseId, courseId);
            return true;
        }
        else{
            logger.error("Job [jobKey=({}, {})] not found", courseId, courseId);
            return false;
        }
    }
}
