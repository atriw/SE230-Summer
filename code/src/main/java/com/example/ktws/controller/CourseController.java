package com.example.ktws.controller;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.TimeSlot;
import com.example.ktws.domain.User;
import com.example.ktws.service.CourseService;
import com.example.ktws.util.Day;
import com.example.ktws.util.SpecificTime;
import com.example.ktws.vo.CourseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/byUser")
    public Iterable<CourseInfo> getCoursesByUser(HttpServletRequest httpServletRequest){
        User u = (User) httpServletRequest.getSession().getAttribute("User");
        if (u == null) {
            return null;
        }
        List<Course> courses = (List<Course>) courseService.getCoursesByUser(u);
        return convertCoursesToVO(courses);
    }

    private Iterable<CourseInfo> convertCoursesToVO(List<Course> courses) {
        List<CourseInfo> courseInfos = new ArrayList<>();
        for (Course course: courses) {
            CourseInfo courseInfo = new CourseInfo();
            courseInfo.setId(course.getId());
            courseInfo.setName(course.getName());
            courseInfo.setNumOfStudent(course.getNumOfStudent());
            courseInfo.setInterval(course.getInterval());
            Set<TimeSlot> timeSlots = course.getTimeSlots();
            courseInfo.setTime(buildTime(timeSlots));
            courseInfos.add(courseInfo);
        }
        return courseInfos;
    }

    @GetMapping("/byCourseId")
    public CourseInfo getCourseByCourseId(@RequestParam(name = "courseId") Long courseId) {
        Optional<Course> existing = courseService.findById(courseId);
        if (!existing.isPresent()) {
            return null;
        }
        Course course = existing.get();
        CourseInfo courseInfo = new CourseInfo();
        courseInfo.setId(course.getId());
        courseInfo.setName(course.getName());
        courseInfo.setNumOfStudent(course.getNumOfStudent());
        courseInfo.setInterval(course.getInterval());
        Set<TimeSlot> timeSlots = course.getTimeSlots();
        courseInfo.setTime(buildTime(timeSlots));
        return courseInfo;
    }

    @GetMapping("/all")
    public Iterable<CourseInfo> getAllCourses(){
        List<Course> courses = (List<Course>) courseService.getAllCourses();
        return convertCoursesToVO(courses);
    }

    private String buildTime(Set<TimeSlot> timeSlots) {
        StringBuilder time = new StringBuilder();
        for (TimeSlot timeSlot: timeSlots) {
            time.append(timeSlot.getDay().toString());
            time.append(" ");
            time.append(timeSlot.getStartTime());
            time.append("-");
            time.append(timeSlot.getEndTime());
            time.append("\n");
        }
        return time.toString();
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
        c.setNumOfStudent(Integer.parseInt((String) map.get("numOfStudent")));
        c.setInterval(Integer.parseInt((String) map.get("interval")));
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
