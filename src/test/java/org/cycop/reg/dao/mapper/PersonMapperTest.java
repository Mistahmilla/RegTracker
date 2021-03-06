package org.cycop.reg.dao.mapper;

import org.cycop.reg.dao.PersonAddressDAO;
import org.cycop.reg.dataobjects.Person;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;

public class PersonMapperTest {

    @Mock
    PersonAddressDAO personAddressDAO;

    @InjectMocks
    PersonMapper pm;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRankMapper(){
        ResultSet rs = Mockito.mock(ResultSet.class);
        Person p;
        try {
            Mockito.when(rs.getLong("PER_SID")).thenReturn(Long.valueOf(1));
            Mockito.when(rs.getString("PER_FIRST_NM")).thenReturn("first");
            Mockito.when(rs.getString("PER_LAST_NM")).thenReturn("last");
            Mockito.when(rs.getDate("BIRTH_D")).thenReturn(Date.valueOf(LocalDate.now()));
            Mockito.when(rs.getString("SEX_C")).thenReturn("F");
            Mockito.when(rs.getString("EML_AD_X")).thenReturn("test@test.com");
            Mockito.when(rs.getString("PHONE_NUM")).thenReturn("(123) 456-7890");
            Mockito.when(rs.getTimestamp("CRE_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.getTimestamp("UPD_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
            p = pm.mapRow(rs, 1);

            assertEquals(1,p.getPersonID());
            assertEquals("first",p.getFirstName());
            assertEquals("last",p.getLastName());
            assertEquals("F",p.getGender().getGenderCode());
            assertEquals("test@test.com", p.getEmailAddress());
            assertEquals("(123) 456-7890", p.getPhoneNumber());
        }catch(Exception e){
            assert(false);
        }
    }
}
