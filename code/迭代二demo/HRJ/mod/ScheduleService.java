package mod;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class ScheduleService {
    private Scheduler scheduler;

    public void init() throws Exception {
        scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
    }

    public void add(long id, String ip, int interval, List<SpecificTime> specificTimes)throws Exception {
        System.out.println("INFO: Adding...");
        if (specificTimes.isEmpty()){
            System.out.println("ERROR: specificTimes can not be empty.");
            return;
        }
        JobKey jobKey = new JobKey(Long.toString(id), Long.toString(id));
        if (scheduler.getJobDetail(jobKey) != null){
            System.out.println("ERROR: Job(" + Long.toString(id) + ") already exist.");
            return;
        }
        JobDataMap newJobDateMap = new JobDataMap();
        newJobDateMap.put("id",id);
        newJobDateMap.put("ip",ip);
        newJobDateMap.put("interval",interval);
        newJobDateMap.put("specificTime",specificTimes);
        JobDetail jobDetail = JobBuilder.newJob(MyClass.class)
                .withIdentity(jobKey)
                .usingJobData(newJobDateMap)
                .storeDurably()
                .build();
        scheduler.addJob(jobDetail,false);
        System.out.println("SUCCESS: Job(" + Long.toString(id) + ") successfully added.");
        int jobId = 1;
        for (SpecificTime each:specificTimes){
            String startTime = each.getStartTime();
            int day = each.getDay();
            String cron = new BuildCron().generate(startTime, day);
            Trigger trigger = newTrigger()
                    .withIdentity("Trigger"+Integer.toString(jobId) , Long.toString(id))
                    .startNow()
                    .withSchedule(cronSchedule(cron))
                    .forJob(jobKey)
                    .build();
            scheduler.scheduleJob(trigger);
            jobId++;
        }
    }

    public void modify(long id, String ip, int interval, List<SpecificTime> specificTimes)throws Exception{
        System.out.println("INFO: Modifying...");
        if (specificTimes.isEmpty()){
            System.out.println("ERROR: specificTimes can not be empty.");
            return;
        }
        JobKey jobKey = new JobKey(Long.toString(id), Long.toString(id));
        JobDataMap newJobDateMap = new JobDataMap();
        newJobDateMap.put("id",id);
        newJobDateMap.put("ip",ip);
        newJobDateMap.put("interval",interval);
        newJobDateMap.put("specificTime",specificTimes);
        JobDetail jobDetail = JobBuilder.newJob(MyClass.class)
                .withIdentity(jobKey)
                .usingJobData(newJobDateMap)
                .storeDurably()
                .build();
        if (scheduler.getJobDetail(jobKey) != null){
            System.out.println("INFO: Delete existing Job.");
            scheduler.deleteJob(jobKey);
        }
        else{
            System.out.println("INFO: Job(" + Long.toString(id) + ") not found.");
        }
        System.out.println("SUCCESS: Job(" + Long.toString(id) + ") added.");
        scheduler.addJob(jobDetail,false);
        int jobId = 1;
        for (SpecificTime each:specificTimes){
            String startTime = each.getStartTime();
            int day = each.getDay();
            String cron = new BuildCron().generate(startTime, day);
            Trigger trigger = newTrigger()
                    .withIdentity("Trigger"+Integer.toString(jobId) , Long.toString(id))
                    .startNow()
                    .withSchedule(cronSchedule(cron))
                    .forJob(jobKey)
                    .build();
            scheduler.scheduleJob(trigger);
            jobId++;
        }
    }

    public void delete(long id) throws Exception{
        System.out.println("INFO: Deleting...");
        JobKey jobKey = new JobKey(Long.toString(id), Long.toString(id));
        if (scheduler.getJobDetail(jobKey) != null){
            System.out.println("SUCCESS: Job(" + Long.toString(id) + ") successfully deleted.");
            scheduler.deleteJob(jobKey);
        }
        else{
            System.out.println("ERROR: Job(" + Long.toString(id) + ") not found.");
        }
    }
}
