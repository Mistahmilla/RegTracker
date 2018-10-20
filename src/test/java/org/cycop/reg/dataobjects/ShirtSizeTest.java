package org.cycop.reg.dataobjects;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ShirtSizeTest {

    @Test
    public void testShirtSize(){
        ShirtSize s = new ShirtSize();
        s.setShirtSizeCode("code");
        s.setShirtSizeDescription("desc");
        s.setSortOrder(1);
        assertEquals("code", s.getShirtSizeCode());
        assertEquals("desc", s.getShirtSizeDescription());
        assertEquals(1, s.getSortOrder());
    }
}
