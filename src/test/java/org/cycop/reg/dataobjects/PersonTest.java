package org.cycop.reg.dataobjects;

import org.junit.*;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class PersonTest {

    @Test
    public void testPerson(){
        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Smith");
        p.setBirthDate(LocalDate.of(2001,9,11));
        assertEquals("John", p.getFirstName());
        assertEquals("Smith", p.getLastName());
        assertEquals("Smith, John", p.toString());
        assertEquals(LocalDate.of(2001, 9, 11), p.getBirthDate());
    }

}
