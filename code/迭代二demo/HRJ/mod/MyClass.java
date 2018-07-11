package mod;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyClass implements Job {
    public MyClass(){}

    public void execute(JobExecutionContext context) throws JobExecutionException{
        JobDataMap data = context.getMergedJobDataMap();
        RequestMsg msg = (RequestMsg) data.get("msg");
        System.out.println("id:" + msg.getId() + " duration:" + msg.getDuration() + " interval:" + msg.getInterval()
        + " ip:" + msg.getIp());
    }
}
