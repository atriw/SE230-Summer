package com.example.ktws.controller;

import com.example.ktws.domain.Stat;
import com.example.ktws.domain.User;
import com.example.ktws.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/stat")
public class StatController {
    @Autowired
    private StatService statService;

    @GetMapping("/byLastCourse")
    public Iterable<Stat> getStatsByLastCourse(HttpServletRequest httpServletRequest) {
        User u = (User) httpServletRequest.getSession().getAttribute("User");
        if (u == null) {
            return null;
        }
        return statService.getStatsByLastCourse(u);
    }
}
