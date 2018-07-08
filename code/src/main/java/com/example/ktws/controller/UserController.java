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
    public boolean addNewUser(@RequestParam(value="name") String name, @RequestParam(value="pwd") String pwd,
                      @RequestParam(value="email") String email, @RequestParam(value="phone") String phone){
        return userService.addNewUser(name, pwd, email, phone);
    }

//    @PostMapping("/login")
//    public boolean checkUsers(@RequestParam(value="name") String name, @RequestParam(value="pwd") String pwd,HttpServletRequest request) {
//        return userService.login(name,pwd,request);
//    }

    @PostMapping("/login")
    public boolean checkUsers(@RequestBody Map map, HttpServletRequest request) {
        return userService.login((String) map.get("name"), (String) map.get("pwd"), request);
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
