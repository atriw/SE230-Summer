package com.example.ktws.config;

import com.example.ktws.schedule.MyJobFactory;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class SchedulerConfig {
    private final MyJobFactory myJobFactory;

    private final DataSource dataSource;

    public SchedulerConfig(MyJobFactory myJobFactory, DataSource dataSource) {
        this.myJobFactory = myJobFactory;
        this.dataSource = dataSource;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(myJobFactory);
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setDataSource(dataSource);
        // 延时启动，应用启动1秒后
        schedulerFactoryBean.setStartupDelay(1);
        System.out.println("myJobFactory:"+myJobFactory);

        return schedulerFactoryBean;
    }


    @Bean
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }

}
