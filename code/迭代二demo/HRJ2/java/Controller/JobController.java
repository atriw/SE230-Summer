package Controller;


import Job.NewJob;
import mod.ScheduleService;
import mod.SpecificTime;
import org.quartz.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Controller
@RequestMapping(value="/job")
public class JobController {
    //加入Qulifier注解，通过名称注入bean
    @Resource(name = "ScheduleService")
    private ScheduleService schedulerService;


    @GetMapping(value="/queryjob")
    public void queryjob() throws SchedulerException,Exception {
        List<SpecificTime> specificTimes = new ArrayList<SpecificTime>();
        SpecificTime time = new SpecificTime();
        time.setStartTime("09:12");
        time.setEndTime("20:00");
        time.setDay(6);
        specificTimes.add(time);
        schedulerService.add(123,"123456",123,specificTimes);
        System.out.println("here");
    }
}
