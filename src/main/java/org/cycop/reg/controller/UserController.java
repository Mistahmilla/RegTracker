package org.cycop.reg.controller;

import org.cycop.reg.dao.RegistrationDAO;
import org.cycop.reg.dao.UserDAO;
import org.cycop.reg.dataobjects.User;
import org.cycop.reg.dataobjects.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    private RegistrationDAO registrationDAO;

    @GetMapping("/{userID}")
    public List getUser(@PathVariable long userID) {
        return userDAO.getUserByAccountID(userID);
    }

    @GetMapping("/{userID}/registrations")
    public List getUserRegistrations(@PathVariable long userID) {
        return registrationDAO.getRegistrationByAccount(userID);
    }

    @PutMapping
    public List updateUser(@RequestBody User input){
        //TODO; if it's a user only allow them to update their own user
        User existingUser = (User)getUser(input.getAccountID()).get(0);
        if (existingUser == null){
            throw new NullPointerException("User does not exist.");
        }

        //validate user
        DataBinder db = new DataBinder(input);
        db.setValidator(new UserValidator());
        db.bind(null);
        db.validate();
        BindingResult result = db.getBindingResult();

        //TODO: if roles are changing add logic to ensure they have permission to update roles
        //TODO: if active/etc flags are changing ensure the logged in user has permission to change them
        if(!result.hasErrors()) {
            //update user
            return userDAO.getUserByAccountID(userDAO.updateExisting(input, userDAO.getUserByAccountID(input.getAccountID()).get(0)));

        }else{
            return result.getAllErrors();
        }

    }
}
