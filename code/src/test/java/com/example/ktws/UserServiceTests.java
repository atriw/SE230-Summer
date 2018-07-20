package com.example.ktws;

import com.example.ktws.domain.Role;
import com.example.ktws.domain.User;
import com.example.ktws.repository.RoleRepository;
import com.example.ktws.repository.UserRepository;
import com.example.ktws.service.RoleService;
import com.example.ktws.service.UserService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("test")
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    private User u1 = new User("n1", "pw1", "e1", "p1");
    private User u2 = new User("n2", "pw2", "e2", "p2");
    private User admin = new User("admin","root","xx@xx.xx","99999999999");
    @Test
    @Transactional
    public void testGetAllUsers() {
        roleService.addNewRole(roleService.getTeacherRoleName());
        userRepository.save(u1);
        userRepository.save(u2);
        if(!userService.findByName("admin").isPresent()){
            userRepository.save(admin);
        }
        List<User> uList = new ArrayList<>();
        uList.add(userService.findByName("admin").get());
        uList.add(u1);
        uList.add(u2);
        List<User> iterable = (List<User>) userService.getAllUsers();
        iterable.sort(Comparator.comparing(User::getName));
        Assert.assertEquals(iterable, uList);
    }

    @Test
    @Transactional
    public void testAddNewUserSucceeded() {
        Role roleTeacher = roleService.addNewRole(roleService.getTeacherRoleName());
        User returnValue = userService.addNewUser(u1);
        Set<Role> roles = new HashSet<>();
        roles.add(roleTeacher);
        Optional<User> u = userRepository.findByName("n1");
        Assert.assertEquals(returnValue, u1);
        Assert.assertEquals(u.get().getName(), u1.getName());
        Assert.assertEquals(u.get().getPwd(), u1.getPwd());
        Assert.assertEquals(u.get().getEmail(), u1.getEmail());
        Assert.assertEquals(u.get().getPhone(), u1.getPhone());
        Assert.assertEquals(roles,u1.getRoles());
    }

    @Test
    @Transactional
    public void testAddNewUserFailed() {
        roleService.addNewRole(roleService.getTeacherRoleName());
        userService.addNewUser(u1);
        Assert.assertNull(userService.addNewUser(u1));
    }


    @Test
    @Transactional
    public void testUpdatePwdSucceeded() {
        roleService.addNewRole(roleService.getTeacherRoleName());
        userService.addNewUser(u1);
        boolean returnValue = userService.updatePwd("pw1", "pw11", u1);
        Assert.assertTrue(returnValue);
        Assert.assertEquals(userRepository.findByName(u1.getName()).get().getPwd(), "pw11");
    }

    @Test
    @Transactional
    public void testUpdatePwdFailed() {
        roleService.addNewRole(roleService.getTeacherRoleName());
        userService.addNewUser(u1);
        boolean returnValue = userService.updatePwd("wrongOldPwd", "pw11", u1);
        Assert.assertFalse(returnValue);
        Assert.assertEquals(userRepository.findByName(u1.getName()).get().getPwd(), "pw1");
    }

    @Test
    @Transactional
    public void testUpdateEmail() {
        roleService.addNewRole(roleService.getTeacherRoleName());
        userService.addNewUser(u1);
        String newEmail = "newEmail";
        boolean returnValue = userService.updateEmail(newEmail, u1);
        Assert.assertTrue(returnValue);
        Assert.assertEquals(userRepository.findByName(u1.getName()).get().getEmail(), newEmail);
    }

    @Test
    @Transactional
    public void testUpdatePhone() {
        roleService.addNewRole(roleService.getTeacherRoleName());
        userService.addNewUser(u1);
        String newPhone = "newPhone";
        boolean returnValue = userService.updatePhone(newPhone, u1);
        Assert.assertTrue(returnValue);
        Assert.assertEquals(userRepository.findByName(u1.getName()).get().getPhone(), newPhone);
    }

    @Test
    @Transactional
    public void testDeleteUser() {
        roleService.addNewRole(roleService.getTeacherRoleName());
        userService.addNewUser(u1);
        boolean returnValue = userService.deleteUser(u1);
        Assert.assertTrue(returnValue);
        Assert.assertFalse(userRepository.findByName(u1.getName()).isPresent());
    }

    @Test
    @Transactional
    public void testLoginSucceeded() {
        roleService.addNewRole(roleService.getTeacherRoleName());
        userService.addNewUser(u1);
        User u = userService.login("n1", "pw1");
        Assert.assertEquals(u, u1);
    }

    @Test
    @Transactional
    public void testLoginFailed() {
        roleService.addNewRole(roleService.getTeacherRoleName());
        userService.addNewUser(u1);
        User u1 = userService.login("n1", "wrongPwd");
        Assert.assertNull(u1);
        User u2 = userService.login("wrongName", "pw1");
        Assert.assertNull(u2);
    }

    @Test
    @Transactional
    public void testCheckDup() {
        roleService.addNewRole(roleService.getTeacherRoleName());
        userService.addNewUser(u1);
        boolean returnValue1 = userService.checkDup("n1");
        Assert.assertFalse(returnValue1);
        boolean returnValue2 = userService.checkDup("n9");
        Assert.assertTrue(returnValue2);
    }

    @Test
    @Transactional
    public void testFindByName() {
        roleService.addNewRole(roleService.getTeacherRoleName());
        userRepository.save(u1);
        Optional<User> u = userService.findByName(u1.getName());
        Assert.assertEquals(u.get(),u1);
    }
}
