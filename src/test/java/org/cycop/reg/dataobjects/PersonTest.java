package org.cycop.reg.dataobjects;

import org.junit.*;

import static org.junit.Assert.*;

public class PersonTest {

    @Test
    public void testPerson(){
        Person p = new Person("John","Smith");
        assertEquals("John", p.getFirstName());
        assertEquals("Smith", p.getLastName());
        assertEquals("Smith, John", p.toString());
    }

}
