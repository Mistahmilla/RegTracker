package org.cycop.reg.dataobjects.repositories;

import org.cycop.reg.dao.GradeDAO;
import org.cycop.reg.dataobjects.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GradeRepository {

    @Autowired
    GradeDAO gradeDAO;

    @Cacheable("grades")
    public List<Grade> getGrade(String gradeCode){
        return gradeDAO.get(gradeCode);
    }

    @Cacheable("grades")
    public List<Grade> getAll(){
        return gradeDAO.list();
    }
}
