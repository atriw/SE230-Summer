package com.example.ktws.config;

import com.example.ktws.schedule.MyJobFactory;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class SchedulerConfig {
    @Autowired
    private MyJobFactory myJobFactory;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        try{
            schedulerFactoryBean.setJobFactory(myJobFactory);
            schedulerFactoryBean.setQuartzProperties(quartzProperties());
            System.out.println("myJobFactory:"+myJobFactory);

        }catch (IOException e) {

            e.printStackTrace();
        }
        return schedulerFactoryBean;
    }


    //指定quartz.properties
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }


    @Bean
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }

}
