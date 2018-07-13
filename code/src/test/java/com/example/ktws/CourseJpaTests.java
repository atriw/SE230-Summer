package com.example.ktws;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.TimeSlot;
import com.example.ktws.domain.User;
import com.example.ktws.repository.CourseRepository;
import com.example.ktws.repository.TimeSlotRepository;
import com.example.ktws.repository.UserRepository;
import com.example.ktws.util.Day;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KtwsApplication.class)
public class CourseJpaTests {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TimeSlotRepository timeSlotRepository;

    @Before
    @Transactional
    @Rollback
    public void init() throws Exception {
        User renrui = new User();
        renrui.setName("renrui");
        renrui.setPwd("renrui");
        renrui.setEmail("renrui@sjtu.edu.cn");
        renrui.setPhone("111222333444");
        userRepository.save(renrui);
        User shenayi = new User();
        shenayi.setName("shenayi");
        shenayi.setPwd("shenayi");
        shenayi.setEmail("shenayi@sjtu.edu.cn");
        shenayi.setPhone("444333222111");
        userRepository.save(shenayi);
        TimeSlot xingqierzao8 = new TimeSlot();
        xingqierzao8.setDay(Day.TUE);
        xingqierzao8.setStartTime("08:00");
        xingqierzao8.setEndTime("10:00");
        timeSlotRepository.save(xingqierzao8);
        TimeSlot xingqisizao8 = new TimeSlot();
        xingqisizao8.setDay(Day.THU);
        xingqisizao8.setStartTime("08:00");
        xingqisizao8.setEndTime("10:00");
        timeSlotRepository.save(xingqisizao8);
    }

    @Test
    @Transactional
    @Rollback
    public void addNewCourseTest() throws Exception {
        Course ruanjiangongcheng = new Course();
        ruanjiangongcheng.setName("ruanjiangongcheng");
        ruanjiangongcheng.setAddress("XY115");
        ruanjiangongcheng.setCamera("http://admin:admin@192.168.1.59:8081");
        ruanjiangongcheng.setInterval(300);
        TimeSlot xingqierzao8 = timeSlotRepository.findTopByDayAndStartTime(Day.TUE, "08:00").get();
        TimeSlot xingqisizao8 = timeSlotRepository.findTopByDayAndStartTime(Day.THU, "08:00").get();
        ruanjiangongcheng.addTimeSlot(xingqierzao8);
        ruanjiangongcheng.addTimeSlot(xingqisizao8);
        ruanjiangongcheng.setNumOfStudent(100);
        User shenayi = userRepository.findByName("shenayi").get(0);
        ruanjiangongcheng.setUser(shenayi);
        courseRepository.save(ruanjiangongcheng);
    }
}
