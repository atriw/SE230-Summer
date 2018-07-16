package Test;

import mod.RequestMsg;
import mod.myJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;


import java.util.Date;

import static org.quartz.CronScheduleBuilder.weeklyOnDayAndHourAndMinute;
import static org.quartz.TriggerBuilder.newTrigger;


public class Test {
    public static void main(String[] args) throws Exception {
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        // 开始
        scheduler.start();
        JobKey jobKey = new JobKey("test" , "test-1");
        RequestMsg msg = new RequestMsg();
        msg.setId(123);
        JobDataMap newJobDateMap = new JobDataMap();
        newJobDateMap.put("msg",msg);
        JobDetail jobDetail = JobBuilder.newJob(myJob.class)
                .withIdentity(jobKey)
                .usingJobData(newJobDateMap)
                .build();

        Trigger trigger = newTrigger()
                .withIdentity("test" , "test")
                // 延迟一秒执行
                .startAt(new Date(System.currentTimeMillis() + 1000))
                // 每隔一秒执行 并一直重复
                .withSchedule(weeklyOnDayAndHourAndMinute(DateBuilder.WEDNESDAY, 15, 0))
                .build();
        scheduler.scheduleJob(jobDetail , trigger);
        if (scheduler.getJobDetail(jobKey) == null){
            System.out.println("exist");
        }

        Thread.sleep(12000);
        // 删除job
        scheduler.deleteJob(jobKey);
    }

}
