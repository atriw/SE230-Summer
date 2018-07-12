package com.example.ktws.schedule;

import com.example.ktws.mq.RequestSender;
import com.example.ktws.util.RequestMsg;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class SendMsgJob implements Job {
    @Autowired
    RequestSender requestSender;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap data = jobExecutionContext.getMergedJobDataMap();
        Long cId = (Long) data.get("id");
        String ip = (String) data.get("ip");
        int interval = (int) data.get("interval");
        int duration = (int) data.get("duration");
        Long sId = Long.parseLong(String.valueOf(cId) + String.valueOf(new Date().getTime()));
        RequestMsg msg = new RequestMsg();
        msg.setId(sId);
        msg.setIp(ip);
        msg.setInterval(interval);
        msg.setDuration(duration);
        requestSender.send(msg, "requestMQ");
    }
}
