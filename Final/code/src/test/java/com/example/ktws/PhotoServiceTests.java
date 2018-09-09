package com.example.ktws;

import com.example.ktws.domain.Course;
import com.example.ktws.domain.Photo;
import com.example.ktws.domain.Section;
import com.example.ktws.domain.User;
import com.example.ktws.repository.CourseRepository;
import com.example.ktws.repository.PhotoRepository;
import com.example.ktws.repository.SectionRepository;
import com.example.ktws.repository.UserRepository;
import com.example.ktws.service.PhotoService;
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
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("test")
public class PhotoServiceTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    SectionRepository sectionRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    PhotoService photoService;

    private User u = new User("n1", "pw1", "e1", "p1");
    private Course c = new Course("name1", "camera1", "address1", 20, 5, u);
    private Timestamp tS = new Timestamp(1234567890123L);
    private Section s = new Section(tS, c);
    private Photo p = new Photo(1234567890123L,s);

    @Test
    @Transactional
    public void testFindById(){
        userRepository.save(u);
        courseRepository.save(c);
        sectionRepository.save(s);
        photoRepository.save(p);
        Optional<Photo> ph = photoService.findById(p.getId());
        Assert.assertEquals(ph.get(),p);
    }
}
