package com.example.ktws;

import com.example.ktws.domain.*;
import com.example.ktws.repository.*;
import com.example.ktws.service.StatService;
import com.example.ktws.util.TypeOfFace;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("test")
public class StatServiceTests {

    @Autowired
    StatRepository statRepository;

    @Autowired
    StatService statService;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    private User u = new User("n1", "pw1", "e1", "p1");
    private Course c = new Course("name1", "camera1", "address1", 20, 5, u);
    private Timestamp tS = new Timestamp(1234567890123L);
    private Section s = new Section(tS, c);
    private Photo p = new Photo(1234567890123L,s);
    private Stat stat1 = new Stat(5,TypeOfFace.ALL,p);
    private Stat stat2 = new Stat(4,TypeOfFace.ALL,p);
    @Test
    @Transactional
    public void testGetStatsByPhoto(){
        userRepository.save(u);
        courseRepository.save(c);
        sectionRepository.save(s);
        photoRepository.save(p);
        statRepository.save(stat1);
        statRepository.save(stat2);
        List<Stat> statList = new ArrayList<>();
        statList.add(stat1);
        statList.add(stat2);
        Iterable<Stat> stats = statService.getStatsByPhoto(p);
        Assert.assertEquals(statList, stats);

    }

    @Test
    @Transactional
    public void testAddNewStat(){
        userRepository.save(u);
        courseRepository.save(c);
        sectionRepository.save(s);
        photoRepository.save(p);
        Stat newStat = statService.addNewStat(5,TypeOfFace.ALL,p);
        stat1.setId(newStat.getId());
        Assert.assertEquals(newStat, stat1);
    }

    @Ignore
    @Test
    @Transactional
    public void testParseStatInfo(){

    }

}
