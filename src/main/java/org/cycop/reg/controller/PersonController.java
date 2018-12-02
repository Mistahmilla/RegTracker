package org.cycop.reg.controller;

import org.cycop.reg.dao.PersonAddressDAO;
import org.cycop.reg.dao.PersonDAO;
import org.cycop.reg.dao.RegistrationDAO;
import org.cycop.reg.dao.UserDAO;
import org.cycop.reg.dataobjects.Address;
import org.cycop.reg.dataobjects.Person;
import org.cycop.reg.dataobjects.User;
import org.cycop.reg.dataobjects.validators.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private PersonAddressDAO personAddressDAO;

    @Autowired
    private RegistrationDAO registrationDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserController userController;

    @GetMapping("/{personID}")
    public List getPerson(@PathVariable long personID) {
        return personDAO.get(personID, "", 0);
    }

    @GetMapping("/{personID}/registrations")
    public List getPersonRegistrations(@PathVariable long personID){
        return registrationDAO.getRegistrationByPerson(personID);
    }

    @GetMapping
    public List personSearch(@RequestParam(value="personID", defaultValue="0") long personID, @RequestParam(value="personName", defaultValue="") String personName, @RequestParam(value="accountID", defaultValue="0") long accountID) {

        return personDAO.get(personID, personName, accountID);
    }

    @DeleteMapping("/{personID}")
    public void deletePerson(@PathVariable long personID){
        personDAO.delete(personID);
    }

    @PutMapping
    public List addPerson(@RequestBody Person input, @RequestParam(value="addToAccount", defaultValue="Y") String addToAccount){
        long personID;

        List existingAddresses;

        DataBinder db = new DataBinder(input);
        db.setValidator(new PersonValidator());
        db.bind(null);
        db.validate();
        BindingResult result = db.getBindingResult();

        if(!result.hasErrors()) {
            personID = personDAO.saveOrUpdate(input);
            if(input.getCurrentAddress() != null){
                existingAddresses = personAddressDAO.get(personID);
                if((existingAddresses.size()==1 && !addressesMatch(input.getCurrentAddress(), (Address)existingAddresses.get(0))) || existingAddresses.isEmpty()) {
                    personAddressDAO.set(personID, input.getCurrentAddress());
                }
            }

            if(addToAccount.equals("Y")){
                List<User> userList;
                User currentUser;
                //get the current user and add the person to the account
                userList = userController.getCurrentUser();
                if (!userList.isEmpty()) {
                    currentUser = userList.get(0);
                    userDAO.addPersonToAccount(currentUser.getAccountID(), personID);
                }
            }

            return personDAO.get(personID, "", 0);
        }else{
            return result.getAllErrors();
        }
    }

    private boolean addressesMatch(Address a, Address b){
        return a.getStreetAddress().equalsIgnoreCase(b.getStreetAddress())
                && a.getCity().equalsIgnoreCase(b.getCity())
                && a.getState().equalsIgnoreCase(b.getState())
                && a.getZipCode().equalsIgnoreCase(b.getZipCode());
    }
}
