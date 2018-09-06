package com.example.ktws.vo;

import com.example.ktws.domain.User;

public class UserInfo {
    private Long id;

    private String name;

    private Integer courseNum;

    private String email;

    private String phone;

    public UserInfo() {}

    public UserInfo(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.courseNum = user.getCourses().size();
        this.email = user.getEmail();
        this.phone = user.getPhone();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(Integer coursenum) {
        this.courseNum = courseNum;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
