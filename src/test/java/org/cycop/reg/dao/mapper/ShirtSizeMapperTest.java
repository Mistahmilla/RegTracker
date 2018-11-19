package org.cycop.reg.dao.mapper;

import org.cycop.reg.dataobjects.ShirtSize;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;

public class ShirtSizeMapperTest {
    @Test
    public void testRankMapper(){
        ResultSet rs = Mockito.mock(ResultSet.class);
        ShirtSizeMapper ssm = new ShirtSizeMapper();
        ShirtSize s;
        try {
            Mockito.when(rs.getString("SHIRT_SIZE_C")).thenReturn("C");
            Mockito.when(rs.getString("SHIRT_SIZE_DS")).thenReturn("DS");
            Mockito.when(rs.getInt("SORT_ORD")).thenReturn(1);
            Mockito.when(rs.getTimestamp("CRE_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.getTimestamp("UPD_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
            s = ssm.mapRow(rs, 1);

            assertEquals("C", s.getShirtSizeCode());
            assertEquals("DS", s.getShirtSizeDescription());
            assertEquals(1, s.getSortOrder());
        }catch(Exception e){
            assert(false);
        }
    }
}
