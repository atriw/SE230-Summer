package com.example.ktws.config;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {

    // TODO: Job中不能Autowired服务类
    @Bean(name="Scheduler")
    public Scheduler scheduler() throws Exception {
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        return scheduler;
    }
}
