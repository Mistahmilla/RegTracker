package org.cycop.reg.controller;

import org.cycop.reg.dao.PersonDAO;
import org.cycop.reg.dao.UserDAO;
import org.cycop.reg.dataobjects.Permission;
import org.cycop.reg.dataobjects.Person;
import org.cycop.reg.dataobjects.Role;
import org.cycop.reg.dataobjects.User;
import org.cycop.reg.security.AuthenticationFacade;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;

public class UserControllerTest {

    @Mock
    PersonDAO personDAO;

    @Mock
    UserDAO userDAO;

    @Mock
    AuthenticationFacade authenticationFacade;

    @Spy
    @InjectMocks
    UserController userController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPutUserPerson(){
        Person p = new Person();
        p.setPersonID(1);
        List<Person> l = new ArrayList();
        List<Person> l2 = new ArrayList();
        l2.add(p);

        User u = new User();
        u.setAccountID(1);
        List uList = new ArrayList();
        uList.add(u);
        Mockito.doReturn(true).when(userController).userHasPermission("PER_ADD");
        Mockito.doReturn(uList).when(userController).getCurrentUser();
        Mockito.when(personDAO.get(1, "", 0)).thenReturn(l).thenReturn(l2);
        Mockito.when(personDAO.saveOrUpdate(p)).thenReturn(Long.valueOf(1));
        userController.putUserPerson(1, p);

        Mockito.verify(personDAO).saveOrUpdate(p);
        Mockito.verify(userDAO).addPersonToAccount(1,1);
    }

    @Test
    public void testPutUserPerson2(){
        Person p = new Person();
        p.setPersonID(1);
        List<Person> l = new ArrayList();
        l.add(p);
        User u = new User();
        u.setAccountID(1);
        List uList = new ArrayList();
        uList.add(u);
        Mockito.doReturn(true).when(userController).userHasPermission("PER_ADD");
        Mockito.doReturn(false).when(userController).userHasPermission("PER_ADD_TO_ANY");
        Mockito.doReturn(uList).when(userController).getCurrentUser();
        Mockito.when(personDAO.get(1, "", 0)).thenReturn(l).thenReturn(l);
        Mockito.when(personDAO.saveOrUpdate(p)).thenReturn(Long.valueOf(1));
        userController.putUserPerson(1, p);

        Mockito.verify(personDAO, times(0)).saveOrUpdate(p);
        Mockito.verify(userDAO).addPersonToAccount(Long.valueOf(1),Long.valueOf(1));

        u.setAccountID(2);
        try{
            userController.putUserPerson(1, p);
            fail("Expected an exception.");
        }catch(IllegalAccessError e){
            assertEquals("User does not have the 'PER_ADD_TO_ANY' permission.", e.getMessage());
        }

        Mockito.doReturn(false).when(userController).userHasPermission("PER_ADD");
        try{
            userController.putUserPerson(2, p);
            fail("Expected an exception.");
        }catch(IllegalAccessError e){
            assertEquals("User does not have the 'PER_ADD' or 'PER_ADD_TO_ANY' permission.", e.getMessage());
        }
    }

    @Test
    public void testUpdateUserBadUser(){
        List<User> l = new ArrayList();
        Mockito.when(userController.getUser(1)).thenReturn(l);
        User u = new User();
        u.setAccountID(1);
        try {
            userController.updateUser(u);
            fail("Expected an exception");
        }catch(NullPointerException e) {
            assertEquals("User does not exist.", e.getMessage());
        }
    }

    @Test
    public void testUpdateUserGoodUser() {
        List<User> l = new ArrayList();
        User u = new User();
        u.setAccountID(1);
        u.setEmailAddress("john@doe.com");
        u.setPassword("test");
        Permission p = new Permission();
        p.setPermissionCode("USER_UPDATE");
        u.addPermission(p);
        l.add(u);

        Mockito.when(userController.getUser(1)).thenReturn(l);
        Mockito.when(userDAO.getUserByEmailAddress(any())).thenReturn(l);
        Mockito.doReturn(l).when(userController).getCurrentUser();

        userController.updateUser(u);
        Mockito.verify(userDAO).updateExisting(u, u);
    }

    @Test
    public void testUpdateUserGoodUserBadAccess() {
        List<User> l = new ArrayList();
        User u = new User();
        u.setAccountID(1);
        u.setEmailAddress("john@doe.com");
        u.setPassword("test");
        Permission p = new Permission();
        p.setPermissionCode("nUSER_UPDATE");
        u.addPermission(p);
        l.add(u);

        Mockito.when(userController.getUser(1)).thenReturn(l);
        Mockito.when(userDAO.getUserByEmailAddress(any())).thenReturn(l);
        Mockito.doReturn(l).when(userController).getCurrentUser();

        try {
            userController.updateUser(u);
            fail("Expected an exception");
        }catch(IllegalAccessError e) {
            assertEquals("User does not have the 'USER_UPDATE' permission.", e.getMessage());
        }
    }

    @Test
    public void testUpdateUserGoodUserBadAccess2() {
        List<User> l = new ArrayList();
        User u = new User();
        u.setAccountID(1);
        u.setEmailAddress("john@doe.com");
        u.setPassword("test");
        Permission p = new Permission();
        p.setPermissionCode("nUSER_UPDATE");
        u.addPermission(p);
        l.add(u);
        u.addRole(new Role("USER", "USER"));

        List<User> l2 = new ArrayList();
        User u2 = new User();
        u2.setAccountID(2);
        u2.setEmailAddress("john2@doe.com");
        u2.setPassword("test");
        Permission p2 = new Permission();
        p2.setPermissionCode("nUSER_UPDATE");
        u2.addPermission(p2);
        l2.add(u2);
        u2.addRole(new Role("ADMIN", "ADMIN"));

        Mockito.doReturn(l).when(userController).getUser(1);
        Mockito.doReturn(l2).when(userController).getUser(2);
        Mockito.when(userDAO.getUserByEmailAddress("john@doe.com")).thenReturn(l);
        Mockito.when(userDAO.getUserByEmailAddress("john2@doe.com")).thenReturn(l2);
        Mockito.doReturn(l2).when(userController).getCurrentUser();

        try {
            userController.updateUser(u);
            fail("Expected an exception");
        }catch(IllegalAccessError e) {
            assertEquals("User does not have the 'USER_UPDATE_ANY' permission.", e.getMessage());
        }

        u.setAccountID(2);
        Permission p3 = new Permission();
        p3.setPermissionCode("USER_UPDATE");
        u2.addPermission(p3);
        p2.setPermissionCode("USER_UPDATE_ANY");
        u.addRole(new Role("new", "new"));
        try {
            userController.updateUser(u);
            fail("Expected an exception");
        }catch(IllegalAccessError e) {
            assertEquals("User does not have the 'USER_UPDATE_ROLE' permission.", e.getMessage());
        }
    }

    @Test
    public void testDeleteUserPerson(){
        List uList = new ArrayList();
        User u = new User();
        u.setAccountID(1);
        uList.add(u);

        Mockito.doReturn(true).when(userController).userHasPermission("PER_DEL");
        Mockito.doReturn(false).when(userController).userHasPermission("PER_DEL_ANY");
        Mockito.doReturn(uList).when(userController).getCurrentUser();

        userController.deleteUserPerson(1, 2);
        Mockito.verify(userDAO).removePersonFromAccount(1, 2);

        Mockito.doReturn(true).when(userController).userHasPermission("PER_DEL_ANY");
        userController.deleteUserPerson(2, 2);
        Mockito.verify(userDAO).removePersonFromAccount(2, 2);

        Mockito.doReturn(false).when(userController).userHasPermission("PER_DEL_ANY");
        try{
            userController.deleteUserPerson(2, 2);
            fail("Expected an exception.");
        }catch(IllegalAccessError e){
            assertEquals("User does not have the 'PER_DEL_ANY' permission.", e.getMessage());
        }

        Mockito.doReturn(false).when(userController).userHasPermission("PER_DEL");
        try{
            userController.deleteUserPerson(1, 2);
            fail("Expected an exception.");
        }catch(IllegalAccessError e){
            assertEquals("User does not have the 'PER_DEL' or 'PER_DEL_ANY' permission.", e.getMessage());
        }
    }
}
