package org.cycop.reg.controller;

import org.cycop.reg.dao.ProgramDAO;
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

    @GetMapping("/{programID}")
    public List getProgram(@PathVariable long programID) {
        return programDAO.get(programID);
    }
}
