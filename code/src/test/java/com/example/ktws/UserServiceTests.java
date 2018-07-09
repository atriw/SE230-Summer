package com.example.ktws;

import com.example.ktws.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KtwsApplication.class)
@WebAppConfiguration
public class UserServiceTests {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void setupMockMvc() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        response = new MockHttpServletResponse();
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testAddNewUser() throws Exception{
        Map<String, String> mockMap1 = new HashMap<>();
        mockMap1.put("name", "luna");
        mockMap1.put("pwd","friend");
        mockMap1.put("email","kill@endless.duel");
        mockMap1.put("phone","22110776451");
        JSONObject mockJson1 = new JSONObject(mockMap1);

        mockMvc.perform(post("/api/user/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJson1.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testLogin() throws Exception
    {
        Map<String, String> mockMap1 = new HashMap<>();
        mockMap1.put("name", "luna");
        mockMap1.put("pwd", "friend");
        JSONObject mockJson1 = new JSONObject(mockMap1);
        mockMvc.perform(post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(mockJson1.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(request().attribute("User",notNullValue()))
                .andExpect(content().string("false"));
//        mockMvc.perform(post("/api/user/login")
//                .param("name","luna")
//                .param("pwd","enemy"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("false"));
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testLogOut() throws Exception
    {
        mockMvc.perform(get("/api/user/logOut"))
                .andExpect(status().isOk())
                .andExpect(request().attribute("User",isEmptyOrNullString()));
    }

//    @Test
//    public void testGetUsers() throws Exception
//    {
//        mockMvc.perform(get("/api/user/all"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.p2pdata", notNullValue()));
//    }

}

