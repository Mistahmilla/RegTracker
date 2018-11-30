package org.cycop.reg.dao.mapper;

import org.cycop.reg.dataobjects.Permission;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;

public class PermissionMapperTest {

    @Test
    public void mapPermissionTest(){
        ResultSet rs = Mockito.mock(ResultSet.class);
        PermissionMapper pm = new PermissionMapper();
        Permission p;

        try{
            Mockito.when(rs.getString("PERM_C")).thenReturn("C");
            Mockito.when(rs.getString("PERM_NM")).thenReturn("N");
            Mockito.when(rs.getString("PERM_DS")).thenReturn("D");
            Mockito.when(rs.getTimestamp("CRE_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.getTimestamp("UPD_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
            p = pm.mapRow(rs,1);

            assertEquals("C", p.getPermissionCode());
            assertEquals("N", p.getPermissionName());
            assertEquals("D", p.getPermissionDescription());
        }catch(Exception e){
            assert(false);
        }
    }
}
