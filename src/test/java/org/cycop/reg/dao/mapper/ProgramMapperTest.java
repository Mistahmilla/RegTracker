package org.cycop.reg.dao.mapper;

import org.cycop.reg.dataobjects.Program;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;

public class ProgramMapperTest {

    @Test
    public void testRankMapper(){
        ResultSet rs = Mockito.mock(ResultSet.class);
        ProgramMapper pm = new ProgramMapper();
        Program p;
        try {
            Mockito.when(rs.getLong("PROGRAM_SID")).thenReturn(Long.valueOf(1));
            Mockito.when(rs.getString("PROGRAM_NM")).thenReturn("name");
            Mockito.when(rs.getDate("START_D")).thenReturn(Date.valueOf(LocalDate.now()));
            Mockito.when(rs.getDate("END_D")).thenReturn(Date.valueOf(LocalDate.now()));
            Mockito.when(rs.getTimestamp("CRE_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.getTimestamp("UPD_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
            p = pm.mapRow(rs, 1);

            assertEquals(1, p.getProgramID());
            assertEquals("name", p.getProgramName());
        }catch(Exception e){
            assert(false);
        }
    }
}
