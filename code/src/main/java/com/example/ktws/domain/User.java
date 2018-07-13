package com.example.ktws.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String pwd;

    private String name;

    private String email;

    private String phone;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Course> courses = new HashSet<>();

    public void addRole(Role role) {
        if (roles.contains(role)) {
            return;
        }
        roles.add(role);
        role.addUser(this);
    }

    public void removeRole(Role role) {
        if (!roles.contains(role)) {
            return;
        }
        roles.remove(role);
        role.removeUser(this);
    }

    public void addCourse(Course course) {
        if (courses.contains(course)) {
            return;
        }
        courses.add(course);
        course.setUser(this);
    }

    public void removeCourse(Course course) {
        if (!courses.contains(course)) {
            return;
        }
        courses.remove(course);
        course.setUser(null);
    }

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

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
