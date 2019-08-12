package com.example.ktws;

import com.example.ktws.domain.Role;
import com.example.ktws.repository.RoleRepository;
import com.example.ktws.service.RoleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("test")
public class RoleServiceTests {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    private Role role = new Role("roleName");

    @Test
    @Transactional
    public void testAddNewRoleAlreadyHad(){
        roleRepository.save(role);
        Optional<Role> r = roleRepository.findByName(role.getName());
        Assert.assertTrue(r.isPresent());
        Assert.assertEquals(roleService.addNewRole(role.getName()),r.get());
    }

    @Test
    @Transactional
    public void testAddNewRoleSucceeded(){
        Optional<Role> r = roleRepository.findByName(role.getName());
        Assert.assertFalse(r.isPresent());
        Role newRole = roleService.addNewRole(role.getName());
        role.setId(newRole.getId());
        Assert.assertEquals(newRole, role);
    }

    @Test
    @Transactional
    public void testFindByName(){
        roleRepository.save(role);
        Optional<Role> r = roleService.findByName(role.getName());
        Assert.assertEquals(r.get(),role);
    }

    @Test
    public void testGetTeacherRoleName(){
        Assert.assertEquals("teacher", roleService.getTeacherRoleName());
    }

    @Test
    public void testGetEARoleName(){
        Assert.assertEquals("EA", roleService.getEARoleName());
    }
}
