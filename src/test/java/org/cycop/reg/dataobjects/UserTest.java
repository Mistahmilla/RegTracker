package org.cycop.reg.dataobjects;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testAddPermission(){
        User u = new User();
        Permission p1 = new Permission();
        p1.setPermissionCode("C1");
        Permission p2 = new Permission();
        p2.setPermissionCode("C1");

        u.addPermission(p1);
        u.addPermission(p2);
        assertEquals(1, u.getPermissions().size());

        u.removePermission(p1);
        assertEquals(0, u.getPermissions().size());
    }

    @Test
    public void testFields(){
        User u = new User();
        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Smith");
        u.setPerson(p);
        u.setEmailAddress("test@test.com");
        u.setSalt("salt");
        u.setPassword("password");
        u.setPasswordExpired(true);
        u.setAccountLocked(true);
        u.setAccountVerified(true);
        u.setAccountID(1);
        assertEquals("test@test.com", u.getEmailAddress());
        assertEquals("salt", u.getSalt());
        assertEquals("password", u.getPassword());
        assertTrue(u.getAccountLocked());
        assertTrue(u.getAccountVerified());
        assertTrue(u.getPasswordExpired());
        assertEquals(1, u.getAccountID());
        assertEquals(p, u.getPerson());
        assertEquals("John", u.getPerson().getFirstName());
    }
}
