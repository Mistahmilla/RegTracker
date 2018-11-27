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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        Mockito.when(personDAO.get(Long.valueOf(1))).thenReturn(pList);
        personController.addPerson(p, "Y");

        Mockito.verify(personAddressDAO).set(1, a);
        Mockito.verify(userDAO).addPersonToAccount(1, 1);

        b.setStreetAddress("one");
        personController.addPerson(p);
        Mockito.verify(personAddressDAO, Mockito.atLeast(2)).set(1, a);

        b.setCity("one");
        personController.addPerson(p);
        Mockito.verify(personAddressDAO, Mockito.atLeast(3)).set(1, a);

        b.setState("one");
        personController.addPerson(p);
        Mockito.verify(personAddressDAO, Mockito.atLeast(4)).set(1, a);
    }

    @Test
    public void testDeletePerson(){
        personController.deletePerson(Long.valueOf(1));
        Mockito.verify(personDAO).delete(Long.valueOf(1));
    }

    @Test
    public void testGetPerson(){
        personController.getPerson(Long.valueOf(1));
        Mockito.verify(personDAO).get(Long.valueOf(1));
    }

    @Test
    public void testGetPersonRegistrations(){
        personController.getPersonRegistrations(Long.valueOf(1));
        Mockito.verify(registrationDAO).getRegistrationByPerson(Long.valueOf(1));
    }

    @Test
    public void testPersonSearch(){
        personController.personSearch(0, "");
        Mockito.verify(personDAO).get();

        personController.personSearch(1, "");
        Mockito.verify(personDAO).get(Long.valueOf(1));

        personController.personSearch(0, "test");
        Mockito.verify(personDAO).get("test");
    }
}
