package com.example.ktws.runner;

import com.example.ktws.domain.Role;
import com.example.ktws.domain.User;
import com.example.ktws.repository.UserRepository;
import com.example.ktws.service.RoleService;
import com.example.ktws.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Profile("!test")
@Component
@Order(value=1)
public class DatabaseInitializationRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializationRunner.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    private static final String ADMINISTRATOR_USER_NAME = "admin";

    private static final String ADMINISTRATOR_USER_PWD = "root";

    private static final String ADMINISTRATOR_USER_EMAIL = "xx@xx.xx";

    private static final String ADMINISTRATOR_USER_PHONE = "99999999999";
    @Override
    public void run(String... args) throws Exception {
        logger.info("app start! initializing database");
        if(!roleService.findByName(roleService.getTeacherRoleName()).isPresent()){
            roleService.addNewRole(roleService.getTeacherRoleName());
            logger.info("role teacher added successfully!");
        }
        if(!roleService.findByName(roleService.getEARoleName()).isPresent()){
            roleService.addNewRole(roleService.getEARoleName());
            logger.info("role EA added successfully!");
        }

        if(!userService.findByName(ADMINISTRATOR_USER_NAME).isPresent()){
            User u = new User(ADMINISTRATOR_USER_NAME,ADMINISTRATOR_USER_PWD,ADMINISTRATOR_USER_EMAIL,ADMINISTRATOR_USER_PHONE);
            Optional<Role> eARole = roleService.findByName(roleService.getEARoleName());
            u.addRole(eARole.get());
            userService.addNewUser(u);
            logger.info("EA user added successfully!");
        }

    }
}
