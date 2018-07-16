package org.cycop.reg.dataobjects;

import org.junit.Test;

import java.time.LocalDate;

import static junit.framework.TestCase.assertEquals;

public class ProgramTest {

    @Test
    public void testFields(){
        Program p = new Program();
        p.setProgramID(1);
        p.setProgramName("name");
        assertEquals("name", p.getProgramName());

        LocalDate s = LocalDate.of(2018, 1, 1);
        p.setStartDate(s);
        LocalDate e  = LocalDate.of(2018, 1, 2);
        p.setEndDate(e);
        assertEquals(LocalDate.of(2018, 1, 1), p.getStartDate());
        assertEquals(LocalDate.of(2018, 1, 2), p.getEndDate());
        assertEquals(1, p.getProgramID());
    }
}
