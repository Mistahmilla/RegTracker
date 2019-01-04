package org.cycop.reg.controller;

import org.cycop.reg.dataobjects.repositories.GradeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class GradeControllerTest {

    @Mock
    GradeRepository gradeRepository;

    @InjectMocks
    GradeController gradeController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGradeController(){
        gradeController.getGrade("G");
        Mockito.verify(gradeRepository).getGrade("G");
        gradeController.getAllGrades();
        Mockito.verify(gradeRepository).getAll();
    }
}
