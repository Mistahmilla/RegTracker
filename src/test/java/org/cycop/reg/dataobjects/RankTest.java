package org.cycop.reg.dataobjects;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RankTest {

    @Test
    public void testRank(){
        Rank r = new Rank("SIT", "Staff in Training", "CIT",1);
        assertEquals("SIT", r.getRankCode());
        assertEquals("Staff in Training", r.getRankDescription());
        assertEquals("CIT", r.getNextRankCode());
        assertEquals(1, r.getSortOrder());
    }

}
