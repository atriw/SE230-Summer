package mod;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class MyClass implements Job {
    public MyClass(){}

    public void execute(JobExecutionContext context) throws JobExecutionException{
        JobDataMap data = context.getMergedJobDataMap();
        System.out.println("id:" + data.get("id") + " ip:" + data.get("ip") + " interval:" + data.get("interval")
        );
        for(SpecificTime each: (List<SpecificTime>)data.get("specificTime")){
            System.out.println(each.getStartTime());
        }
    }
}
