package com.example.ktws;

import com.example.ktws.controller.CourseController;
import com.example.ktws.domain.Course;
import com.example.ktws.domain.User;
import com.example.ktws.service.CourseService;
import com.example.ktws.util.Day;
import com.example.ktws.util.SpecificTime;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class CourseControllerTests {
    @Mock
    private CourseService courseService;

    @Autowired
    @InjectMocks
    private CourseController courseController;

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
    public void testgetCoursesByUser() throws Exception {
        User u = new User(1L,"name","pwd","email","phone");
        Integer numOfStudent = 10;
        Integer interval = 5;
        Course c1 = new Course("name1", "camera1", "address1", numOfStudent, interval, u);
        Course c2 = new Course("name2", "camera2", "address2", numOfStudent, interval, u);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(c1);
        courses.add(c2);
        mockHttpSession.setAttribute("User",u);
        when(courseService.getCoursesByUser(u)).thenReturn(courses);

        mockMvc.perform(get("/api/course/byUser")
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":null,\"name\":\"name1\",\"address\":\"address1\",\"camera\":\"camera1\",\"numOfStudent\":10,\"interval\":5}," +
                                                              "{\"id\":null,\"name\":\"name2\",\"address\":\"address2\",\"camera\":\"camera2\",\"numOfStudent\":10,\"interval\":5}]"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testgetAllCourses() throws Exception {
        User u = new User(1L,"name","pwd","email","phone");
        Integer numOfStudent = 10;
        Integer interval = 5;
        Course c1 = new Course("name1", "camera1", "address1", numOfStudent, interval, u);
        Course c2 = new Course("name2", "camera2", "address2", numOfStudent, interval, u);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(c1);
        courses.add(c2);
        when(courseService.getAllCourses()).thenReturn(courses);

        mockMvc.perform(get("/api/course/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":null,\"name\":\"name1\",\"address\":\"address1\",\"camera\":\"camera1\",\"numOfStudent\":10,\"interval\":5}," +
                                                              "{\"id\":null,\"name\":\"name2\",\"address\":\"address2\",\"camera\":\"camera2\",\"numOfStudent\":10,\"interval\":5}]"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testAddNewCourseSucceeded() throws Exception{
        User u = new User(1L,"name","pwd","email","phone");
        Integer numOfStudent = 10;
        Integer interval = 5;
        Course c = new Course("name", "camera", "address", numOfStudent, interval, u);
        SpecificTime sT = new SpecificTime();
        sT.setDay(Day.MON);
        sT.setStartTime("08:00");
        sT.setEndTime("10:00");
        List<SpecificTime> specificTimes = new ArrayList<>();
        specificTimes.add(sT);
        ArrayList<Map> time = new ArrayList<Map>();
        mockHttpSession.setAttribute("User",u);
        when(courseService.addNewCourse(c,specificTimes)).thenReturn(c);
        JSONObject mockJson = new JSONObject();
        mockJson.put("name", "name");
        mockJson.put("camera","camera");
        mockJson.put("address","address");
        mockJson.put("numOfStudent",numOfStudent);
        mockJson.put("interval", interval);
        mockJson.put("time", time);

        mockMvc.perform(post("/api/course/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJson.toString())
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":null,\"name\":\"name\",\"address\":\"address\",\"camera\":\"camera\",\"numOfStudent\":10,\"interval\":5}"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testAddNewCourseFailed() throws Exception{
        User u = new User(1L,"name","pwd","email","phone");
        Integer numOfStudent = 10;
        Integer interval = 5;
        Course c = new Course("name", "camera", "address", numOfStudent, interval, u);
        SpecificTime sT = new SpecificTime();
        sT.setDay(Day.MON);
        sT.setStartTime("08:00");
        sT.setEndTime("10:00");
        List<SpecificTime> specificTimes = new ArrayList<>();
        specificTimes.add(sT);
        ArrayList<Map> time = new ArrayList<Map>();
        //mockHttpSession.setAttribute("User",u);
        when(courseService.addNewCourse(c,specificTimes)).thenReturn(null);
        JSONObject mockJson = new JSONObject();
        mockJson.put("name", "name");
        mockJson.put("camera","camera");
        mockJson.put("address","address");
        mockJson.put("numOfStudent",numOfStudent);
        mockJson.put("interval", interval);
        mockJson.put("time", time);

        mockMvc.perform(post("/api/course/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJson.toString())
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testUpdateNewCourseSucceeded() throws Exception{
        User u = new User(1L,"name","pwd","email","phone");
        Integer numOfStudent = 10;
        Integer interval = 5;
        Course c = new Course("name", "camera", "address", numOfStudent, interval, u);
        SpecificTime sT = new SpecificTime();
        sT.setDay(Day.MON);
        sT.setStartTime("08:00");
        sT.setEndTime("10:00");
        List<SpecificTime> specificTimes = new ArrayList<>();
        specificTimes.add(sT);
        ArrayList<Map> time = new ArrayList<>();
        Map<String, String> t = new HashMap<>();
        t.put("day", "MON");
        t.put("startTime", "08:00");
        t.put("endTime", "10:00");
        time.add(t);
        mockHttpSession.setAttribute("User",u);
        String newName = "newName";
        String oldName = c.getName();
        when(courseService.updateCourse(oldName, newName, c.getAddress(), c.getCamera(), numOfStudent, interval, specificTimes)).thenReturn(true);
        JSONObject mockJson = new JSONObject();
        mockJson.put("newName", newName);
        mockJson.put("oldName", oldName);
        mockJson.put("camera","camera");
        mockJson.put("address","address");
        mockJson.put("numOfStudent",numOfStudent);
        mockJson.put("interval", interval);
        mockJson.put("time", time);

        mockMvc.perform(post("/api/course/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJson.toString())
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testUpdateNewCourseFailed() throws Exception{
        User u = new User(1L,"name","pwd","email","phone");
        Integer numOfStudent = 10;
        Integer interval = 5;
        Course c = new Course("name", "camera", "address", numOfStudent, interval, u);
        SpecificTime sT = new SpecificTime();
        sT.setDay(Day.MON);
        sT.setStartTime("08:00");
        sT.setEndTime("10:00");
        List<SpecificTime> specificTimes = new ArrayList<>();
        specificTimes.add(sT);
        ArrayList<Map> time = new ArrayList<>();
        Map<String, String> t = new HashMap<>();
        t.put("day", "MON");
        t.put("startTime", "08:00");
        t.put("endTime", "10:00");
        time.add(t);
        mockHttpSession.setAttribute("User",u);
        String newName = "newName";
        String oldName = c.getName();
        when(courseService.updateCourse(oldName, newName, c.getAddress(), c.getCamera(), numOfStudent, interval, specificTimes)).thenReturn(false);
        JSONObject mockJson = new JSONObject();
        mockJson.put("newName", newName);
        mockJson.put("oldName", oldName);
        mockJson.put("camera","camera");
        mockJson.put("address","address");
        mockJson.put("numOfStudent",numOfStudent);
        mockJson.put("interval", interval);
        mockJson.put("time", time);

        mockMvc.perform(post("/api/course/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJson.toString())
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testUpdateCourseSessionNoUser() throws Exception{
        User u = new User(1L,"name","pwd","email","phone");
        Integer numOfStudent = 10;
        Integer interval = 5;
        Course c = new Course("name", "camera", "address", numOfStudent, interval, u);
        SpecificTime sT = new SpecificTime();
        sT.setDay(Day.MON);
        sT.setStartTime("08:00");
        sT.setEndTime("10:00");
        List<SpecificTime> specificTimes = new ArrayList<>();
        specificTimes.add(sT);
        ArrayList<Map> time = new ArrayList<>();
        Map<String, String> t = new HashMap<>();
        t.put("day", "MON");
        t.put("startTime", "08:00");
        t.put("endTime", "10:00");
        time.add(t);
        //mockHttpSession.setAttribute("User",u);
        String newName = "newName";
        String oldName = c.getName();
        when(courseService.updateCourse(oldName, newName, c.getAddress(), c.getCamera(), numOfStudent, interval, specificTimes)).thenReturn(true);
        JSONObject mockJson = new JSONObject();
        mockJson.put("newName", newName);
        mockJson.put("oldName", oldName);
        mockJson.put("camera","camera");
        mockJson.put("address","address");
        mockJson.put("numOfStudent",numOfStudent);
        mockJson.put("interval", interval);
        mockJson.put("time", time);

        mockMvc.perform(post("/api/course/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJson.toString())
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testDeleteCourseSucceeded() throws Exception{
        User u = new User(1L,"name","pwd","email","phone");
        Integer numOfStudent = 10;
        Integer interval = 5;
        Course c = new Course("name", "camera", "address", numOfStudent, interval, u);
        c.setId(1L);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(c);
        mockHttpSession.setAttribute("User",u);
        when(courseService.getCoursesByUser(u)).thenReturn(courses);
        when(courseService.deleteCourse(c.getId())).thenReturn(true);
        JSONObject mockJson = new JSONObject();
        mockJson.put("name", "name");
        mockMvc.perform(post("/api/course/delete")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJson.toString())
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testDeleteCourseFailed() throws Exception{
        User u = new User(1L,"name","pwd","email","phone");
        Integer numOfStudent = 10;
        Integer interval = 5;
        Course c = new Course("name", "camera", "address", numOfStudent, interval, u);
        c.setId(1L);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(c);
        mockHttpSession.setAttribute("User",u);
        when(courseService.getCoursesByUser(u)).thenReturn(courses);
        when(courseService.deleteCourse(c.getId())).thenReturn(false);
        JSONObject mockJson = new JSONObject();
        mockJson.put("name", "name");
        mockMvc.perform(post("/api/course/delete")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJson.toString())
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testDeleteCourseNotFound() throws Exception{
        User u = new User(1L,"name","pwd","email","phone");
        Integer numOfStudent = 10;
        Integer interval = 5;
        Course c = new Course("name", "camera", "address", numOfStudent, interval, u);
        c.setId(1L);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(c);
        mockHttpSession.setAttribute("User",u);
        when(courseService.getCoursesByUser(u)).thenReturn(courses);
        when(courseService.deleteCourse(c.getId())).thenReturn(true);
        JSONObject mockJson = new JSONObject();
        mockJson.put("name", "nameNotFound");
        mockMvc.perform(post("/api/course/delete")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJson.toString())
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testDeleteCourseSessionNoUser() throws Exception{
        User u = new User(1L,"name","pwd","email","phone");
        Integer numOfStudent = 10;
        Integer interval = 5;
        Course c = new Course("name", "camera", "address", numOfStudent, interval, u);
        c.setId(1L);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(c);
        //mockHttpSession.setAttribute("User",u);
        when(courseService.getCoursesByUser(u)).thenReturn(courses);
        when(courseService.deleteCourse(c.getId())).thenReturn(true);
        JSONObject mockJson = new JSONObject();
        mockJson.put("name", "name");
        mockMvc.perform(post("/api/course/delete")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJson.toString())
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"))
                .andDo(print())
                .andReturn();
    }

}
