package org.cycop.reg.dao.mapper;

import org.cycop.reg.dataobjects.Rank;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;

public class RankMapperTest {

    @Test
    public void testRankMapper(){
        ResultSet rs = Mockito.mock(ResultSet.class);
        RankMapper rm = new RankMapper();
        Rank r;
        try {
            Mockito.when(rs.getString("RANK_C")).thenReturn("1st");
            Mockito.when(rs.getString("RANK_DS")).thenReturn("1st Year");
            Mockito.when(rs.getString("NEXT_RANK_C")).thenReturn("2nd");
            Mockito.when(rs.getInt("SORT_ORD")).thenReturn(1);
            Mockito.when(rs.getTimestamp("CRE_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.getTimestamp("UPD_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
            r = rm.mapRow(rs, 1);

            assertEquals("1st", r.getRankCode());
            assertEquals("1st Year", r.getRankDescription());
            assertEquals("2nd", r.getNextRankCode());
            assertEquals(1, r.getSortOrder());
        }catch(Exception e){
            assert(false);
        }
    }
}
