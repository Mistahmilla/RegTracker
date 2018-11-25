package org.cycop.reg.controller;

import org.cycop.reg.dao.PersonAddressDAO;
import org.cycop.reg.dao.PersonDAO;
import org.cycop.reg.dataobjects.Address;
import org.cycop.reg.dataobjects.Person;
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

        List<Address> aList = new ArrayList();
        aList.add(b);

        Mockito.when(personDAO.saveOrUpdate(p)).thenReturn(Long.valueOf(1));
        Mockito.when(personAddressDAO.get(1)).thenReturn(aList);
        personController.addPerson(p);

        Mockito.verify(personAddressDAO).set(1, a);

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
}
