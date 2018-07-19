package com.example.ktws.schedule;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.Section;
import com.example.ktws.mq.RequestSender;
import com.example.ktws.service.CourseService;
import com.example.ktws.service.SectionService;
import com.example.ktws.util.RequestMsg;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;

@Component
public class SendMsgJob implements Job {
    @Autowired
    private RequestSender requestSender;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private CourseService courseService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        logger.info("JobExecution: Sending...");
        JobDataMap data = jobExecutionContext.getMergedJobDataMap();
        Long courseId = Long.parseLong((String) data.get("courseId"));
        String camera = (String) data.get("camera");
        Integer interval = Integer.parseInt((String) data.get("interval"));
        Integer duration = Integer.parseInt((String) data.get("duration"));

        Optional<Course> c = courseService.findById(courseId);
        if (!c.isPresent()) {
            logger.error("No such course [id={}]" , courseId);
            return;
        }
        Course course = c.get();
        Section section = sectionService.addNewSection(new Timestamp(System.currentTimeMillis()), course);

        RequestMsg msg = new RequestMsg();
        msg.setSectionId(section.getId());
        msg.setCamera(camera);
        msg.setInterval(interval);
        msg.setDuration(duration);
        String queueName = "requestQueue";
        requestSender.send(msg, queueName);
        logger.info("JobExecution: Send message {} to queue [name={}]", msg, queueName);
    }
}
