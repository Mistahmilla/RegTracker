package org.cycop.reg.dataobjects;

import org.junit.Test;

import java.time.LocalDate;

import static junit.framework.TestCase.assertEquals;

public class RegistrationTest {

    @Test
    public void testRegistration(){
        Registration r = new Registration();

        r.setGrade(new Grade("C", "DS", 1));
        r.setRank(new Rank("1", "One", "2",1));

        ShirtSize s = new ShirtSize();
        s.setShirtSizeCode("S");
        s.setShirtSizeDescription("Size");
        s.setSortOrder(1);
        r.setShirtSize(s);

        Program p = new Program();
        p.setProgramID(1);
        p.setProgramName("CYC");
        r.setProgram(p);

        Person per = new Person();
        per.setFirstName("John");
        per.setLastName("Doe");
        r.setPerson(per);

        Address a = new Address();
        a.setStreetAddress("123 Fake Street");
        a.setCity("Springfield");
        a.setState("MA");
        a.setZipCode("12345");
        r.setAddress(a);

        r.setRegistrationDate(LocalDate.of(2018,11,19));
        r.setRegistrationCancelDate(LocalDate.of(2018, 11, 20));

        assertEquals("C", r.getGrade().getGradeCode());
        assertEquals("S", r.getShirtSize().getShirtSizeCode());
        assertEquals("CYC", r.getProgram().getProgramName());
        assertEquals("John", r.getPerson().getFirstName());
        assertEquals("1", r.getRank().getRankCode());
        assertEquals(a, r.getAddress());
        assertEquals(LocalDate.of(2018,11,19), r.getRegistrationDate());
        assertEquals(LocalDate.of(2018, 11, 20), r.getRegistrationCancelDate());
    }
}
