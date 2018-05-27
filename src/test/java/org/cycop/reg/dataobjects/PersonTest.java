package org.cycop.reg.dataobjects;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class PersonTest {

    @Test
    public void testPerson(){
        Person p = new Person();
        p.setPersonID((long)1);
        p.setFirstName("John");
        p.setLastName("Smith");
        p.setBirthDate(LocalDate.of(2001,9,11));
        p.setGender("F");
        p.setCreateTime(LocalDateTime.of(2001, 9, 11, 5, 11, 15, 10));
        p.setUpdateTime(LocalDateTime.of(2001, 9, 11, 5, 12, 15, 10));
        assertEquals("John", p.getFirstName());
        assertEquals("Smith", p.getLastName());
        assertEquals("Smith, John", p.toString());
        assertEquals(LocalDate.of(2001, 9, 11), p.getBirthDate());
        assertEquals(Person.Gender.F, p.getGender());
        assertEquals("Female", p.getGender().getGenderDescription());
        assertEquals("F", p.getGender().getGenderCode());
        assertEquals((long)1, (long)p.getPersonID());
        assertEquals(LocalDateTime.of(2001, 9, 11, 5, 11, 15, 10), p.getCreateTime());
        assertEquals(LocalDateTime.of(2001, 9, 11, 5, 12, 15, 10), p.getUpdateTime());
    }

}
