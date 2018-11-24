package org.cycop.reg.controller;

import org.cycop.reg.dao.RegistrationDAO;
import org.cycop.reg.dataobjects.Program;
import org.cycop.reg.dataobjects.Registration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ProgramControllerTest {

    @Mock
    RegistrationDAO registrationDAO;

    @InjectMocks
    ProgramController programController;

    boolean bPassed;
    Registration r;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        List<Registration> rList = new ArrayList<Registration>();
        r = new Registration();
        bPassed = false;
        Program p = new Program();
        p.setProgramID(1);
        r.setProgram(p);

        rList.add(r);

        Mockito.when(registrationDAO.saveOrUpdateRegistration(r)).thenReturn(rList);
    }

    @Test
    public void testPutProgramRegistration(){

        try {
            programController.putProgramRegistration(2, r);
        }catch (IllegalArgumentException e){
            bPassed = true;
            assertEquals("Program ID passed in does not match program ID in registration object",e.getMessage());
        }

        if(!bPassed){
            assertTrue(false);
        }


    }

    @Test
    public void testPutProgramRegistration2(){
        bPassed = true;
        try {
            programController.putProgramRegistration(1, r);
        }catch (Exception e){
            bPassed = false;
            assertTrue(false);
        }

        if(bPassed){
            assertTrue(true);
        }
    }

}
