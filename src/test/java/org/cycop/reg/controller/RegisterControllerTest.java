package org.cycop.reg.controller;

import org.cycop.reg.dao.UserDAO;
import org.cycop.reg.dataobjects.Role;
import org.cycop.reg.dataobjects.User;
import org.cycop.reg.dataobjects.repositories.RoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class RegisterControllerTest {

    @Mock
    RoleRepository roleRepository;

    @Mock
    UserDAO userDAO;

    @InjectMocks
    RegisterController registerController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterController(){
        User u = new User();
        u.setAccountID(1);
        registerController.registerAccount(u);
        Mockito.verify(roleRepository).getRoleByCode("USER");
        Mockito.verify(userDAO).createNew(u);
    }

    @Test
    public void testRegisterController2(){
        User u = new User();
        u.setAccountID(1);
        Role r = new Role();
        r.setRoleCode("USER");
        r.setRoleDescription("USER");
        List rList = new ArrayList();
        rList.add(r);
        Mockito.doReturn(rList).when(roleRepository).getRoleByCode("USER");
        registerController.registerAccount(u);
        Mockito.verify(roleRepository, Mockito.atLeastOnce()).getRoleByCode("USER");
        Mockito.verify(userDAO).createNew(u);
    }
}
