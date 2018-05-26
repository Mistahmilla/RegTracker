package org.cycop.reg.controller;

import org.cycop.reg.dao.PersonDAO;
import org.cycop.reg.dataobjects.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonDAO personDAO;

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
        return personDAO.get(personDAO.saveOrUpdate(input));
    }
}
