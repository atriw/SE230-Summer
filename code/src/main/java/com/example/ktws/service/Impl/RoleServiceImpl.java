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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Role addNewRole(String name) {
        Optional<Role> or = findByName(name);
        if (or.isPresent()) {
            logger.info("AddNewRole: Role {} already exists", name);
            return or.get();
        }
        Role r = new Role();
        r.setName(name);
        roleRepository.save(r);
        logger.info("AddNewRole: Successfully added a new role {} with rid {}", name, r.getId());
        return r;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}
