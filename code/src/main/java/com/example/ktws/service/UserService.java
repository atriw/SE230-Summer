package com.example.ktws.service;

import com.example.ktws.domain.User;

public interface UserService {

    Iterable<User> getAllUsers();

    boolean addNewUser(String name, String pwd, String email, String phone);

    boolean updatePwd(String oldPwd, String newPwd, User u);

    boolean updateEmail(String newEmail, User u);

    boolean updatePhone(String newPhone, User u);

    boolean deleteUser(User u);

    User login(String name, String pwd);

    boolean checkDup(String name);
}
