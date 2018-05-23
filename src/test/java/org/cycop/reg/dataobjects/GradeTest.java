package org.cycop.reg.dataobjects;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GradeTest {

    @Test
    public void testRank(){
        Grade g = new Grade("9th", "Ninth", 9);
        assertEquals("9th", g.getGradeCode());
        assertEquals("Ninth", g.getGradeDescription());
        assertEquals(9, g.getSortOrder());
    }

}
