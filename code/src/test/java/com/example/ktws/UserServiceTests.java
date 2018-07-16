package com.example.ktws;

import com.example.ktws.domain.User;
import com.example.ktws.repository.UserRepository;
import com.example.ktws.service.UserService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User u1 = new User("n1", "pw1", "e1", "p1");
    private User u2 = new User("n2", "pw2", "e2", "p2");

    @Test
    @Transactional
    public void testGetAllUsers() {
        userRepository.save(u1);
        userRepository.save(u2);
        List<User> uList = new ArrayList<>();
        uList.add(u1);
        uList.add(u2);
        Iterable<User> iterable = userService.getAllUsers();
        int i = 0;
        for (User anIterable : iterable) {
            Assert.assertEquals(uList.get(i).getName(), anIterable.getName());
            Assert.assertEquals(uList.get(i).getPwd(), anIterable.getPwd());
            Assert.assertEquals(uList.get(i).getEmail(), anIterable.getEmail());
            Assert.assertEquals(uList.get(i).getPhone(), anIterable.getPhone());
            i++;
        }
    }

    @Test
    @Transactional
    public void testAddNewUserSucceeded() {
        User returnValue = userService.addNewUser(u1);
<<<<<<< Updated upstream
        List<User> u = userRepository.findByName("n1");
        Assert.assertEquals(returnValue, u1);
        Assert.assertEquals(u.get(0).getName(), u1.getName());
        Assert.assertEquals(u.get(0).getPwd(), u1.getPwd());
        Assert.assertEquals(u.get(0).getEmail(), u1.getEmail());
        Assert.assertEquals(u.get(0).getPhone(), u1.getPhone());
=======
        Optional<User> u = userRepository.findByName("n1");
        Assert.assertEquals(returnValue, u1);
        Assert.assertEquals(u.get().getName(), u1.getName());
        Assert.assertEquals(u.get().getPwd(), u1.getPwd());
        Assert.assertEquals(u.get().getEmail(), u1.getEmail());
        Assert.assertEquals(u.get().getPhone(), u1.getPhone());
>>>>>>> Stashed changes
    }

    @Test
    @Transactional
    public void testAddNewUserFailed() {
        userService.addNewUser(u1);
        Assert.assertNull(userService.addNewUser(u1));
    }


    @Test
    @Transactional
    public void testUpdatePwdSucceeded() {
        userService.addNewUser(u1);
        boolean returnValue = userService.updatePwd("pw1", "pw11", u1);
        Assert.assertTrue(returnValue);
<<<<<<< Updated upstream
        Assert.assertEquals(userRepository.findByName(u1.getName()).get(0).getPwd(), "pw11");
=======
        Assert.assertEquals(userRepository.findByName(u1.getName()).get().getPwd(), "pw11");
>>>>>>> Stashed changes
    }

    @Test
    @Transactional
    public void testUpdatePwdFailed() {
        userService.addNewUser(u1);
        boolean returnValue = userService.updatePwd("wrongOldPwd", "pw11", u1);
        Assert.assertFalse(returnValue);
<<<<<<< Updated upstream
        Assert.assertEquals(userRepository.findByName(u1.getName()).get(0).getPwd(), "pw1");
=======
        Assert.assertEquals(userRepository.findByName(u1.getName()).get().getPwd(), "pw1");
>>>>>>> Stashed changes
    }

    @Test
    @Transactional
    public void testUpdateEmail() {
        userService.addNewUser(u1);
        String newEmail = "newEmail";
        boolean returnValue = userService.updateEmail(newEmail, u1);
        Assert.assertTrue(returnValue);
<<<<<<< Updated upstream
        Assert.assertEquals(userRepository.findByName(u1.getName()).get(0).getEmail(), newEmail);
=======
        Assert.assertEquals(userRepository.findByName(u1.getName()).get().getEmail(), newEmail);
>>>>>>> Stashed changes
    }

    @Test
    @Transactional
    public void testUpdatePhone() {
        userService.addNewUser(u1);
        String newPhone = "newPhone";
        boolean returnValue = userService.updatePhone(newPhone, u1);
        Assert.assertTrue(returnValue);
<<<<<<< Updated upstream
        Assert.assertEquals(userRepository.findByName(u1.getName()).get(0).getPhone(), newPhone);
=======
        Assert.assertEquals(userRepository.findByName(u1.getName()).get().getPhone(), newPhone);
>>>>>>> Stashed changes
    }

    @Test
    @Transactional
    public void testDeleteUser() {
        userService.addNewUser(u1);
        boolean returnValue = userService.deleteUser(u1);
        Assert.assertTrue(returnValue);
<<<<<<< Updated upstream
        Assert.assertTrue(userRepository.findByName(u1.getName()).isEmpty());
=======
        Assert.assertFalse(userRepository.findByName(u1.getName()).isPresent());
>>>>>>> Stashed changes
    }

    @Test
    @Transactional
    public void testLoginSucceeded() {
        userService.addNewUser(u1);
        User u = userService.login("n1", "pw1");
        Assert.assertEquals(u, u1);
    }

    @Test
    @Transactional
    public void testLoginFailed() {
        userService.addNewUser(u1);
        User u1 = userService.login("n1", "wrongPwd");
        Assert.assertNull(u1);
        User u2 = userService.login("wrongName", "pw1");
        Assert.assertNull(u2);
    }

    @Test
    @Transactional
    public void testCheckDup() {
        userService.addNewUser(u1);
        boolean returnValue1 = userService.checkDup("n1");
        Assert.assertFalse(returnValue1);
        boolean returnValue2 = userService.checkDup("n9");
        Assert.assertTrue(returnValue2);
    }
}
