package org.cycop.reg.controller;

import org.cycop.reg.dao.UserDAO;
import org.cycop.reg.dataobjects.User;
import org.cycop.reg.dataobjects.repositories.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserDAO userDAO;

    @Autowired
    RoleRepository roleRepository;

    @PostMapping
    public List registerAccount(@RequestBody User input){
        if(!roleRepository.getRoleByCode("USER").isEmpty()) {
            input.addRole(roleRepository.getRoleByCode("USER").get(0));
        }
        logger.info("Creating new user");
        long userID = userDAO.createNew(input);
        logger.info("User created: {}", userID);
        return userDAO.getUserByAccountID(userID);
    }
}
