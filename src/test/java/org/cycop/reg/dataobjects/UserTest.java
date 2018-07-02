package org.cycop.reg.dataobjects;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void testAddRole(){
        User u = new User();
        Role r1 = new Role("ONE", "ONE");
        Role r2 = new Role("ONE", "ONE");

        u.addRole(r1);
        u.addRole(r2);
        assertEquals(1, u.getRoles().size());

        u.removeRole(r1);
        assertEquals(0, u.getRoles().size());

    }
}
