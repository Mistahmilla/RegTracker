package org.cycop.reg.controller;

import org.cycop.reg.dao.PersonAddressDAO;
import org.cycop.reg.dao.PersonDAO;
import org.cycop.reg.dao.RegistrationDAO;
import org.cycop.reg.dao.UserDAO;
import org.cycop.reg.dataobjects.Address;
import org.cycop.reg.dataobjects.Person;
import org.cycop.reg.dataobjects.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class PersonControllerTest {

    @Mock
    PersonDAO personDAO;

    @Mock
    PersonAddressDAO personAddressDAO;

    @Mock
    RegistrationDAO registrationDAO;

    @Mock
    UserDAO userDAO;

    @Mock
    UserController userController;

    @Spy
    @InjectMocks
    PersonController personController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addPersonTest(){

        Address a = new Address();
        a.setAddressID(1);
        a.setStreetAddress("one");
        a.setCity("one");
        a.setState("one");
        a.setZipCode("12345");

        Address b = new Address();
        b.setAddressID(2);
        b.setStreetAddress("two");
        b.setCity("two");
        b.setState("two");
        b.setZipCode("54321");

        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Doe");
        p.setBirthDate(LocalDate.now());
        p.setCurrentAddress(a);
        p.setPersonID(Long.valueOf(1));

        List<Address> aList = new ArrayList();
        aList.add(b);

        List<User> uList = new ArrayList();
        User u = new User();
        u.setAccountID(1);
        uList.add(u);

        List<Person> pList = new ArrayList();
        pList.add(p);

        Mockito.when(personDAO.saveOrUpdate(p)).thenReturn(Long.valueOf(1));
        Mockito.when(personAddressDAO.get(1)).thenReturn(aList);
        Mockito.when(userController.getCurrentUser()).thenReturn(uList);
        Mockito.when(personDAO.get(1, "",0)).thenReturn(pList);

        try {
            personController.addPerson(p, "Y");
            fail("Expected an exception.");
        }catch(IllegalAccessError e){
            assertEquals("User does not have the 'PER_ADD' or 'PER_ADD_TO_ANY' permission.", e.getMessage());
        }
        Mockito.doReturn(true).when(userController).userHasPermission("PER_ADD");
        personController.addPerson(p, "Y");
        Mockito.verify(personAddressDAO).set(1, a);
        Mockito.verify(userDAO).addPersonToAccount(1, 1);

        b.setStreetAddress("one");
        personController.addPerson(p, "N");
        Mockito.verify(personAddressDAO, Mockito.atLeast(2)).set(1, a);

        b.setCity("one");
        personController.addPerson(p, "N");
        Mockito.verify(personAddressDAO, Mockito.atLeast(3)).set(1, a);

        b.setState("one");
        personController.addPerson(p, "N");
        Mockito.verify(personAddressDAO, Mockito.atLeast(4)).set(1, a);
    }

    @Test
    public void testDeletePerson(){
        List uList = new ArrayList();
        User u = new User();
        u.setAccountID(5);
        uList.add(u);
        List pList = new ArrayList();
        Person p = new Person();
        p.setPersonID(1);
        pList.add(p);

        Mockito.doReturn(uList).when(userController).getCurrentUser();
        Mockito.doReturn(pList).when(personController).personSearch(1, "", 5);

        try {
            personController.deletePerson(1);
            fail("Expected an exception.");
        }catch (IllegalAccessError e){
            assertEquals("User does not have the 'PER_DEL' or 'PER_DEL_ANY' permission.", e.getMessage());
        }


        Mockito.doReturn(true).when(userController).userHasPermission("PER_DEL");
        personController.deletePerson(1);
        Mockito.verify(personDAO).delete(1);

        Mockito.doReturn(true).when(userController).userHasPermission("PER_DEL_ANY");
        personController.deletePerson(1);
        Mockito.verify(personDAO, Mockito.times(2)).delete(Long.valueOf(1));

        List pList2 = new ArrayList();
        Mockito.doReturn(pList2).when(personController).personSearch(2, "", 5);
        Mockito.doReturn(false).when(userController).userHasPermission("PER_DEL_ANY");

        try {
            personController.deletePerson(2);
            fail("Expected an exception.");
        }catch (IllegalAccessError e){
            assertEquals("User does not have the 'PER_DEL_ANY' permission or the user does not exist.", e.getMessage());
        }
    }

    @Test
    public void testGetPerson(){
        List uList = new ArrayList();
        User u = new User();
        u.setAccountID(5);
        uList.add(u);
        Mockito.doReturn(true).when(userController).userHasPermission("PER_VIEW");
        Mockito.doReturn(uList).when(userController).getCurrentUser();
        personController.getPerson(1);
        Mockito.verify(personController).personSearch(1, "", 0);
    }

    @Test
    public void testGetPersonRegistrations(){
        List uList = new ArrayList();
        User u = new User();
        u.setAccountID(2);
        uList.add(u);

        List pList = new ArrayList();
        Person p = new Person();
        p.setPersonID(1);
        pList.add(p);

        Mockito.doReturn(false).when(userController).userHasPermission("REG_VIEW");
        Mockito.doReturn(false).when(userController).userHasPermission("REG_VIEW_ANY");
        Mockito.doReturn(uList).when(userController).getCurrentUser();
        Mockito.doReturn(pList).when(personController).personSearch(1, "", 2);

        try {
            personController.getPersonRegistrations(1);
            fail("Expected an exception.");
        }catch(IllegalAccessError e){
            assertEquals("User does not have the 'REG_VIEW' or 'REG_VIEW_ANY' permission.", e.getMessage());
        }
        Mockito.doReturn(true).when(userController).userHasPermission("REG_VIEW");
        personController.getPersonRegistrations(1);
        Mockito.verify(registrationDAO).getRegistrationByPerson(1);

        pList.remove(p);
        try {
            personController.getPersonRegistrations(1);
            fail("Expected an exception.");
        }catch(IllegalAccessError e){
            assertEquals("User does not have the 'REG_VIEW_ANY' permission.", e.getMessage());
        }

        Mockito.doReturn(true).when(userController).userHasPermission("REG_VIEW_ANY");
        personController.getPersonRegistrations(1);
        Mockito.verify(registrationDAO, Mockito.times(2)).getRegistrationByPerson(1);
    }

    @Test
    public void testPersonSearch(){
        List uList = new ArrayList();
        User u = new User();
        u.setAccountID(5);
        uList.add(u);

        try{
            personController.personSearch(0, "", 0);
            fail("Expected an exception.");
        }catch(IllegalAccessError e){
            assertEquals("User does not have the 'PER_VIEW' or 'PER_VIEW_ANY' permission.", e.getMessage());
        }
        Mockito.doReturn(true).when(userController).userHasPermission("PER_VIEW");
        Mockito.doReturn(uList).when(userController).getCurrentUser();
        personController.personSearch(0, "", 0);
        Mockito.verify(personDAO).get(Long.valueOf(0), "", 5);

        personController.personSearch(1, "", 0);
        Mockito.verify(personDAO).get(Long.valueOf(1), "", 5);

        personController.personSearch(0, "test", 0);
        Mockito.verify(personDAO).get(Long.valueOf(0),"test",5);

        try{
            personController.personSearch(0, "", 6);
            fail("Expected an exception.");
        }catch(IllegalAccessError e){
            assertEquals("User does not have the 'PER_VIEW_ANY' permission.", e.getMessage());
        }

        Mockito.doReturn(true).when(userController).userHasPermission("PER_VIEW_ANY");
        personController.personSearch(0, "", 0);
        Mockito.verify(personDAO).get(Long.valueOf(0), "", 0);

        personController.personSearch(1, "", 0);
        Mockito.verify(personDAO).get(Long.valueOf(1), "", 0);

        personController.personSearch(0, "test", 0);
        Mockito.verify(personDAO).get(Long.valueOf(0),"test",0);
    }
}
