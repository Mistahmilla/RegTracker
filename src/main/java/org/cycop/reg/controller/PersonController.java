package org.cycop.reg.controller;

import org.cycop.reg.dao.PersonAddressDAO;
import org.cycop.reg.dao.PersonDAO;
import org.cycop.reg.dataobjects.Address;
import org.cycop.reg.dataobjects.Person;
import org.cycop.reg.dataobjects.validators.PersonValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private PersonAddressDAO personAddressDAO;

    @GetMapping("/{personID}")
    public List getPerson(@PathVariable long personID) {
        return personDAO.get(personID);
    }

    @GetMapping
    public List personSearch(@RequestParam(value="personID", defaultValue="0") long personID, @RequestParam(value="personName", defaultValue="") String personName) {
        if (personID != 0) {
            return personDAO.get(personID);
        }
        if (!personName.equals("")) {
            return personDAO.get(personName);
        }
        return personDAO.get();
    }

    @DeleteMapping("/{personID}")
    public void deletePerson(@PathVariable long personID){
        personDAO.delete(personID);
    }

    @PutMapping
    public List addPerson(@RequestBody Person input){
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
                logger.info("existingAddresses.size()="+existingAddresses.size());
                if((existingAddresses.size()==1 && !addressesMatch(input.getCurrentAddress(), (Address)existingAddresses.get(0))) || existingAddresses.size() == 0) {
                    personAddressDAO.set(personID, input.getCurrentAddress());
                }
            }
            return personDAO.get(personID);
        }else{
            return result.getAllErrors();
        }
    }

    private boolean addressesMatch(Address a, Address b){
        if (a.getStreetAddress().equalsIgnoreCase(b.getStreetAddress())
                && a.getCity().equalsIgnoreCase(b.getCity())
                && a.getState().equalsIgnoreCase(b.getState())
                && a.getZipCode().equalsIgnoreCase(b.getZipCode())){
            return true;
        }else{
            return false;
        }
    }
}
