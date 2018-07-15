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

    @GetMapping("/all")
    public Iterable<Course> getAllCourses(){
        return courseService.getAllCourses();
    }

    @PostMapping("/add")
    public boolean addNewCourse(@RequestBody Map map, HttpServletRequest httpServletRequest){
        User u = (User) httpServletRequest.getSession().getAttribute("User");
        if (u == null) {
            return false;
        }
        String name = (String) map.get("name");
        String address = (String) map.get("address");
        String camera = (String) map.get("camera");
        Integer numOfStudent = (Integer) map.get("numOfStudent");
        Integer interval = (Integer) map.get("interval");
        JSONArray time = (JSONArray) map.get("time");
        List<SpecificTime> specificTimes = new ArrayList<>();
        convertTimeToSTimes(time, specificTimes);
        courseService.addNewCourse(name, address, camera, numOfStudent, interval, specificTimes, u);
        return true;
    }

    @PostMapping("/delete")
    public boolean deleteCourse(@RequestBody Map map){
        return courseService.deleteCourse((String)map.get("name"));
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
        JSONArray time = (JSONArray) map.get("time");
        List<SpecificTime> specificTimes = new ArrayList<>();
        convertTimeToSTimes(time, specificTimes);
        return courseService.updateCourse(oldName, newName, address, camera, numOfStudent, interval, specificTimes);
    }

    private void convertTimeToSTimes(JSONArray time, List<SpecificTime> specificTimes) {
        for (Object o : time) {
            JSONObject t = (JSONObject) o;
            String day = (String) t.get("day");
            String startTime = (String) t.get("start_time");
            String endTime = (String) t.get("end_time");
            SpecificTime st = new SpecificTime();
            st.setDay(Day.valueOf(day));
            st.setStartTime(startTime);
            st.setEndTime(endTime);
            specificTimes.add(st);
        }
    }

}
