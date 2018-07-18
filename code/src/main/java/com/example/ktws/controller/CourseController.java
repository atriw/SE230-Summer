package com.example.ktws.controller;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.User;
import com.example.ktws.service.CourseService;
import com.example.ktws.util.Day;
import com.example.ktws.util.SpecificTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/byUser")
    public Iterable<Course> getCoursesByUser(HttpServletRequest httpServletRequest){
        User u = (User) httpServletRequest.getSession().getAttribute("User");
        if (u == null) {
            return null;
        }
        return courseService.getCoursesByUser(u);
    }

    @GetMapping("/byCourseId")
    public Course getCourseByCourseId(@RequestParam(name = "courseId") Long courseId) {
        Optional<Course> existing = courseService.findById(courseId);
        return existing.orElse(null);
    }

    @GetMapping("/all")
    public Iterable<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @PostMapping("/add")
    public Course addNewCourse(@RequestBody Map map, HttpServletRequest httpServletRequest){
        User u = (User) httpServletRequest.getSession().getAttribute("User");
        if (u == null) {
            return null;
        }
        Course c = new Course();
        c.setName((String) map.get("name"));
        c.setAddress((String) map.get("address"));
        c.setCamera((String) map.get("camera"));
        c.setNumOfStudent((Integer) map.get("numOfStudent"));
        c.setInterval((Integer) map.get("interval"));
        c.setUser(u);
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
            System.out.println("session no user!");
            return false;
        }
        else{
            String name = (String)map.get("name");
            Iterable<Course> courses = courseService.getCoursesByUser(u);
            for(Course aCourse : courses){
                if(aCourse.getName().equals(name)){
                    return courseService.deleteCourse(aCourse.getId());
                }
            }
            return false;
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
        Integer numOfStudent = (Integer) map.get("numOfStudent");
        Integer interval = (Integer) map.get("interval");
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
