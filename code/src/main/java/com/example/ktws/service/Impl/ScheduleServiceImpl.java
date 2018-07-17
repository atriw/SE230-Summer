package com.example.ktws.service.Impl;

import com.example.ktws.schedule.SendMsgJob;
import com.example.ktws.service.ScheduleService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private Scheduler scheduler;

    @Override
    public boolean add(Long courseId, String camera, Integer interval, List<String> cronExpression, Integer duration) throws Exception {
        System.out.println("INFO: Adding...");
        if (cronExpression.isEmpty()){
            System.out.println("ERROR: cronExpression can not be empty.");
            return false;
        }

        JobKey jobKey = new JobKey(Long.toString(courseId), Long.toString(courseId));
        if (scheduler.getJobDetail(jobKey) != null){
            System.out.println("ERROR: Job(" + Long.toString(courseId) + ") already exist.");
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
        System.out.println("SUCCESS: Job(" + Long.toString(courseId) + ") successfully added.");
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
        return true;
    }

    @Override
    public boolean modify(Long courseId, String camera, Integer interval, List<String> cronExpression, Integer duration) throws Exception {
        System.out.println("INFO: Modifying...");
        if (cronExpression.isEmpty()){
            System.out.println("ERROR: cronExpression can not be empty.");
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
            System.out.println("INFO: Delete existing Job.");
            scheduler.deleteJob(jobKey);
        }
        else{
            System.out.println("INFO: Job(" + Long.toString(courseId) + ") not found.");
        }
        System.out.println("SUCCESS: Job(" + Long.toString(courseId) + ") added.");
        scheduler.addJob(jobDetail,false);
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
        return true;
    }

    @Override
    public boolean delete(Long courseId) throws Exception {
        System.out.println("INFO: Deleting...");
        JobKey jobKey = new JobKey(Long.toString(courseId), Long.toString(courseId));
        if (scheduler.getJobDetail(jobKey) != null){
            System.out.println("SUCCESS: Job(" + Long.toString(courseId) + ") successfully deleted.");
            scheduler.deleteJob(jobKey);
            return true;
        }
        else{
            System.out.println("ERROR: Job(" + Long.toString(courseId) + ") not found.");
            return false;
        }
    }
}
