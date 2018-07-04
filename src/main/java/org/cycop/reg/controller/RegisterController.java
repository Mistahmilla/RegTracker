package org.cycop.reg.controller;

import org.cycop.reg.dao.RoleDAO;
import org.cycop.reg.dao.UserDAO;
import org.cycop.reg.dataobjects.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
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
    RoleDAO roleDAO;

    @PutMapping
    public List registerAccount(@RequestBody User input){
        input.addRole(roleDAO.get("USER").get(0));
        logger.info("Creating new user");
        long userID = userDAO.SaveOrUpdate(input);
        logger.info("User created: " + userID);
        return userDAO.getUserByAccountID(userID);
    }
}
