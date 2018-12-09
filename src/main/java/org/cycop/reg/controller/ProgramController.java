package org.cycop.reg.controller;

import org.cycop.reg.dao.PersonDAO;
import org.cycop.reg.dao.ProgramDAO;
import org.cycop.reg.dao.RegistrationDAO;
import org.cycop.reg.dataobjects.Registration;
import org.cycop.reg.dataobjects.validators.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
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

    @Autowired
    PersonDAO personDAO;

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

        List pList;
        if (programID != input.getProgram().getProgramID()){
            throw new IllegalArgumentException("Program ID passed in does not match program ID in registration object");
        }

        pList = personDAO.get(input.getPerson().getPersonID(), "", userController.getCurrentUser().get(0).getAccountID());
        if(pList.isEmpty() && !userController.userHasPermission("REG_UPDATE_ANY")){
            throw new IllegalAccessError("User does not have the 'REG_UPDATE_ANY' permission.");
        }
        if(pList.size() == 1 && (!userController.userHasPermission("REG_UPDATE") && !userController.userHasPermission("REG_UPDATE_ANY"))){
            throw new IllegalAccessError("User does not have the 'REG_UPDATE' permission.");
        }
        if(pList.isEmpty()){
            throw new IllegalArgumentException("Person passed in is not valid.");
        }

        DataBinder db = new DataBinder(input);
        db.setValidator(new RegistrationValidator());
        db.bind(null);
        db.validate();
        BindingResult result = db.getBindingResult();
        if(!result.hasErrors()) {
            return registrationDAO.saveOrUpdateRegistration(input);
        }else{
            return result.getAllErrors();
        }
    }
}
