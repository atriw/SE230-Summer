import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.List;

import static org.quartz.TriggerBuilder.newTrigger;

public class ScheduleService {
    public Scheduler scheduler;

    public void init() throws Exception {
        scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
    }

    public void add(long id, String ip, int interval, List<SpecificTime> specificTimes)throws Exception {
        JobKey jobKey = new JobKey("Lesson" , Long.toString(id));
        RequestMsg msg = new RequestMsg();
        msg.setId(id);
        msg.setInterval(interval);
        msg.setIp(ip);
        if (specificTimes.isEmpty()){
            System.out.println("课程时间不能为空");
            return;
        }

        // waiting for implement
        SpecificTime time = specificTimes.get(0);
        time.getStartTime();
        time.getEndTime();
        msg.setDuration(0);

        JobDataMap newJobDateMap = new JobDataMap();
        newJobDateMap.put("msg",msg);
        JobDetail jobDetail = JobBuilder.newJob()
                .withIdentity(jobKey)
                .usingJobData(newJobDateMap)
                .build();
        Trigger trigger = newTrigger()
                .withIdentity("test" , "test")
                .startAt(new Date(System.currentTimeMillis() + 1000))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
                .build();

        scheduler.scheduleJob(jobDetail , trigger);
    }
}
