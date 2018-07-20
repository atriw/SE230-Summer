package com.example.ktws;

import com.example.ktws.controller.UserController;
import com.example.ktws.domain.Course;
import com.example.ktws.domain.Role;
import com.example.ktws.domain.User;
import com.example.ktws.service.UserService;
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
@ActiveProfiles("test")
public class UserControllerTests {
    @Mock
    private UserService userService;

    @Autowired
    @InjectMocks
    private UserController userController;

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
    public void testGetAllUsers() throws Exception{
        ArrayList<User> userList= new ArrayList<>();
        User u1 = new User("1","2","3","4");
        User u2 = new User("5","6","7","8");
        Course c = new Course("name", "camera", "address", 10, 5, u1);
        userList.add(u1);
        userList.add(u2);
        u1.addCourse(c);
        when(userService.getAllUsers()).thenReturn(userList);

        mockMvc.perform(get("/api/user/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "[{\"name\":\"1\",\"coursenum\":1,\"email\":\"3\",\"phone\":\"4\"}," +
                                          "{\"name\":\"5\",\"coursenum\":0,\"email\":\"7\",\"phone\":\"8\"}]"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testAddNewUserSucceeded() throws Exception{
        String name ="1";
        String pwd = "2";
        String email = "3";
        String phone = "4";
        User u = new User(name, pwd, email, phone);
        when(userService.addNewUser(u)).thenReturn(null);
        Map<String, String> mockMap1 = new HashMap<>();
        mockMap1.put("name", "1");
        mockMap1.put("pwd","2");
        mockMap1.put("email","3");
        mockMap1.put("phone","4");
        JSONObject mockJson1 = new JSONObject(mockMap1);

        mockMvc.perform(post("/api/user/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJson1.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testAddNewUserFailed() throws Exception{
        String name ="1";
        String pwd = "2";
        String email = "3";
        String phone = "4";
        User u = new User(name, pwd, email, phone);
        when(userService.addNewUser(u)).thenReturn(u);
        Map<String, String> mockMap2 = new HashMap<>();
        mockMap2.put("name", "1");
        mockMap2.put("pwd","2");
        mockMap2.put("email","3");
        mockMap2.put("phone","4");
        JSONObject mockJson2 = new JSONObject(mockMap2);

        mockMvc.perform(post("/api/user/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJson2.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":null,\"pwd\":\"2\",\"name\":\"1\",\"email\":\"3\",\"phone\":\"4\"}"));
    }

    @Test
    public void testUpdateUserSessionNoUser() throws Exception{
        Map<String, String> mockMap = new HashMap<>();
        mockMap.put("oldPwd", "oldPwd");
        mockMap.put("newPwd","newPwd");
        mockMap.put("mode","0");
        JSONObject mockJson = new JSONObject(mockMap);

        mockMvc.perform(post("/api/user/update")
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
    public void testUpdateUserPwdSucceeded() throws Exception{
        String oldPwd ="oldPwd";
        String newPwd = "newPwd";
        User u = new User(1L,"name","oldPwd","email","phone");
        User newU = new User(1L,"name","newPwd","email","phone");
        mockHttpSession.setAttribute("User",u);
        when(userService.updatePwd(oldPwd,newPwd,u)).thenReturn(true);
        Map<String, String> mockMap = new HashMap<>();
        mockMap.put("oldPwd", "oldPwd");
        mockMap.put("newPwd","newPwd");
        mockMap.put("mode","0");
        JSONObject mockJson = new JSONObject(mockMap);

        mockMvc.perform(post("/api/user/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJson.toString())
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andExpect(MockMvcResultMatchers.request().sessionAttribute("User",newU))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testUpdateUserPwdFailed() throws Exception{
        String oldPwd ="oldPwd";
        String newPwd = "newPwd";
        User u = new User(1L,"name","oldPwd","email","phone");
        mockHttpSession.setAttribute("User",u);
        when(userService.updatePwd(oldPwd,newPwd,u)).thenReturn(false);
        Map<String, String> mockMap = new HashMap<>();
        mockMap.put("oldPwd", "oldPwd");
        mockMap.put("newPwd","newPwd");
        mockMap.put("mode","0");
        JSONObject mockJson = new JSONObject(mockMap);

        mockMvc.perform(post("/api/user/update")
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
    public void testUpdateUserEmailSucceeded() throws Exception{
        String newEmail = "newEmail";
        User u = new User(1L,"name","pwd","oldEmail","phone");
        User newU = new User(1L,"name","pwd","newEmail","phone");
        mockHttpSession.setAttribute("User",u);
        when(userService.updateEmail(newEmail,u)).thenReturn(true);
        Map<String, String> mockMap = new HashMap<>();
        mockMap.put("newEmail","newEmail");
        mockMap.put("mode","1");
        JSONObject mockJson = new JSONObject(mockMap);

        mockMvc.perform(post("/api/user/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJson.toString())
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andExpect(MockMvcResultMatchers.request().sessionAttribute("User",newU))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testUpdateUserPhoneSucceeded() throws Exception{
        String newPhone = "newPhone";
        User u = new User(1L,"name","pwd","email","oldPhone");
        User newU = new User(1L,"name","pwd","email","newPhone");
        mockHttpSession.setAttribute("User",u);
        when(userService.updatePhone(newPhone,u)).thenReturn(true);
        Map<String, String> mockMap = new HashMap<>();
        mockMap.put("newPhone","newPhone");
        mockMap.put("mode","2");
        JSONObject mockJson = new JSONObject(mockMap);
        mockMvc.perform(post("/api/user/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJson.toString())
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andExpect(MockMvcResultMatchers.request().sessionAttribute("User",newU))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testUpdateUserModeFailed() throws Exception{
        String newPhone = "newPhone";
        User u = new User(1L,"name","pwd","email","oldPhone");
        mockHttpSession.setAttribute("User",u);
        when(userService.updatePhone(newPhone,u)).thenReturn(true);

        Map<String, String> mockMap = new HashMap<>();
        mockMap.put("newPhone","newPhone");
        mockMap.put("mode","3");
        JSONObject mockJson = new JSONObject(mockMap);
        mockMvc.perform(post("/api/user/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJson.toString())
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"))
                .andExpect(MockMvcResultMatchers.request().sessionAttribute("User",u))
                .andDo(print())
                .andReturn();
        Assert.assertNotNull(u);
    }

    @Test
    public void testDeleteUser() throws Exception{
        User u = new User(1L,"name","pwd","email","oldPhone");
        mockHttpSession.setAttribute("User",u);
        when(userService.deleteUser(u)).thenReturn(true);
        User nullU = null;
        mockMvc.perform(post("/api/user/delete")
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andExpect(MockMvcResultMatchers.request().sessionAttribute("User",nullU))
                .andDo(print())
                .andReturn();
        Assert.assertNotNull(u);
    }

    @Test
    public void testLoginSucceeded() throws Exception{
        String name = "name";
        String pwd = "pwd";
        User u = new User(1L,"name","pwd","email","phone");
        when(userService.login(name,pwd)).thenReturn(u);

        Map<String, String> mockMap = new HashMap<>();
        mockMap.put("name","name");
        mockMap.put("pwd","pwd");
        JSONObject mockJson = new JSONObject(mockMap);
        mockMvc.perform(post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJson.toString())
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andExpect(MockMvcResultMatchers.request().sessionAttribute("User",u))
                .andDo(print())
                .andReturn();
        Assert.assertNotNull(u);
    }

    @Test
    public void testLoginFailed() throws Exception{
        String name = "name";
        String pwd = "wrongPwd";
        User u = new User(1L,"name","pwd","email","phone");
        when(userService.login(name,pwd)).thenReturn(null);

        Map<String, String> mockMap = new HashMap<>();
        mockMap.put("name",name);
        mockMap.put("pwd",pwd);
        JSONObject mockJson = new JSONObject(mockMap);
        User nullU = null;
        mockMvc.perform(post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJson.toString())
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"))
                .andExpect(MockMvcResultMatchers.request().sessionAttribute("User",nullU))
                .andDo(print())
                .andReturn();
        Assert.assertNotNull(u);
    }

    @Test
    public void testLogout() throws Exception{
        User u = new User(1L,"name","pwd","email","phone");
        mockHttpSession.setAttribute("User",u);
        User nullU = null;
        mockMvc.perform(post("/api/user/logout")
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andExpect(MockMvcResultMatchers.request().sessionAttribute("User",nullU))
                .andDo(print())
                .andReturn();
        Assert.assertNotNull(u);
    }

    @Test
    public void testCheckDupTrue() throws Exception{
        String name ="name";
        when(userService.checkDup(name)).thenReturn(true);

        mockMvc.perform(get("/api/user/checkDup")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("name",name)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testCheckDupFalse() throws Exception{
        String name ="name";
        when(userService.checkDup(name)).thenReturn(false);

        mockMvc.perform(get("/api/user/checkDup")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("name",name)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testGetRoles() throws Exception{
        User u = new User(1L,"name","pwd","email","phone");
        Role r = new Role("teacher");
        u.addRole(r);
        mockHttpSession.setAttribute("User",u);
        List<String> roles = new ArrayList<>();
        roles.add(r.getName());

        mockMvc.perform(get("/api/user/getRoles")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .session(mockHttpSession)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[\"teacher\"]"))
                .andDo(print())
                .andReturn();
    }
}