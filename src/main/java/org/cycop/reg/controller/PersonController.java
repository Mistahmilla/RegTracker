package org.cycop.reg.controller;

import org.cycop.reg.dao.PersonDAO;
import org.cycop.reg.dataobjects.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

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
    public List getPerson(){
        return personDAO.list();
    }

    @DeleteMapping
    public void deletePerson(@RequestParam(value="personID") Long personID){
        personDAO.delete(personID);
    }

    @PutMapping
    public List addPerson(@RequestBody Person input){
        return personDAO.get(personDAO.saveOrUpdate(input));
    }
}
