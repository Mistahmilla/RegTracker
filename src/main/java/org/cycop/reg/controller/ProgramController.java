package org.cycop.reg.controller;

import org.cycop.reg.dao.ProgramDAO;
import org.cycop.reg.dao.RegistrationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/program")
public class ProgramController {

    @Autowired
    private ProgramDAO programDAO;

    @Autowired
    RegistrationDAO registrationDAO;

    @GetMapping("/{programID}")
    public List getProgram(@PathVariable long programID) {
        return programDAO.get(programID);
    }

    @GetMapping("/{programID}/registrations")
    public List getProgramRegistrations(@PathVariable long programID){
        return  registrationDAO.getRegistrationByProgram(programID);
    }
}
