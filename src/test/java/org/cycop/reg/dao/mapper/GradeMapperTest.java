package org.cycop.reg.dao.mapper;

import org.cycop.reg.dataobjects.Grade;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;

public class GradeMapperTest {

    @Test
    public void testRankMapper(){
        ResultSet rs = Mockito.mock(ResultSet.class);
        GradeMapper gm = new GradeMapper();
        Grade g;
        try {
            Mockito.when(rs.getString("GRADE_C")).thenReturn("C");
            Mockito.when(rs.getString("GRADE_DS")).thenReturn("DS");
            Mockito.when(rs.getInt("SORT_ORD")).thenReturn(1);
            Mockito.when(rs.getTimestamp("CRE_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.getTimestamp("UPD_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
            g = gm.mapRow(rs, 1);

            assertEquals("C", g.getGradeCode());
            assertEquals("DS", g.getGradeDescription());
            assertEquals(1, g.getSortOrder());
        }catch(Exception e){
            assert(false);
        }
    }
}
