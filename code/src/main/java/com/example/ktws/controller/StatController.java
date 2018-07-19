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
        if (sections.isEmpty()) {
            return null;
        }
        sections.sort(Comparator.comparing(Section::getDatetime));
        Section lastSection = sections.get(sections.size() - 1);
        Set<Photo> photos = lastSection.getPhotos();
        List<StatInfo> statInfos = new ArrayList<>();
        extractStatInfosFromPhotos(statInfos, photos);
        return statInfos;
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
        sections.sort(Comparator.comparing(Section::getDatetime));
        int maxIndex = sections.size() < 3 ? sections.size() : 3;
        List<StatInfo> statInfos = new ArrayList<>();
        for (int i = 1; i <= maxIndex; i++) {
            Section section = sections.get(sections.size() - i);
            Set<Photo> photos = section.getPhotos();
            extractStatInfosFromPhotos(statInfos, photos);
        }
        return statInfos;
    }

    private void extractStatInfosFromPhotos(List<StatInfo> statInfos, Set<Photo> photos) {
        for (Photo p : photos) {
            StatInfo statInfo = new StatInfo();
            statInfo.setPhotoId(p.getId());
            statInfo.setTimestamp(p.getTimestamp());
            statInfo.setStats(p.getStats());
            statInfos.add(statInfo);
        }
    }

    @GetMapping("/byCourse")
    public Iterable<StatInfo> getStatsByCourse(@RequestParam(name = "courseId") Long courseId) {
        Optional<Course> c = courseService.findById(courseId);
        if (!c.isPresent()) {
            return null;
        }
        Course course = c.get();
        Set<Section> sections = course.getSections();
        List<StatInfo> statInfos = new ArrayList<>();
        for (Section s : sections) {
            Set<Photo> photos = s.getPhotos();
            extractStatInfosFromPhotos(statInfos, photos);
        }
        return statInfos;
    }

    @GetMapping("/byPhoto")
    public Iterable<Stat> getByPhoto(@RequestParam(name = "photoId") Long photoId) {
        Optional<Photo> p = photoService.findById(photoId);
        if (!p.isPresent()) {
            return null;
        }
        Photo photo = p.get();
        return statService.getStatsByPhoto(photo);
    }
}
