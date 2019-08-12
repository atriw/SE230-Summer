package com.example.ktws;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.User;
import com.example.ktws.repository.CourseRepository;
import com.example.ktws.repository.UserRepository;
import com.example.ktws.service.CourseService;
import com.example.ktws.util.Day;
import com.example.ktws.util.SpecificTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("test")
public class CourseServiceTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    CourseRepository courseRepository;

    private User u1 = new User("n1", "pw1", "e1", "p1");
    private User u2 = new User("n2", "pw2", "e2", "p2");
    private Course c1 = new Course("name1", "camera1", "address1", 20, 5, u1);
    private Course c2 = new Course("name2", "camera2", "address2", 10, 10, u2);

    @Test
    @Transactional
    public void testGetAllCourses() {
        userRepository.save(u1);
        userRepository.save(u2);
        courseRepository.save(c1);
        courseRepository.save(c2);
        List<Course> cList = new ArrayList<>();
        cList.add(c1);
        cList.add(c2);
        Iterable<Course> iterable = courseService.getAllCourses();
        Assert.assertEquals(cList, iterable);
    }

    @Test
    @Transactional
    public void testGetCoursesByUser() {
        userRepository.save(u1);
        userRepository.save(u2);
        courseRepository.save(c1);
        courseRepository.save(c2);
        List<Course> cList = new ArrayList<>();
        cList.add(c1);
        Iterable<Course> iterable = courseService.getCoursesByUser(u1);
        Assert.assertEquals(cList, iterable);
    }

    @Test
    @Transactional
    public void testFindById() {
        userRepository.save(u1);
        courseRepository.save(c1);
        Optional<Course> c = courseService.findById(c1.getId());
        Assert.assertEquals(c.get(),c1);
    }

    @Test
    @Transactional
    public void testAddNewCourseAlreadyHad(){
        userRepository.save(u1);
        courseRepository.save(c1);
        List<SpecificTime> time = new ArrayList<>();
        SpecificTime sT = new SpecificTime();
        sT.setDay(Day.MON);
        sT.setStartTime("08:00");
        sT.setEndTime("10:00");
        time.add(sT);
        Course returnValue = courseService.addNewCourse(c1,time);
        Assert.assertTrue((courseRepository.findByName(c1.getName()).isPresent()));
        Assert.assertEquals(returnValue, c1);
    }

    @Test
    @Transactional
    public void testAddNewCourseSucceeded(){
        userRepository.save(u1);
        userRepository.save(u2);
        List<SpecificTime> time = new ArrayList<>();
        SpecificTime sT = new SpecificTime();
        sT.setDay(Day.MON);
        sT.setStartTime("08:00");
        sT.setEndTime("10:00");
        time.add(sT);
        Assert.assertFalse((courseRepository.findByName(c1.getName()).isPresent()));
        Course returnValue = courseService.addNewCourse(c1,time);
        Assert.assertEquals(returnValue, c1);
    }

    @Test
    @Transactional
    public void testDeleteCourseNotFound(){
        userRepository.save(u1);
        userRepository.save(u2);
        courseRepository.save(c1);
        Assert.assertFalse(courseRepository.findById(777L).isPresent());
        Assert.assertFalse(courseService.deleteCourse(777L));
    }

    @Test
    @Transactional
    public void testDeleteCourseSucceeded(){
        userRepository.save(u1);
        userRepository.save(u2);
        courseRepository.save(c1);
        Assert.assertTrue(courseRepository.findById(c1.getId()).isPresent());
        Assert.assertTrue(courseService.deleteCourse(c1.getId()));
    }


    @Test
    @Transactional
    public void testUpdateCourseNotFound(){
        userRepository.save(u1);
        userRepository.save(u2);
        String oldName = c1.getName();
        String newName = "newName";
        String address = "address";
        String camera = "camera";
        Integer numOfStudent = 10;
        Integer interval = 5;
        List<SpecificTime> time =new ArrayList<>();
        SpecificTime sT = new SpecificTime();
        sT.setDay(Day.MON);
        sT.setStartTime("08:00");
        sT.setEndTime("10:00");
        time.add(sT);
        Assert.assertFalse(courseRepository.findByName(c1.getName()).isPresent());
        Assert.assertFalse(courseService.updateCourse(oldName, newName, address, camera, numOfStudent, interval, time));
    }

    @Test
    @Transactional
    public void testUpdateCourseSucceeded(){
        userRepository.save(u1);
        userRepository.save(u2);
        courseRepository.save(c1);
        String oldName = c1.getName();
        String newName = "newName";
        String address = "address";
        String camera = "camera";
        Integer numOfStudent = 10;
        Integer interval = 5;
        List<SpecificTime> time =new ArrayList<>();
        SpecificTime sT = new SpecificTime();
        sT.setDay(Day.MON);
        sT.setStartTime("08:00");
        sT.setEndTime("10:00");
        time.add(sT);
        Assert.assertTrue(courseRepository.findByName(c1.getName()).isPresent());
        Assert.assertTrue(courseService.updateCourse(oldName, newName, address, camera, numOfStudent, interval, time));
        Course newC = courseRepository.findByName(c1.getName()).get();
        Assert.assertEquals(newC.getName(),newName);
        Assert.assertEquals(newC.getAddress(),address);
        Assert.assertEquals(newC.getCamera(),camera);
        Assert.assertEquals(newC.getNumOfStudent(),numOfStudent);
        Assert.assertEquals(newC.getInterval(),interval);
    }
}
