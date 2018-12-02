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

    @Autowired
    UserController userController;

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
        List<Registration> rList;
        // check what permission the user has and return all or only their registrations depending
        if(userController.userHasPermission("REG_VIEW_ANY")) {
            return registrationDAO.getRegistrationByProgram(programID);
        }else if(userController.userHasPermission("REG_VIEW")){
            rList = registrationDAO.getRegistrationByAccount(userController.getCurrentUser().get(0).getAccountID());
            for (int i = 0; i < rList.size(); i++){
                if(rList.get(i).getProgram().getProgramID() != programID){
                    rList.remove(i);
                    i--;
                }
            }
            return rList;
        }else{
            throw new IllegalAccessError("User does not have the 'REG_VIEW' or 'REG_VIEW_ANY' permission.");
        }
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
