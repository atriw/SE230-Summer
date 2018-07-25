package com.example.ktws.controller;

import com.example.ktws.domain.*;
import com.example.ktws.service.CourseService;
import com.example.ktws.service.PhotoService;
import com.example.ktws.service.SectionService;
import com.example.ktws.service.StatService;
import com.example.ktws.vo.StatInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stat")
public class StatController {
    @Autowired
    private StatService statService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private SectionService sectionService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/byUserLastCourse")
    public Iterable<StatInfo> getStatsByUserLastCourse(HttpServletRequest httpServletRequest) {
        User u = (User) httpServletRequest.getSession().getAttribute("User");
        if (u == null) {
            return null;
        }
        List<Section> sections = (ArrayList<Section>) sectionService.getSectionsByUser(u);
        logger.info("GetStatsByUserLastCourse: Got {} sections", sections.size());
        return sections.stream()
                .max(Comparator.comparing(Section::getDatetime))
                .map(section -> section.getPhotos().stream()
                        .map(StatInfo::new).collect(Collectors.toList()))
                .orElse(null);
    }

    @GetMapping("/byLastCourse")
    public Iterable<StatInfo> getStatsByLastCourse(@RequestParam(name = "courseId") Long courseId) {
        return courseService.findById(courseId)
                .<Iterable<StatInfo>>map(course -> course.getSections().stream()
                        .max(Comparator.comparing(Section::getDatetime))
                        .map(section -> section.getPhotos().stream()
                                .map(StatInfo::new)
                                .collect(Collectors.toList()))
                        .orElse(null))
                .orElse(null);
    }

    @GetMapping("/byLast3Courses")
    public Iterable<StatInfo> getStatsByLast3Courses(@RequestParam(name = "courseId") Long courseId) {
        Optional<Course> c = courseService.findById(courseId);
        if (!c.isPresent()) {
            return null;
        }
        Course course = c.get();
        List<Section> sections = (ArrayList<Section>) sectionService.getSectionsByCourse(course);
        if (sections.isEmpty()) {
            return null;
        }
        int maxIndex = sections.size() < 3 ? sections.size() : 3;
        return sections.stream()
                .sorted(Comparator.comparing(Section::getDatetime).reversed())
                .filter(section -> sections.indexOf(section) < maxIndex)
                .flatMap(section -> section.getPhotos().stream())
                .map(StatInfo::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/byCourse")
    public Iterable<StatInfo> getStatsByCourse(@RequestParam(name = "courseId") Long courseId) {
        return courseService.findById(courseId)
                .<Iterable<StatInfo>>map(course -> course.getSections().stream()
                .flatMap((section -> section.getPhotos().stream()))
                .map(StatInfo::new)
                .collect(Collectors.toList()))
                .orElse(null);
    }

    @GetMapping("/byPhoto")
    public Iterable<Stat> getByPhoto(@RequestParam(name = "photoId") Long photoId) {
        return photoService.findById(photoId)
                .map(photo -> statService.getStatsByPhoto(photo))
                .orElse(null);
    }
}
