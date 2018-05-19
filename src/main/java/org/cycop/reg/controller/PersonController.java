package org.cycop.reg.controller;

import org.cycop.reg.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class PersonController {

    @Autowired
    private PersonDAO personDAO;

    @RequestMapping(value = "/person", method=GET)
    public List person(@RequestParam(value="personID", defaultValue="0") Long personID) {
        if (personID > 0){
            return personDAO.get(personID);
        }else {
            return personDAO.list();
        }
    }
}
