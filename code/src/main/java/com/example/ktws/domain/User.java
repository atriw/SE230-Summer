package com.example.ktws.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String pwd;

    private String name;

    private String email;

    private String phone;

//    private String role;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User(String name,String pwd,String email, String phone){
        this.name = name;
        this.pwd = pwd;
        this.email = email;
        this.phone = phone;
    }

    public User(Long id,String name,String pwd,String email, String phone){
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.email = email;
        this.phone = phone;
    }

    public User(){
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User user = (User) obj;
            System.out.println("ME: "+id+" "+name+" "+pwd+" "+email+" "+phone);
            System.out.println("NOTME: "+user.getId()+" "+user.getName()+" "+user.getPwd()+" "+user.getEmail()+" "+user.getPhone());
            return Objects.equals(id, user.getId()) &&
                   name.equals(user.getName())&&
                   pwd.equals(user.getPwd())&&
                   email.equals(user.getEmail())&&
                   phone.equals(user.getPhone());
        }
        return false;
    }
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
}
