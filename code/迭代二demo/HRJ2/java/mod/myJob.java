package mod;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class myJob implements Job {

    // Instances of Job must have a public no-argument constructor.
    public myJob(){

    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data = context.getMergedJobDataMap();
        RequestMsg msg = (RequestMsg) data.get("msg");
        System.out.println(msg.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        System.out.println(sdf.format(new Date()));
    }
}
