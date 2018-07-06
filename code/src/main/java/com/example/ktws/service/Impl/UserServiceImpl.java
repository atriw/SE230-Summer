package com.example.ktws.service.Impl;

import com.example.ktws.domain.User;
import com.example.ktws.repository.UserRepository;
import com.example.ktws.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    public boolean addNewUser(String name, String pwd, String email, String phone){
        if(!userRepository.findByName(name).isEmpty()) {
            return false;
        }
        User n = new User();
        n.setName(name);
        n.setPwd(pwd);
        n.setEmail(email);
        n.setPhone(phone);
        userRepository.save(n);
        return true;
    }

    @Override
    public boolean login(String name, String pwd, HttpServletRequest request){
        List<User> tmp = userRepository.findByName(name);
        if(!tmp.isEmpty() && (tmp.get(0).getPwd().equals(pwd))){
            request.getSession().setAttribute("User",tmp.get(0));
            return true;
        }
        return false;
    }

    @Override
    public boolean logOut(HttpServletRequest request){
        request.getSession().invalidate();
        return true;
    }

    @Override
    public boolean checkDup(String name){
        return userRepository.findByName(name).isEmpty();
    }
}
