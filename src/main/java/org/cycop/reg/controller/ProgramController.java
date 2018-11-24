package org.cycop.reg.controller;

import org.cycop.reg.dao.ProgramDAO;
import org.cycop.reg.dao.RegistrationDAO;
import org.cycop.reg.dataobjects.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/program")
public class ProgramController {

    @Autowired
    private ProgramDAO programDAO;

    @Autowired
    RegistrationDAO registrationDAO;

    @GetMapping
    public List getAllPrograms(){
        return programDAO.get();
    }

    @GetMapping("/{programID}")
    public List getProgram(@PathVariable long programID) {
        return programDAO.get(programID);
    }

    @GetMapping("/{programID}/registrations")
    public List getProgramRegistrations(@PathVariable long programID){
        //TODO: for users only return their registrations
        return  registrationDAO.getRegistrationByProgram(programID);
    }

    @PutMapping("/{programID}/registrations")
    public List putProgramRegistration(@PathVariable long programID, @RequestBody Registration input){

        if (programID != input.getProgram().getProgramID()){
            throw new IllegalArgumentException("Program ID passed in does not match program ID in registration object");
        }

        //TODO: Validate the registration object being passed in
        //TODO: Allow users to only update their registrations

        return registrationDAO.saveOrUpdateRegistration(input);
    }
}
