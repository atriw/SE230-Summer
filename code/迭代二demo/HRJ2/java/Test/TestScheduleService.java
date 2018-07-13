package Test;

import mod.ScheduleService;
import mod.SpecificTime;

import java.util.ArrayList;
import java.util.List;

public class TestScheduleService {
    public static void main(String[] args) throws Exception{
        ScheduleService scheduleService = new ScheduleService();
        scheduleService.init();
        List<SpecificTime> specificTimes = new ArrayList<SpecificTime>();
        SpecificTime time = new SpecificTime();
        time.setDay(3);
        time.setStartTime("09:30");
        time.setEndTime("10:00");
        specificTimes.add(time);
        scheduleService.add(1,"123456798",1,specificTimes);
        specificTimes.clear();
        SpecificTime time1 = new SpecificTime();
        SpecificTime time2 = new SpecificTime();
        time1.setDay(4);
        time1.setStartTime("09:40");
        time1.setEndTime("10:00");
        specificTimes.add(time1);
        time2.setDay(5);
        time2.setStartTime("15:07");
        time2.setEndTime("10:00");
        specificTimes.add(time2);
        scheduleService.modify(1,"123456798",1,specificTimes);
    }
}
