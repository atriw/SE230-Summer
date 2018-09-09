package com.example.ktws.service;

import com.example.ktws.domain.Role;

import java.util.Optional;

public interface RoleService {
    Role addNewRole(String name);

    Optional<Role> findByName(String name);

    String getTeacherRoleName();

    String getEARoleName();
}
