package com.example.ktws.controller;

import com.example.ktws.domain.Course;
import com.example.ktws.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    CourseService courseService;

    @GetMapping("/byUd")
    public Iterable<Course> getCoursesByUd(HttpSession session){
        return courseService.getCoursesByUd(session);
  }

    @GetMapping("/all")
    public Iterable<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @PostMapping("/add")
    public boolean addNewCourse(@RequestBody Map map){
        long ud = ((Integer) map.get("ud")).longValue(); //直接long转化报错了
        return courseService.addNewCourse(ud, (String)map.get("name"), (String)map.get("address")
                , (String)map.get("camera"), (Integer)map.get("num_of_student"), (Integer)map.get("Interval"));
    }

    @PostMapping("/delete")
    public boolean deleteCourse(HttpServletRequest request){
        courseService.deleteCourse(request);
        return true;
    }

    @PostMapping("/update")
    public boolean updateCourse(@RequestBody Map map){
        long ud = ((Integer) map.get("ud")).longValue();
        return courseService.updateCourse(ud, (String)map.get("name"),( String)map.get("name"), (String)map.get("address")
                , (String)map.get("camera"), (Integer)map.get("num_of_student"), (Integer)map.get("Interval"));
    }

}
