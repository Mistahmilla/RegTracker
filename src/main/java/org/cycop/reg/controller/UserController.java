package org.cycop.reg.controller;

import org.cycop.reg.dao.PersonDAO;
import org.cycop.reg.dao.RegistrationDAO;
import org.cycop.reg.dao.UserDAO;
import org.cycop.reg.dataobjects.Permission;
import org.cycop.reg.dataobjects.Person;
import org.cycop.reg.dataobjects.User;
import org.cycop.reg.dataobjects.validators.UserValidator;
import org.cycop.reg.security.AuthenticationFacade;
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

    @Autowired
    PersonDAO personDAO;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @GetMapping("/{userID}")
    public List getUser(@PathVariable long userID) {
        return userDAO.getUserByAccountID(userID);
    }

    @GetMapping("/{userID}/registrations")
    public List getUserRegistrations(@PathVariable long userID) {
        return registrationDAO.getRegistrationByAccount(userID);
    }

    @GetMapping("{userID}/person")
    public List getUserPeople(@PathVariable long userID){
        return personDAO.get(0, "", userID);
    }

    @PutMapping("{userID}/person")
    public List putUserPerson(@PathVariable long userID, @RequestBody Person input){

        if(getCurrentUser().get(0).getAccountID() != userID && !userHasPermission("PER_ADD_TO_ANY") ){
            throw new IllegalAccessError("User does not have the 'PER_ADD_TO_ANY' permission.");
        }

        if(!userHasPermission("PER_ADD") && !userHasPermission("PER_ADD_TO_ANY")){
            throw new IllegalAccessError("User does not have the 'PER_ADD' or 'PER_ADD_TO_ANY' permission.");
        }

        Person existingPerson;
        List<Person> l = personDAO.get(input.getPersonID(), "", 0);
        if (l.isEmpty()){
            existingPerson = personDAO.get(personDAO.saveOrUpdate(input), "", 0).get(0);
        }else{
            existingPerson = l.get(0);
        }

        userDAO.addPersonToAccount(userID, existingPerson.getPersonID());

        return getUserPeople(userID);
    }

    @DeleteMapping("{userID}/person/{personID}")
    public void deleteUserPerson(@PathVariable long userID, @PathVariable long personID){
        if(getCurrentUser().get(0).getAccountID() != userID && !userHasPermission("PER_DEL_ANY") ){
            throw new IllegalAccessError("User does not have the 'PER_DEL_ANY' permission.");
        }

        if(!userHasPermission("PER_DEL") && !userHasPermission("PER_DEL_ANY")){
            throw new IllegalAccessError("User does not have the 'PER_DEL' or 'PER_DEL_ANY' permission.");
        }

        userDAO.removePersonFromAccount(userID, personID);
    }

    @PutMapping
    public List updateUser(@RequestBody User input) {
        List<User> returnedUsers = getUser(input.getAccountID());

        if (returnedUsers.isEmpty()){
            throw new NullPointerException("User does not exist.");
        }

        if(input.getAccountID() != getCurrentUser().get(0).getAccountID() && !userHasPermission("USER_UPDATE_ANY")){
            throw new IllegalAccessError("User does not have the 'USER_UPDATE_ANY' permission.");
        }

        if(input.getAccountID() == getCurrentUser().get(0).getAccountID() && !userHasPermission("USER_UPDATE")){
            throw new IllegalAccessError("User does not have the 'USER_UPDATE' permission.");
        }

        //determine if roles are changing, if so require the 'USER_UPDATE_ROLE' permission
        if((!input.getRoles().containsAll(returnedUsers.get(0).getRoles()) || !returnedUsers.get(0).getRoles().containsAll(input.getRoles()) && !userHasPermission("USER_UPDATE_ROLE")) ){
            throw new IllegalAccessError("User does not have the 'USER_UPDATE_ROLE' permission.");
        }

        //if active/etc flags are changing ensure the logged in user has permission to change them
        if((input.getAccountLocked()!= returnedUsers.get(0).getAccountLocked() || input.getAccountVerified() != returnedUsers.get(0).getAccountVerified() || input.getPasswordExpired() != returnedUsers.get(0).getPasswordExpired()) && !userHasPermission("USER_ACTIVE_FLAGS")){
            throw new IllegalAccessError("User does not have the 'USER_ACTIVE_FLAGS' permission.");
        }

        //validate user
        DataBinder db = new DataBinder(input);
        db.setValidator(new UserValidator());
        db.bind(null);
        db.validate();
        BindingResult result = db.getBindingResult();

        if(!result.hasErrors()) {
            //update user
            return userDAO.getUserByAccountID(userDAO.updateExisting(input, userDAO.getUserByAccountID(input.getAccountID()).get(0)));

        }else{
            return result.getAllErrors();
        }

    }

    @GetMapping("/current")
    public List<User> getCurrentUser(){
        return userDAO.getUserByEmailAddress(authenticationFacade.getAuthentication().getName());
    }

    public boolean userHasPermission(String permissionCode){
        User currentUser = getCurrentUser().get(0);
        Permission p = new Permission();
        p.setPermissionCode(permissionCode);
        return currentUser.getPermissions().contains(p);
    }
}
