package org.cycop.reg.controller;

import org.cycop.reg.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.cycop.reg.dataobjects.Person;
import org.cycop.reg.dao.PersonDAOImpl;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

@RestController
public class PersonController {

    @Autowired
    private PersonDAO personDAO;

    @RequestMapping("/person")
    public Person person(@RequestParam(value="id", defaultValue="") Long id) {
        return personDAO.get((long)1);
    }
}
