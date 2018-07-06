package com.example.ktws.service;

import com.example.ktws.domain.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    Iterable<User> getAllUsers();

    boolean addNewUser(String name, String pwd, String email, String phone);

    boolean login(String name, String pwd, HttpServletRequest request);

    boolean logOut(HttpServletRequest request);

    boolean checkDup(String name);
}
