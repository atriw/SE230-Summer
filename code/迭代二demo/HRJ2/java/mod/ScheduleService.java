package mod;

import org.quartz.*;

import javax.annotation.Resource;
import java.util.List;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;


@Resource(name = "ScheduleService")
public class ScheduleService {
    @Resource(name = "Scheduler")
    private Scheduler scheduler;

    public void add(Long id, String ip, int interval, List<String> cronExpression, int duration)throws Exception {
        System.out.println("INFO: Adding...");
        if (cronExpression.isEmpty()){
            System.out.println("ERROR: cronExpression can not be empty.");
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
        newJobDateMap.put("duration",duration);
        newJobDateMap.put("cronExpression",cronExpression);
        JobDetail jobDetail = JobBuilder.newJob(MyClass.class)
                .withIdentity(jobKey)
                .usingJobData(newJobDateMap)
                .storeDurably()
                .build();
        scheduler.addJob(jobDetail,false);
        System.out.println("SUCCESS: Job(" + Long.toString(id) + ") successfully added.");
        int jobId = 1;
        for (String each:cronExpression){
            Trigger trigger = newTrigger()
                    .withIdentity("Trigger"+Integer.toString(jobId) , Long.toString(id))
                    .startNow()
                    .withSchedule(cronSchedule(each))
                    .forJob(jobKey)
                    .build();
            scheduler.scheduleJob(trigger);
            jobId++;
        }
    }

    public void modify(Long id, String ip, int interval, List<String> cronExpression, int duration)throws Exception{
        System.out.println("INFO: Modifying...");
        if (cronExpression.isEmpty()){
            System.out.println("ERROR: cronExpression can not be empty.");
            return;
        }
        JobKey jobKey = new JobKey(Long.toString(id), Long.toString(id));
        JobDataMap newJobDateMap = new JobDataMap();
        newJobDateMap.put("id",id);
        newJobDateMap.put("ip",ip);
        newJobDateMap.put("interval",interval);
        newJobDateMap.put("cronExpression",cronExpression);
        newJobDateMap.put("duration",duration);
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
        for (String each:cronExpression){
            Trigger trigger = newTrigger()
                    .withIdentity("Trigger"+Integer.toString(jobId) , Long.toString(id))
                    .startNow()
                    .withSchedule(cronSchedule(each))
                    .forJob(jobKey)
                    .build();
            scheduler.scheduleJob(trigger);
            jobId++;
        }
    }

    public void delete(Long id) throws Exception{
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
