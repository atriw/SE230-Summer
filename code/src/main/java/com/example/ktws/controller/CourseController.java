package com.example.ktws.controller;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.User;
import com.example.ktws.service.CourseService;
import com.example.ktws.service.UserService;
import com.example.ktws.util.Day;
import com.example.ktws.util.SpecificTime;
import com.example.ktws.vo.CourseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/byUser")
    public Iterable<CourseInfo> getCoursesByUser(HttpServletRequest httpServletRequest){
        User u = (User) httpServletRequest.getSession().getAttribute("User");
        if (u == null) {
            return null;
        }
        List<Course> courses = (List<Course>) courseService.getCoursesByUser(u);
        return courses.stream().map(CourseInfo::new).collect(Collectors.toList());
    }

    @GetMapping("/byUserName")
    public Iterable<CourseInfo> getCoursesByUserName(@RequestParam(name = "userName") String name) {
        Optional<User> user = userService.findByName(name);
        if (!user.isPresent()) {
            return null;
        }
        User u = user.get();
        List<Course> courses = (List<Course>) courseService.getCoursesByUser(u);
        return courses.stream().map(CourseInfo::new).collect(Collectors.toList());
    }

    @GetMapping("/byCourseId")
    public CourseInfo getCourseByCourseId(@RequestParam(name = "courseId") Long courseId) {
        Optional<Course> existing = courseService.findById(courseId);
        if (!existing.isPresent()) {
            logger.error("No such course");
            return null;
        }
        Course course = existing.get();
        logger.info("GetCourseByCourseId: Got Course {}", course);
        return new CourseInfo(course);
    }

    @GetMapping("/all")
    public Iterable<CourseInfo> getAllCourses(){
        List<Course> courses = (List<Course>) courseService.getAllCourses();
        return courses.stream().map(CourseInfo::new).collect(Collectors.toList());
    }

    @PostMapping("/add")
    public Course addNewCourse(@RequestBody Map map, HttpServletRequest httpServletRequest){
        User u = (User) httpServletRequest.getSession().getAttribute("User");
        if (u == null) {
            return null;
        }
        String name = (String) map.get("name");
        String address = (String) map.get("address");
        String camera = (String) map.get("camera");
        Integer numOfStudent = Integer.parseInt((String) map.get("numOfStudent"));
        Integer interval = Integer.parseInt((String) map.get("interval"));
        Course c = new Course(name, camera, address, numOfStudent, interval, u);
        ArrayList<Map> time = (ArrayList<Map>) map.get("time");
        List<SpecificTime> specificTimes = new ArrayList<>();
        convertTimeToSTimes(time, specificTimes);
        courseService.addNewCourse(c, specificTimes);
        return c;
    }

    @PostMapping("/delete")
    public boolean deleteCourse(@RequestBody Map map,HttpServletRequest request){
        User u = (User)request.getSession().getAttribute("User");
        if(u == null){
            logger.error("No user in session");
            return false;
        }
        else{
            Long id = Long.parseLong((String)map.get("id"));
            return courseService.deleteCourse(id);
        }
    }

    @PostMapping("/update")
    public boolean updateCourse(@RequestBody Map map, HttpServletRequest httpServletRequest){
        User u = (User) httpServletRequest.getSession().getAttribute("User");
        if (u == null) {
            return false;
        }
        String oldName = (String) map.get("oldName");
        String newName = (String) map.get("newName");
        String address = (String) map.get("address");
        String camera = (String) map.get("camera");
        Integer numOfStudent = Integer.parseInt((String) map.get("numOfStudent")) ;
        Integer interval = Integer.parseInt((String) map.get("interval")) ;
        ArrayList<Map> time = (ArrayList<Map>) map.get("time");
        List<SpecificTime> specificTimes = new ArrayList<>();
        convertTimeToSTimes(time, specificTimes);
        return courseService.updateCourse(oldName, newName, address, camera, numOfStudent, interval, specificTimes);
    }

    private void convertTimeToSTimes(ArrayList<Map> time, List<SpecificTime> specificTimes) {
        for (Map m : time) {
            String day = (String) m.get("day");
            String startTime = (String) m.get("startTime");
            String endTime = (String) m.get("endTime");
            SpecificTime st = new SpecificTime();
            st.setDay(Day.valueOf(day));
            st.setStartTime(startTime);
            st.setEndTime(endTime);
            specificTimes.add(st);
        }
    }

}
