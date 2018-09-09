package com.example.ktws.service;

import com.example.ktws.domain.User;

import java.util.Optional;

public interface UserService {

    Iterable<User> getAllUsers();

    User addNewUser(User u);

    boolean updatePwd(String oldPwd, String newPwd, User u);

    boolean updateEmail(String newEmail, User u);

    boolean updatePhone(String newPhone, User u);

    boolean deleteUser(User u);

    User login(String name, String pwd);

    boolean checkDup(String name);

    Optional<User> findByName(String name);
}