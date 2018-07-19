package com.example.ktws.controller;

import com.example.ktws.domain.*;
import com.example.ktws.service.CourseService;
import com.example.ktws.service.PhotoService;
import com.example.ktws.service.SectionService;
import com.example.ktws.service.StatService;
import com.example.ktws.vo.StatInfo;
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

    @GetMapping("/byLastCourse")
    public Iterable<StatInfo> getStatsByLastCourse(HttpServletRequest httpServletRequest) {
        User u = (User) httpServletRequest.getSession().getAttribute("User");
        if (u == null) {
            return null;
        }
        List<Section> sections = (ArrayList<Section>) sectionService.getSectionsByUser(u);
        return sections.stream()
                .max(Comparator.comparing(Section::getDatetime))
                .map(section -> section.getPhotos().stream()
                        .map(StatInfo::new).collect(Collectors.toList()))
                .orElse(null);
    }

    @GetMapping("/byLast3Courses")
    public Iterable<StatInfo> getStatsByLast3Courses(HttpServletRequest httpServletRequest) {
        User u = (User) httpServletRequest.getSession().getAttribute("User");
        if (u == null) {
            return null;
        }
        List<Section> sections = (ArrayList<Section>) sectionService.getSectionsByUser(u);
        if (sections.isEmpty()) {
            return null;
        }
        int maxIndex = sections.size() < 3 ? sections.size() : 3;
        return sections.stream()
                .sorted(Comparator.comparing(Section::getDatetime).reversed())
                .filter(section -> sections.indexOf(section) <= maxIndex)
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
