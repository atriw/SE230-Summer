package com.example.ktws.service.Impl;

import com.example.ktws.domain.User;
import com.example.ktws.repository.UserRepository;
import com.example.ktws.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
<<<<<<< Updated upstream
    @Transactional(rollbackOn = {Exception.class, RuntimeException.class, Error.class})
    public User addNewUser(User u){
        if(!userRepository.findByName(u.getName()).isEmpty()) {
            return null;
        }
        userRepository.save(u);
        return u;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class, RuntimeException.class, Error.class})
=======
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
>>>>>>> Stashed changes
    public boolean updatePwd(String oldPwd, String newPwd, User u){
        if(u.getPwd().equals(oldPwd)) {
            u.setPwd(newPwd);
            userRepository.save(u);
            return true;
        }
        return false;
    }

    @Override
<<<<<<< Updated upstream
    @Transactional(rollbackOn = {Exception.class, RuntimeException.class, Error.class})
=======
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, Error.class})
>>>>>>> Stashed changes
    public boolean updateEmail(String newEmail, User u){
        u.setEmail(newEmail);
        userRepository.save(u);
        return true;
    }

    @Override
<<<<<<< Updated upstream
    @Transactional(rollbackOn = {Exception.class,RuntimeException.class, Error.class})
=======
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class, Error.class})
>>>>>>> Stashed changes
    public boolean updatePhone(String newPhone, User u){
        u.setPhone(newPhone);
        userRepository.save(u);
        return true;
    }

    @Override
<<<<<<< Updated upstream
    @Transactional(rollbackOn = {Exception.class,RuntimeException.class, Error.class})
=======
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class, Error.class})
>>>>>>> Stashed changes
    public boolean deleteUser(User u){
        userRepository.delete(u);
        return true;
    }

    @Override
    public User login(String name, String pwd){
<<<<<<< Updated upstream
        List<User> tmp = userRepository.findByName(name);
        if(!tmp.isEmpty() && (tmp.get(0).getPwd().equals(pwd))){
            return tmp.get(0);
=======
        Optional<User> tmp = userRepository.findByName(name);
        if(tmp.isPresent() && (tmp.get().getPwd().equals(pwd))){
            return tmp.get();
>>>>>>> Stashed changes
        }
        return null;
    }


    @Override
    public boolean checkDup(String name){
        return userRepository.findByName(name).isEmpty();
    }
}