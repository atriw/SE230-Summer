package com.example.ktws.service.Impl;

import com.example.ktws.domain.User;
import com.example.ktws.repository.UserRepository;
import com.example.ktws.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    public User addNewUser(String name, String pwd, String email, String phone){
        Optional<User> ou = userRepository.findByName(name);
        if (ou.isPresent()) {
            return ou.get();
        }
        User n = new User();
        n.setName(name);
        n.setPwd(pwd);
        n.setEmail(email);
        n.setPhone(phone);
        return userRepository.save(n);
    }

    @Override
    public boolean updatePwd(String oldPwd, String newPwd, HttpServletRequest request){
        User u = (User)request.getSession().getAttribute("User");
        if(u != null){
            if(u.getPwd().equals(oldPwd)) {
                u.setPwd(newPwd);
                userRepository.save(u);
                request.setAttribute("User",u);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateEmail(String newEmail, HttpServletRequest request){
        User u = (User)request.getSession().getAttribute("User");
        if(u != null){
            u.setEmail(newEmail);
            userRepository.save(u);
            request.setAttribute("User",u);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePhone(String newPhone, HttpServletRequest request){
        User u = (User)request.getSession().getAttribute("User");
        if(u != null){
            u.setPhone(newPhone);
            userRepository.save(u);
            request.setAttribute("User",u);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(HttpServletRequest request){
        User u = (User)request.getSession().getAttribute("User");
        userRepository.delete(u);
        request.getSession().invalidate();
        return true;
    }
    @Override
    public boolean login(String name, String pwd, HttpServletRequest request){
        Optional<User> tmp = userRepository.findByName(name);
        if(tmp.isPresent() && (tmp.get().getPwd().equals(pwd))){
            request.getSession().setAttribute("User",tmp.get());
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
        return !userRepository.findByName(name).isPresent();
    }
}
