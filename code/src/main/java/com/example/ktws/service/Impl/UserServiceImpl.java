package com.example.ktws.service.Impl;

import com.example.ktws.domain.User;
import com.example.ktws.repository.UserRepository;
import com.example.ktws.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, Error.class})
    public User addNewUser(User u) {
        Optional<User> ou = userRepository.findByName(u.getName());
        if (ou.isPresent()) {
            return null;
        }
        return userRepository.save(u);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, Error.class})
    public boolean updatePwd(String oldPwd, String newPwd, User u){
        if(u.getPwd().equals(oldPwd)) {
            u.setPwd(newPwd);
            userRepository.save(u);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, Error.class})
    public boolean updateEmail(String newEmail, User u){
        u.setEmail(newEmail);
        userRepository.save(u);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class, Error.class})
    public boolean updatePhone(String newPhone, User u){
        u.setPhone(newPhone);
        userRepository.save(u);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class, Error.class})
    public boolean deleteUser(User u){
        userRepository.delete(u);
        return true;
    }

    @Override
    public User login(String name, String pwd){
        Optional<User> tmp = userRepository.findByName(name);
        if(tmp.isPresent() && (tmp.get().getPwd().equals(pwd))){
            return tmp.get();
        }
        return null;
    }


    @Override
    public boolean checkDup(String name){
        return !userRepository.findByName(name).isPresent();
    }
}