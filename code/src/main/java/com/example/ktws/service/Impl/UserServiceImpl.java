package com.example.ktws.service.Impl;

import com.example.ktws.domain.Role;
import com.example.ktws.domain.User;
import com.example.ktws.repository.UserRepository;
import com.example.ktws.service.RoleService;
import com.example.ktws.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, Error.class})
    public User addNewUser(User u) {
        Optional<User> ou = userRepository.findByName(u.getName());
        if (ou.isPresent()) {
            logger.error("User [name={}] already exists", u.getName());
            return null;
        }
        Role roleTeacher = roleService.findByName(roleService.getTeacherRoleName()).get();
        u.addRole(roleTeacher);
        userRepository.save(u);
        logger.info("AddNewUser: Added user {}", u);
        return u;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, Error.class})
    public boolean updatePwd(String oldPwd, String newPwd, User u){
        if(u.getPwd().equals(oldPwd)) {
            u.setPwd(newPwd);
            userRepository.save(u);
            logger.info("UpdatePwd: updated oldPwd {} to newPwd {} of user [name={}]", oldPwd, newPwd, u.getName());
            return true;
        }
        logger.error("Wrong password");
        return false;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, Error.class})
    public boolean updateEmail(String newEmail, User u){
        String oldEmail = u.getEmail();
        u.setEmail(newEmail);
        userRepository.save(u);
        logger.info("UpdateEmail: Updated oldEmail {} to newEmail {} of user [name={}]", oldEmail, newEmail, u.getName());
        return true;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class, Error.class})
    public boolean updatePhone(String newPhone, User u){
        String oldPhone = u.getPhone();
        u.setPhone(newPhone);
        userRepository.save(u);
        logger.info("UpdatePhone: Updated oldPhone {} to newPhone {} of user [name={}]", oldPhone, newPhone, u.getName());
        return true;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class, Error.class})
    public boolean deleteUser(User u){
        logger.info("DeleteUser: Deleted user {}", u);
        userRepository.delete(u);
        return true;
    }

    @Override
    public User login(String name, String pwd){
        Optional<User> tmp = userRepository.findByName(name);
        if(tmp.isPresent() && (tmp.get().getPwd().equals(pwd))){
            logger.info("Login: Success");
            return tmp.get();
        }
        logger.error("Fail to login");
        return null;
    }


    @Override
    public boolean checkDup(String name){
        return !userRepository.findByName(name).isPresent();
    }

    @Override
    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }
}