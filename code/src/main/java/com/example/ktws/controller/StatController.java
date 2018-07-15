package com.example.ktws.controller;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.Photo;
import com.example.ktws.domain.Stat;
import com.example.ktws.domain.User;
import com.example.ktws.service.CourseService;
import com.example.ktws.service.PhotoService;
import com.example.ktws.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/stat")
public class StatController {
    @Autowired
    private StatService statService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/byLastCourse")
    public Iterable<Stat> getStatsByLastCourse(HttpServletRequest httpServletRequest) {
        User u = (User) httpServletRequest.getSession().getAttribute("User");
        if (u == null) {
            return null;
        }
        return statService.getStatsByLastCourse(u);
    }

    @GetMapping("/byLast3Courses")
    public Iterable<Stat> getStatsByLast3Courses(HttpServletRequest httpServletRequest) {
        User u = (User) httpServletRequest.getSession().getAttribute("User");
        if (u == null) {
            return null;
        }
        return statService.getStatsByLast3Courses(u);
    }

    @GetMapping("/byCourse")
    public Iterable<Stat> getStatsByCourse(@RequestParam(name = "courseId") Long courseId) {
        Optional<Course> course = courseService.findById(courseId);
        return course.map(course1 -> statService.getStatsByCourse(course1)).orElse(null);
    }


    @GetMapping("/byPhoto")
    public Iterable<Stat> getByPhoto(@RequestParam(name = "photoId") Long photoId) {
        Photo photo = null; // TODO: 根据photoService更新
        return statService.getStatsByPhoto(photo);
    }
}
