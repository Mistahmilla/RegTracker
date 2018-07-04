package org.cycop.reg.controller;

import org.cycop.reg.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserDAO userDAO;

    @GetMapping("/{userID}")
    public List getPerson(@PathVariable long userID) {
        return userDAO.getUserByAccountID(userID);
    }
}
