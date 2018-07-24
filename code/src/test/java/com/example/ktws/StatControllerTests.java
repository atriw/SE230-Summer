package com.example.ktws;

import com.example.ktws.controller.StatController;
import com.example.ktws.domain.Course;
import com.example.ktws.domain.Photo;
import com.example.ktws.domain.Section;
import com.example.ktws.domain.User;
import com.example.ktws.service.CourseService;
import com.example.ktws.service.PhotoService;
import com.example.ktws.service.SectionService;
import com.example.ktws.service.StatService;
import com.example.ktws.vo.StatInfo;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.*;

import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class StatControllerTests {
    @Mock
    private StatService statService;

    @Mock
    private PhotoService photoService;

    @Mock
    private CourseService courseService;

    @Mock
    private SectionService sectionService;

    @Autowired
    @InjectMocks
    private StatController statController;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private MockHttpSession mockHttpSession;

    @Before
    public void setupMockMvc() throws Exception{
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mockHttpSession = new MockHttpSession();
    }

    @Test
    public void testgetStatsByUserLastCourseSessionNoUser() throws Exception {
        User u = new User(1L, "name", "pwd", "email", "phone");
        //mockHttpSession.setAttribute("User",u);
        mockMvc.perform(get("/api/stat/byUserLastCourse")
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetStatsByUserLastCourseNoSection() throws Exception {
        User u = new User(1L, "name", "pwd", "email", "phone");
        mockHttpSession.setAttribute("User",u);
        List<Section> sections = new ArrayList<>();
        when(sectionService.getSectionsByUser(u)).thenReturn(sections);
        mockMvc.perform(get("/api/stat/byUserLastCourse")
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetStatsByUserLastCourseSucceeded() throws Exception {
        User u = new User(1L, "name", "pwd", "email", "phone");
        mockHttpSession.setAttribute("User",u);
        List<Section> sections = new ArrayList<>();
        Integer numOfStudent = 10;
        Integer interval = 5;

        Long t1 = 9876543210987L;
        Timestamp tS1 = new Timestamp(t1);
        Long t2 = 1234567890123L;
        Timestamp sT2 = new Timestamp(t2);

        Section s1 = new Section();
        s1.setId(1L);
        Course c1 = new Course("name1", "camera1", "address1", numOfStudent, interval, u);
        c1.setId(1L);
        s1.setCourse(c1);
        s1.setDatetime(tS1);
        Section s2 = new Section();
        s2.setId(2L);
        Course c2 = new Course("name2", "camera2", "address2", numOfStudent, interval, u);
        c2.setId(2L);
        s2.setCourse(c2);
        s2.setDatetime(sT2);

        Photo p1 = new Photo();
        p1.setId(1L);
        p1.setSection(s1);
        p1.setTimestamp(t1);
        p1.setStats(null);
        Photo p2 = new Photo();
        p2.setId(2L);
        p2.setSection(s2);
        p2.setTimestamp(t2);
        p2.setStats(null);

        s1.addPhoto(p1);
        s2.addPhoto(p2);

        sections.add(s1);
        sections.add(s2);
        when(sectionService.getSectionsByUser(u)).thenReturn(sections);
        List<StatInfo> statInfos = new ArrayList<>();
        mockMvc.perform(get("/api/stat/byUserLastCourse")
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"photoId\":1,\"timestamp\":9876543210987,\"stats\":null}]"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetStatsByLast3CoursesNoSection() throws Exception {
        User u = new User(1L, "name", "pwd", "email", "phone");
        Course course = new Course("name1", "camera1", "address1", 10, 5, u);
        course.setId(2L);
        List<Section> sections = new ArrayList<>();
        when(courseService.findById(course.getId())).thenReturn(Optional.of(course));
        when(sectionService.getSectionsByCourse(course)).thenReturn(sections);
        mockMvc.perform(get("/api/stat/byLast3Courses")
                .session(mockHttpSession)
                .param("courseId", course.getId().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetStatsByLast3CoursesSucceeded() throws Exception {
        User u = new User(1L, "name", "pwd", "email", "phone");
        mockHttpSession.setAttribute("User",u);
        List<Section> sections = new ArrayList<>();
        Integer numOfStudent = 10;
        Integer interval = 5;

        Long t1 = 9876543210987L;
        Timestamp tS1 = new Timestamp(t1);
        Long t2 = 1234567890123L;
        Timestamp sT2 = new Timestamp(t2);

        Course c = new Course("name1", "camera1", "address1", numOfStudent, interval, u);
        c.setId(1L);

        Section s1 = new Section();
        s1.setId(1L);
        s1.setCourse(c);
        s1.setDatetime(tS1);
        Section s2 = new Section();
        s2.setId(2L);
        s2.setCourse(c);
        s2.setDatetime(sT2);


        Photo p1 = new Photo();
        p1.setId(1L);
        p1.setSection(s1);
        p1.setTimestamp(t1);
        p1.setStats(null);
        Photo p2 = new Photo();
        p2.setId(2L);
        p2.setSection(s2);
        p2.setTimestamp(t2);
        p2.setStats(null);

        s1.addPhoto(p1);
        s2.addPhoto(p2);

        sections.add(s1);
        sections.add(s2);
        when(courseService.findById(c.getId())).thenReturn(Optional.of(c));
        when(sectionService.getSectionsByCourse(c)).thenReturn(sections);
        List<StatInfo> statInfos = new ArrayList<>();
        mockMvc.perform(get("/api/stat/byLast3Courses")
                .session(mockHttpSession)
                .param("courseId", c.getId().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"photoId\":1,\"timestamp\":9876543210987,\"stats\":null}," +
                                                              "{\"photoId\":2,\"timestamp\":1234567890123,\"stats\":null}]"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetStatsByLastCourseNoSection() throws Exception {
        User u = new User(1L, "name", "pwd", "email", "phone");
        Course course = new Course("name1", "camera1", "address1", 10, 5, u);
        course.setId(2L);
        List<Section> sections = new ArrayList<>();
        when(courseService.findById(course.getId())).thenReturn(Optional.of(course));
        when(sectionService.getSectionsByCourse(course)).thenReturn(sections);
        mockMvc.perform(get("/api/stat/byLastCourse")
                .session(mockHttpSession)
                .param("courseId", course.getId().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetStatsByLastCourseSucceeded() throws Exception {
        User u = new User(1L, "name", "pwd", "email", "phone");
        mockHttpSession.setAttribute("User",u);
        Integer numOfStudent = 10;
        Integer interval = 5;

        Long t1 = 9876543210987L;
        Timestamp tS1 = new Timestamp(t1);
        Long t2 = 1234567890123L;
        Timestamp sT2 = new Timestamp(t2);

        Course c = new Course("name1", "camera1", "address1", numOfStudent, interval, u);
        c.setId(1L);

        Section s1 = new Section();
        s1.setId(1L);
        c.addSection(s1);
        s1.setDatetime(tS1);
        Section s2 = new Section();
        s2.setId(2L);
        c.addSection(s2);
        s2.setDatetime(sT2);

        Photo p1 = new Photo();
        p1.setId(1L);
        p1.setSection(s1);
        p1.setTimestamp(t1);
        p1.setStats(null);
        Photo p2 = new Photo();
        p2.setId(2L);
        p2.setSection(s2);
        p2.setTimestamp(t2);
        p2.setStats(null);

        s1.addPhoto(p1);
        s2.addPhoto(p2);

        when(courseService.findById(c.getId())).thenReturn(Optional.of(c));
        mockMvc.perform(get("/api/stat/byLastCourse")
                .session(mockHttpSession)
                .param("courseId", c.getId().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"photoId\":1,\"timestamp\":9876543210987,\"stats\":null}]"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetStatsByCourseNotFound() throws Exception{
        User u = new User(1L,"name","pwd","email","phone");
        Integer numOfStudent = 10;
        Integer interval = 5;
        Course c = new Course("name", "camera", "address", numOfStudent, interval, u);
        c.setId(1L);

        Long t1 = 9876543210987L;
        Timestamp tS1 = new Timestamp(t1);
        Long t2 = 1234567890123L;
        Timestamp sT2 = new Timestamp(t2);

        Section s1 = new Section();
        s1.setId(1L);
        s1.setCourse(c);
        s1.setDatetime(tS1);
        Section s2 = new Section();
        s2.setId(2L);
        s2.setCourse(c);
        s2.setDatetime(sT2);

        Photo p1 = new Photo();
        p1.setId(1L);
        p1.setSection(s1);
        p1.setTimestamp(t1);
        p1.setStats(null);
        Photo p2 = new Photo();
        p2.setId(2L);
        p2.setSection(s2);
        p2.setTimestamp(t2);
        p2.setStats(null);

        s1.addPhoto(p1);
        s2.addPhoto(p2);

        c.addSection(s1);
        c.addSection(s2);

        when(courseService.findById(c.getId())).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/stat/byCourse")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("courseId",String.valueOf(c.getId()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetStatsByCourseSucceeded() throws Exception{
        User u = new User(1L,"name","pwd","email","phone");
        Integer numOfStudent = 10;
        Integer interval = 5;
        Course c = new Course("name", "camera", "address", numOfStudent, interval, u);
        c.setId(1L);

        Long t1 = 9876543210987L;
        Timestamp tS1 = new Timestamp(t1);
        Long t2 = 1234567890123L;
        Timestamp sT2 = new Timestamp(t2);

        Section s1 = new Section();
        s1.setId(1L);
        s1.setCourse(c);
        s1.setDatetime(tS1);
        Section s2 = new Section();
        s2.setId(2L);
        s2.setCourse(c);
        s2.setDatetime(sT2);


        Photo p1 = new Photo();
        p1.setId(1L);
        p1.setSection(s1);
        p1.setTimestamp(t1);
        p1.setStats(null);
        Photo p2 = new Photo();
        p2.setId(2L);
        p2.setSection(s2);
        p2.setTimestamp(t2);
        p2.setStats(null);

        s1.addPhoto(p1);
        s2.addPhoto(p2);

        c.addSection(s1);
        c.addSection(s2);

        when(courseService.findById(c.getId())).thenReturn(Optional.of(c));
        mockMvc.perform(get("/api/stat/byCourse")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("courseId",String.valueOf(c.getId()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"timestamp\":1234567890123,\"stats\":null},{\"timestamp\":9876543210987,\"stats\":null}]", false))
                .andDo(print())
                .andReturn();
    }
    public class myMatcher implements ResultMatcher {

        @Override
        public void match(MvcResult mvcResult) throws Exception {

        }
    }
    @Test
    public void testGetByPhotoNotFound() throws Exception{
        Photo p = new Photo();
        p.setId(1L);
        when(courseService.findById(p.getId())).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/stat/byPhoto")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("photoId",String.valueOf(p.getId()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print())
                .andReturn();
    }

    @Ignore
    @Test
    public void testGetByPhotoSucceeded() throws Exception{

    }
}
