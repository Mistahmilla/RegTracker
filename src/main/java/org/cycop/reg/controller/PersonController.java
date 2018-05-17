package org.cycop.reg.controller;

import org.cycop.reg.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class PersonController {

    @Autowired
    private PersonDAO personDAO;

    @RequestMapping(value = "/person", method=GET)
    public List person(@RequestParam(value="id", defaultValue="0") Long id) {
        if (id > 0){
            ArrayList l = new ArrayList();
            l.add(personDAO.get(id));
            return l;
        }else {
            return personDAO.list();
        }
    }
}
