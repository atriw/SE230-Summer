package com.example.ktws.service.Impl;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.User;
import com.example.ktws.repository.CourseRepository;
import com.example.ktws.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Iterable<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Iterable<Course> getCoursesByUd(HttpSession session){
        User u = (User)session.getAttribute("User");
        if (u != null) {
            Long ud = u.getId();
            return courseRepository.findCourseByUd(ud);
        }
        return null;
    }

    @Override
    public boolean addNewCourse(Long ud, String name, String address, String camera, Integer num_of_student, Integer interval){
        if(courseRepository.findByName(name) == null) {
            return false;
        }
        Course c = new Course();
        c.setUd(ud);
        c.setName(name);
        c.setAddress(address);
        c.setCamera(camera);
        c.setNum_of_student(num_of_student);
        c.setInterval(interval);
        courseRepository.save(c);
        return true;
    }

    @Override
    public boolean deleteCourse(String name) {
        Course c = courseRepository.findByName(name);
        if(c == null)
            return false;
        courseRepository.delete(c);
        return true;
    }

    @Override
    public boolean updateCourse(long ud, String oldName, String name, String address, String camera, Integer num_of_student, Integer interval) {
        Course c = courseRepository.findByName(oldName);
        c.setUd(ud);
        c.setName(name);
        c.setAddress(address);
        c.setCamera(camera);
        c.setNum_of_student(num_of_student);
        c.setInterval(interval);
        courseRepository.save(c);
        return true;
    }

}
