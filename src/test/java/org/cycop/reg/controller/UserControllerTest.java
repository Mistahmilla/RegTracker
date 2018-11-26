package org.cycop.reg.controller;

import org.cycop.reg.dao.PersonDAO;
import org.cycop.reg.dao.UserDAO;
import org.cycop.reg.dataobjects.Person;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class UserControllerTest {

    @Mock
    PersonDAO personDAO;

    @Mock
    UserDAO userDAO;

    @InjectMocks
    UserController userController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPutUserPerson(){
        Person p = new Person();
        p.setPersonID(Long.valueOf(1));
        List<Person> l = new ArrayList();
        List<Person> l2 = new ArrayList();
        l2.add(p);
        Mockito.when(personDAO.get(Long.valueOf(1))).thenReturn(l).thenReturn(l2);
        Mockito.when(personDAO.saveOrUpdate(p)).thenReturn(Long.valueOf(1));
        userController.putUserPerson(1, p);

        Mockito.verify(personDAO).saveOrUpdate(p);
        Mockito.verify(userDAO).addPersonToAccount(Long.valueOf(1),Long.valueOf(1));
    }

    @Test
    public void testPutUserPerson2(){
        Person p = new Person();
        p.setPersonID(Long.valueOf(1));
        List<Person> l = new ArrayList();
        l.add(p);
        Mockito.when(personDAO.get(Long.valueOf(1))).thenReturn(l).thenReturn(l);
        Mockito.when(personDAO.saveOrUpdate(p)).thenReturn(Long.valueOf(1));
        userController.putUserPerson(1, p);

        Mockito.verify(personDAO, Mockito.times(0)).saveOrUpdate(p);
        Mockito.verify(userDAO).addPersonToAccount(Long.valueOf(1),Long.valueOf(1));
    }
    
}
