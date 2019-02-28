package org.cycop.reg.dataobjects;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChurchTest {

    @Test
    public void testChurch(){
        Church c = new Church();
        c.setChurchID(1);
        assertEquals(1, c.getChurchID());
        c.setChurchName("church");
        assertEquals("church", c.getChurchName());
        c.setEmailAddress("test@test.com");
        assertEquals("test@test.com", c.getEmailAddress());
        c.setPhoneNumber("123-123-1234");
        assertEquals("123-123-1234", c.getPhoneNumber());

        Address a = new Address();
        a.setStreetAddress("123 Fake Street");
        a.setCity("Springfield");
        a.setState("MA");
        a.setZipCode("12345");
        c.setAddress(a);
        assertEquals("123 Fake Street", c.getAddress().getStreetAddress());

        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Doe");
        c.setPastor(p);
        assertEquals("John", c.getPastor().getFirstName());
    }

}
