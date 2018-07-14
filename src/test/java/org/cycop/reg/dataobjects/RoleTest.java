package org.cycop.reg.dataobjects;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RoleTest {

    @Test
    public void testRole(){
        Role r = new Role();
        r.setRoleCode("CODE");
        r.setRoleDescription("DESC");
        assertEquals("CODE", r.getRoleCode());
        assertEquals("DESC", r.getRoleDescription());
        Role r2 = new Role("CODE", "DESC");
        assertEquals(r, r2);
        assertEquals(r.hashCode(), r2.hashCode());
        r2.setRoleCode("DIFF");
        assertNotEquals(r, r2);
        assertNotEquals(r, "test");
        assertNotEquals(r.hashCode(), r2.hashCode());
    }
}
