package org.cycop.reg.dao.mapper;

import org.cycop.reg.dao.ChurchAddressDAO;
import org.cycop.reg.dao.PersonDAO;
import org.cycop.reg.dataobjects.Address;
import org.cycop.reg.dataobjects.Church;
import org.cycop.reg.dataobjects.Person;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ChurchMapperTest {

    @Mock
    ChurchAddressDAO churchAddressDAO;

    @Mock
    PersonDAO personDAO;

    @InjectMocks
    ChurchMapper churchMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void churchMapperTest(){
        ResultSet rs = Mockito.mock(ResultSet.class);
        Church c;

        List<Address> aList = new ArrayList();
        Address a = new Address();
        a.setStreetAddress("123 Fake Street");
        aList.add(a);

        List<Person> pList = new ArrayList();
        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Smith");
        pList.add(p);
        try {
            Mockito.when(rs.getLong("CHURCH_SID")).thenReturn(Long.valueOf(1));
            Mockito.when(rs.getString("CHURCH_NM")).thenReturn("Church");
            Mockito.when(rs.getString("PHONE_NUM")).thenReturn("phone");
            Mockito.when(rs.getString("EML_AD_X")).thenReturn("email");
            Mockito.when(rs.getLong("ADDRESS_SID")).thenReturn(Long.valueOf(5));
            Mockito.when(rs.getLong("PASTOR_SID")).thenReturn(Long.valueOf(10));
            Mockito.when(churchAddressDAO.get(1)).thenReturn(aList);
            Mockito.when(personDAO.get(10, "", 0)).thenReturn(pList);
            c=churchMapper.mapRow(rs,1);

            assertEquals(1, c.getChurchID());
            assertEquals("Church", c.getChurchName());
            assertEquals("phone", c.getPhoneNumber());
            assertEquals("email", c.getEmailAddress());
            assertEquals("123 Fake Street", c.getAddress().getStreetAddress());
            assertEquals("John", c.getPastor().getFirstName());
        }catch(Exception e){
            assert(false);
        }
    }
}
