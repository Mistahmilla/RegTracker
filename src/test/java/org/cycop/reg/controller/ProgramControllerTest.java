package org.cycop.reg.controller;

import org.cycop.reg.dao.RegistrationDAO;
import org.cycop.reg.dataobjects.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

public class ProgramControllerTest {

    @Mock
    RegistrationDAO registrationDAO;

    @Mock
    UserController userController;

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
        Person per = new Person();
        per.setFirstName("John");
        per.setLastName("Doe");
        r.setPerson(per);
        r.setRegistrationDate(LocalDate.now());
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

        try {
            programController.putProgramRegistration(1, r);
        }catch (Exception e){
            assertTrue(false);
        }

        Mockito.verify(registrationDAO).saveOrUpdateRegistration(r);
    }

    @Test
    public void testGetRegistrationsVIEW(){
        Permission p = new Permission();
        p.setPermissionCode("REG_VIEW");
        User u = new User();
        u.setAccountID(1);
        u.addPermission(p);
        List uList = new ArrayList();
        uList.add(u);
        Mockito.doReturn(uList).when(userController).getCurrentUser();
        Mockito.doReturn(true).when(userController).userHasPermission("REG_VIEW");

        programController.getProgramRegistrations(2);
        Mockito.verify(registrationDAO).getRegistrationByAccount(1);

        List rList = new ArrayList();
        Registration r1 = new Registration();
        Program p1 = new Program();
        p1.setProgramID(1);
        r1.setProgram(p1);

        Registration r2 = new Registration();
        Program p2 = new Program();
        p2.setProgramID(2);
        r2.setProgram(p2);

        Registration r3 = new Registration();
        Program p3 = new Program();
        p3.setProgramID(2);
        r3.setProgram(p3);

        rList.add(r1);
        rList.add(r2);
        rList.add(r3);

        Mockito.doReturn(rList).when(registrationDAO).getRegistrationByAccount(1);
        List returnList = programController.getProgramRegistrations(2);
        assertEquals(2, returnList.size());
    }

    @Test
    public void testGetRegistrationsVIEW_ALL(){
        Permission p = new Permission();
        p.setPermissionCode("REG_VIEW_ANY");
        User u = new User();
        u.setAccountID(1);
        u.addPermission(p);
        List uList = new ArrayList();
        uList.add(u);
        Mockito.doReturn(uList).when(userController).getCurrentUser();
        Mockito.doReturn(true).when(userController).userHasPermission("REG_VIEW_ANY");

        programController.getProgramRegistrations(2);
        Mockito.verify(registrationDAO).getRegistrationByProgram(2);
    }

    @Test
    public void testGetRegistrationsNone(){
        User u = new User();
        u.setAccountID(1);
        List uList = new ArrayList();
        uList.add(u);
        Mockito.doReturn(uList).when(userController).getCurrentUser();
        Mockito.doReturn(false).when(userController).userHasPermission(Mockito.any());

        try {
            programController.getProgramRegistrations(2);
            fail("Expected an exceptino.");
        }catch(IllegalAccessError e){
            Assert.assertEquals("User does not have the 'REG_VIEW' or 'REG_VIEW_ANY' permission.", e.getMessage());
        }
    }

}
