package com.example.ktws;

import com.example.ktws.controller.CourseController;
import com.example.ktws.domain.Course;
import com.example.ktws.domain.TimeSlot;
import com.example.ktws.domain.User;
import com.example.ktws.service.CourseService;
import com.example.ktws.util.Day;
import com.example.ktws.util.SpecificTime;
import org.json.JSONObject;
import org.junit.Assert;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;
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
@ActiveProfiles("test")
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
        TimeSlot t1 = new TimeSlot("08:00","10:00",Day.MON);
        TimeSlot t2 = new TimeSlot("18:00","20:00",Day.TUE);
        c1.addTimeSlot(t1);
        c2.addTimeSlot(t2);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(c1);
        courses.add(c2);
        mockHttpSession.setAttribute("User",u);
        when(courseService.getCoursesByUser(u)).thenReturn(courses);

        mockMvc.perform(get("/api/course/byUser")
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":null,\"name\":\"name1\",\"time\":\"MON 08:00-10:00\\n" +
                        "\",\"address\":\"address1\",\"numOfStudent\":10,\"interval\":5,\"camera\":\"camera1\"},{\"id\":null,\"name\":\"name2\",\"time\":\"TUE 18:00-20:00\\n" +
                        "\",\"address\":\"address2\",\"numOfStudent\":10,\"interval\":5,\"camera\":\"camera2\"}]"))
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
        TimeSlot t1 = new TimeSlot("08:00","10:00",Day.MON);
        TimeSlot t2 = new TimeSlot("18:00","20:00",Day.TUE);
        c1.addTimeSlot(t1);
        c2.addTimeSlot(t2);
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(c1);
        courses.add(c2);
        when(courseService.getAllCourses()).thenReturn(courses);

        mockMvc.perform(get("/api/course/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":null,\"name\":\"name1\",\"time\":\"MON 08:00-10:00\\n" +
                        "\",\"address\":\"address1\",\"numOfStudent\":10,\"interval\":5,\"camera\":\"camera1\"},{\"id\":null,\"name\":\"name2\",\"time\":\"TUE 18:00-20:00\\n" +
                        "\",\"address\":\"address2\",\"numOfStudent\":10,\"interval\":5,\"camera\":\"camera2\"}]"))
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
        mockJson.put("numOfStudent",numOfStudent.toString());
        mockJson.put("interval", interval.toString());
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
        mockJson.put("numOfStudent",numOfStudent.toString());
        mockJson.put("interval", interval.toString());
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
        mockJson.put("numOfStudent",numOfStudent.toString());
        mockJson.put("interval", interval.toString());
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
        mockJson.put("numOfStudent",numOfStudent.toString());
        mockJson.put("interval", interval.toString());
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
        mockJson.put("id", "1");
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
        mockJson.put("id", "1");
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
        mockJson.put("id", "2");
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
        mockJson.put("id", 1L);
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
    public void testConvertTime2STime() throws Exception{
        Method method = CourseController.class.getDeclaredMethod("convertTimeToSTimes", ArrayList.class, List.class);
        method.setAccessible(true);
        ArrayList<Map> time = new ArrayList<>();
        Map<String, String> t1 = new HashMap<>();
        t1.put("day", "MON");
        t1.put("startTime", "08:00");
        t1.put("endTime", "10:00");
        Map<String, String> t2 = new HashMap<>();
        t2.put("day", "FRI");
        t2.put("startTime", "18:00");
        t2.put("endTime", "20:00");
        time.add(t1);
        time.add(t2);
        List<SpecificTime> specificTimes = new ArrayList<>();
        method.invoke(courseController, time,specificTimes);
        List<SpecificTime> sTs = new ArrayList<>();
        SpecificTime sT1 = new SpecificTime();
        sT1.setDay(Day.MON);
        sT1.setStartTime("08:00");
        sT1.setEndTime("10:00");
        SpecificTime sT2 = new SpecificTime();
        sT2.setDay(Day.FRI);
        sT2.setStartTime("18:00");
        sT2.setEndTime("20:00");
        sTs.add(sT1);
        sTs.add(sT2);
        Assert.assertEquals(sTs,specificTimes);
    }
}
