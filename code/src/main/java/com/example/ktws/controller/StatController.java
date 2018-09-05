package com.example.ktws.controller;

import com.example.ktws.domain.*;
import com.example.ktws.service.CourseService;
import com.example.ktws.service.PhotoService;
import com.example.ktws.service.SectionService;
import com.example.ktws.service.StatService;
import com.example.ktws.vo.SectionStat;
import com.example.ktws.vo.StatInfo;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
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

    @GetMapping("/sectionStat")
    public Iterable<SectionStat> getSectionStat(@RequestParam(name = "courseId") Long courseId) {
        Optional<Course> c = courseService.findById(courseId);
        if (!c.isPresent()) {
            return null;
        }
        Course course = c.get();
        List<SectionStat> sectionStats = new ArrayList<>();
        Set<Section> sections = course.getSections();
        for (Section s : sections) {
            SectionStat sectionStat = new SectionStat();
            sectionStat.setId(s.getId());
            sectionStat.setDatetime(s.getDatetime().toLocalDateTime());
            sectionStat.setCourseId(courseId);

            List<StatInfo> statInfos = s.getPhotos().stream().map(StatInfo::new)
                    .sorted((Comparator.comparing(StatInfo::getNumOfFace)))
                    .collect(Collectors.toList());
            if (statInfos.isEmpty()) {
                break;
            }

            float count = 0;

            for (StatInfo statInfo : statInfos) {
                count += statInfo.emotionCount();
            }

            float emotion = count / (course.getNumOfStudent() * statInfos.size());

            Integer sum = statInfos.stream()
                    .reduce(0,
                            (integer, statInfo) -> integer + statInfo.getNumOfFace(),
                            (integer, integer2) -> integer + integer2);

            String minTime = new Timestamp(statInfos.get(0).getTimestamp())
                    .toLocalDateTime()
                    .format(DateTimeFormatter.ISO_LOCAL_TIME);
            Integer minValue = statInfos.get(0).getStats().iterator().next().getNumOfFace();

            String maxTime = new Timestamp(statInfos.get(statInfos.size() - 1).getTimestamp())
                    .toLocalDateTime()
                    .format(DateTimeFormatter.ISO_LOCAL_TIME);
            Integer maxValue = statInfos.get(statInfos.size() - 1).getStats().iterator().next().getNumOfFace();

            Integer numOfStudent = course.getNumOfStudent();

            Double average = (double) sum / (double) statInfos.size() / (double) numOfStudent;

            DecimalFormat df = new DecimalFormat("0.00");
            String t = df.format(average);

            String averagePercent = t.substring(2) + "%";
            Map<String, Object> maxJson = new HashMap<>();
            Map<String, Object> minJson = new HashMap<>();

            maxJson.put("time", maxTime);
            maxJson.put("value", maxValue);
            minJson.put("time", minTime);
            minJson.put("value", minValue);

            Map<String, Object> info = new HashMap<>();
            info.put("average", averagePercent);
            info.put("max", maxJson);
            info.put("min", minJson);
            info.put("emotion", emotion);

            sectionStat.setInfo(info);
            sectionStats.add(sectionStat);
        }

        return sectionStats;
    }

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
