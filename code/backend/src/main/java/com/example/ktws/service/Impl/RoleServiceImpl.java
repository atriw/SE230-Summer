package com.example.ktws.service.Impl;

import com.example.ktws.domain.Role;
import com.example.ktws.repository.RoleRepository;
import com.example.ktws.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    private static final String ROLE_TEACHER = "teacher";

    private static final String ROLE_E_ADMINISTRATOR = "EA";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Role addNewRole(String name) {
        Optional<Role> or = findByName(name);
        if (or.isPresent()) {
            logger.info("AddNewRole: Role [name={}] already exists", name);
            return or.get();
        }
        Role r = new Role();
        r.setName(name);
        roleRepository.save(r);
        logger.info("AddNewRole: Added role {}", r);
        return r;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public String getTeacherRoleName(){
        return ROLE_TEACHER;
    }

    @Override
    public String getEARoleName(){
        return ROLE_E_ADMINISTRATOR;
    }
}
