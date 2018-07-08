package com.example.ktws.controller;

import com.example.ktws.domain.User;
import com.example.ktws.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/all")
    public Iterable<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/add")
    public boolean addNewUser(@RequestBody Map map){
        return userService.addNewUser((String)map.get("name"), (String)map.get("pwd"), (String)map.get("email"), (String)map.get("phone"));
    }

    @PostMapping("/login")
    public boolean checkUsers(@RequestBody Map map, HttpServletRequest request) {
        return userService.login((String)map.get("name"),(String)map.get("pwd"),request);
    }

    @GetMapping("/logOut")
    public boolean logOut(HttpServletRequest request) {
        return userService.logOut(request);
    }

    @GetMapping("/checkDup")
    public boolean findUsers(@RequestParam String name){
        return userService.checkDup(name);
    }
}
