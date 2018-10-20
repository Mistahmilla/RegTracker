package org.cycop.reg.security;

import org.junit.Test;

import java.util.ArrayList;

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

        ArrayList<Authority> authList = new ArrayList<Authority>();
        authList.add(auth1);
        authList.add(new Authority("TWO", "TWO", "TWO"));
        a.setAuthorities(authList);
        assertEquals(2, a.getAuthorities().size());
    }

    @Test
    public void TestAccount(){
        Account a = new Account();
        a.setAccountID(1);
        assertEquals(1, a.getAccountID());

        a.setPassword("notapw");
        assertEquals("notapw", a.getPassword());

        a.setUsername("test@test.com");
        assertEquals("test@test.com", a.getUsername());

        a.setPasswordSalt("sodium");
        assertEquals("sodium", a.getPasswordSalt());

        a.setPasswordExpired(false);
        assertEquals(true, a.isCredentialsNonExpired());

        a.setAccountLocked(false);
        assertEquals(true, a.isAccountNonLocked());
        assertEquals(true, a.isAccountNonExpired());

        a.setAccountVerified(true);

        assertEquals(true, a.isEnabled());

        a.setAccountLocked(true);
        assertEquals(false, a.isEnabled());
    }
}
