package org.cycop.reg.dataobjects;

import org.junit.*;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class PersonTest {

    @Test
    public void testPerson(){
        Person p = new Person();
        p.setPersonID((long)1);
        p.setFirstName("John");
        p.setLastName("Smith");
        p.setBirthDate(LocalDate.of(2001,9,11));
        p.setGender(Person.Gender.F);
        assertEquals("John", p.getFirstName());
        assertEquals("Smith", p.getLastName());
        assertEquals("Smith, John", p.toString());
        assertEquals(LocalDate.of(2001, 9, 11), p.getBirthDate());
        assertEquals(Person.Gender.F, p.getGender());
        assertEquals("Female", p.getGender().getGenderDescription());
        assertEquals("F", p.getGender().getGenderCode());
        assertEquals((long)1, (long)p.getPersonID());
    }

}
