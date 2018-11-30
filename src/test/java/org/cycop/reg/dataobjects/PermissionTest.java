package org.cycop.reg.dataobjects;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

public class PermissionTest {

    @Test
    public void testPermission(){
        Permission p = new Permission();
        p.setPermissionCode("C");
        p.setPermissionName("N");
        p.setPermissionDescription("D");

        assertEquals("C", p.getPermissionCode());
        assertEquals("N", p.getPermissionName());
        assertEquals("D", p.getPermissionDescription());
    }

    @Test
    public void testEquality(){
        Permission p1 =new Permission();
        p1.setPermissionCode("P1");
        Permission p2 = new Permission();
        p2.setPermissionCode("P2");
        Permission p3 =new Permission();
        p3.setPermissionCode("P1");

        assertEquals(p1, p3);
        assertEquals(p1.hashCode(), p3.hashCode());
        assertFalse(p1.equals(p2));

        User u = new User();
        assertFalse(p1.equals(u));


    }
}
