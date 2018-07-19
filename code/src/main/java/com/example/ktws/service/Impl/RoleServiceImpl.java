package com.example.ktws.service.Impl;

import com.example.ktws.domain.Role;
import com.example.ktws.repository.RoleRepository;
import com.example.ktws.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role addNewRole(String name) {
        Optional<Role> or = findByName(name);
        if (or.isPresent()) {
            return or.get();
        }
        Role r = new Role();
        r.setName(name);
        return roleRepository.save(r);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}
