package org.cycop.reg.dao.mapper;

import org.cycop.reg.dataobjects.Role;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;

public class RoleMapperTest {

    @Test
    public void testRankMapper(){
        ResultSet rs = Mockito.mock(ResultSet.class);
        RoleMapper rm = new RoleMapper();
        Role r;
        try {
            Mockito.when(rs.getString("ROLE_C")).thenReturn("C");
            Mockito.when(rs.getString("ROLE_DS")).thenReturn("DS");
            Mockito.when(rs.getTimestamp("CRE_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.getTimestamp("UPD_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
            r = rm.mapRow(rs, 1);

            assertEquals("C", r.getRoleCode());
            assertEquals("DS", r.getRoleDescription());
        }catch(Exception e){
            assert(false);
        }
    }
}
