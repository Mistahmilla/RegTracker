package org.cycop.reg.dataobjects;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddressTest {

    @Test
    public void testAddress(){
        Address a = new Address();
        a.setAddressID(1);
        a.setStreetAddress("123 Fake Street");
        a.setCity("Springfield");
        a.setState("MA");
        a.setZipCode("12345");
        assertEquals("123 Fake Street", a.getStreetAddress());
        assertEquals("Springfield", a.getCity());
        assertEquals("MA", a.getState());
        assertEquals("12345", a.getZipCode());
        assertEquals(1, a.getAddressID());
    }
}
