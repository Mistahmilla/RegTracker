package org.cycop.reg.dataobjects;

import org.junit.*;
import static org.junit.Assert.*;

public class CredentialTest {

    @Test
    public void testCredential(){
        Credential c = new Credential("user", "pass");
        assertEquals("user", c.getUsername());
        assertEquals("pass", c.getPassword());
    }

    @Test
    public void testCredential2(){
        Credential c = new Credential();
        c.setUsername("user");
        c.setPassword("pass");
        assertEquals("user", c.getUsername());
        assertEquals("pass", c.getPassword());
    }
}
