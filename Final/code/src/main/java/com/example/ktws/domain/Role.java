package com.example.ktws.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

    public void addUser(User user) {
        if (users.contains(user)) {
            return;
        }
        users.add(user);
        user.addRole(this);
    }

    public void removeUser(User user) {
        if (!users.contains(user)) {
            return;
        }
        users.remove(user);
        user.removeRole(this);
    }

    public Role(){}

    public Role(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Role) {
            Role role = (Role) obj;
            return Objects.equals(id, role.getId()) &&
                    name.equals(role.getName());
        }
        return false;
    }

    @Override
    public String toString() {
        return "[id=" +
                this.getId() +
                ", name=" +
                this.getName() +
                "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }
}
