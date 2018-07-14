package org.cycop.reg.security;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    @Test
    public void testAddAuthority(){
        Account a = new Account();
        Authority auth1 = new Authority("ONE", "ONE", "ONE");
        Authority auth2 = new Authority("ONE", "ONE", "ONE");
        a.addAuthority(auth1);
        a.addAuthority(auth2);
        assertEquals(1, a.getAuthorities().size());
    }
}
