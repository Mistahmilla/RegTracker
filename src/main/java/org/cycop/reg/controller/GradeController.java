package org.cycop.reg.controller;

import org.cycop.reg.dao.GradeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private GradeDAO gradeDAO;

    @GetMapping("/{gradeCode}")
    public List getGrade(@PathVariable String gradeCode) {
        return gradeDAO.get(gradeCode);
    }

    @GetMapping
    public List getAllGrades() {
        return gradeDAO.list();
    }

}
