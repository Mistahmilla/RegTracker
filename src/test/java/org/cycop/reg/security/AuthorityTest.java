package org.cycop.reg.security;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AuthorityTest {

    @Test
    public void testAuthority(){
        Authority a1 = new Authority("ONE", "ONE", "ONE");
        Authority a2 = new Authority("ONE", "ONE", "ONE");
        Authority a3 = new Authority("TWO", "TWO", "TWO");

        assertEquals(a1, a2);
        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1, a3);
        assertNotEquals(a1.hashCode(), a3.hashCode());
        assertEquals(a1.getAuthorityDescription(), "ONE");
        String s = "test";
        assertNotEquals(a1, s);
    }
}
