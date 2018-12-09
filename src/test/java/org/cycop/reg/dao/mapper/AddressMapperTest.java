package org.cycop.reg.dao.mapper;

import org.cycop.reg.dataobjects.Address;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;

public class AddressMapperTest {

    @Test
    public void testRankMapper(){
        ResultSet rs = Mockito.mock(ResultSet.class);
        AddressMapper am = new AddressMapper();
        Address a;
        try {
            Mockito.when(rs.getLong("ADDRESS_SID")).thenReturn(Long.valueOf(1));
            Mockito.when(rs.getString("STREET_ADDRESS")).thenReturn("Street");
            Mockito.when(rs.getString("CITY")).thenReturn("City");
            Mockito.when(rs.getString("STATE")).thenReturn("State");
            Mockito.when(rs.getString("ZIP_CODE")).thenReturn("Zip");
            Mockito.when(rs.getTimestamp("CRE_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.getTimestamp("UPD_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
            a = am.mapRow(rs, 1);

            assertEquals(1,a.getAddressID());
            assertEquals("Street", a.getStreetAddress());
            assertEquals("City", a.getCity());
            assertEquals("State", a.getState());
            assertEquals("Zip", a.getZipCode());
        }catch(Exception e){
            assert(false);
        }
    }
}
