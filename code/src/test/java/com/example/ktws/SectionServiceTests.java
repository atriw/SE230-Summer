package com.example.ktws;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.Section;
import com.example.ktws.domain.User;
import com.example.ktws.repository.CourseRepository;
import com.example.ktws.repository.SectionRepository;
import com.example.ktws.repository.UserRepository;
import com.example.ktws.service.SectionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("test")
public class SectionServiceTests {
    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    SectionService sectionService;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    private User u = new User("n1", "pw1", "e1", "p1");
    private Course c = new Course("name1", "camera1", "address1", 20, 5, u);
    private Timestamp tS = new Timestamp(1234567890123L);
    private Section s = new Section(tS, c);
    private Section s2 = new Section(tS, c);

    @Test
    @Transactional
    public void testAddNewSection(){
        Section newSection = sectionService.addNewSection(s.getDatetime(),s.getCourse());
        s.setId(newSection.getId());
        Assert.assertEquals(newSection, s);
    }

    @Test
    @Transactional
    public void testGetSectionsByCourse() {
        userRepository.save(u);
        courseRepository.save(c);
        sectionRepository.save(s);
        sectionRepository.save(s2);
        List<Section> sList = new ArrayList<>();
        sList.add(s);
        sList.add(s2);
        Iterable<Section> iterable = sectionService.getSectionsByCourse(c);
        Assert.assertEquals(sList, iterable);
    }

    @Test
    @Transactional
    public void testGetSectionsByUser() {
        User newUser = userRepository.save(u);
        courseRepository.save(c);
        sectionRepository.save(s);
        sectionRepository.save(s2);
        u.addCourse(c);
        c.addSection(s);
        c.addSection(s2);
        Set<Section> result = new HashSet<>();
        result.add(s);
        result.add(s2);
        List<Section> iterable = (List<Section>) sectionService.getSectionsByUser(newUser);
        Set<Section> sections = new HashSet<>(iterable);
        Assert.assertEquals(result,sections);
    }

    @Test
    @Transactional
    public void testFindById(){
        userRepository.save(u);
        courseRepository.save(c);
        Section newSection = sectionRepository.save(s);
        Optional<Section> section = sectionService.findById(newSection.getId());
        Assert.assertEquals(section.get(),newSection);
    }
}
