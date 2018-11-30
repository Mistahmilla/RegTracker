package org.cycop.reg.dao.mapper;

import org.cycop.reg.dao.PersonDAO;
import org.cycop.reg.dao.RoleDAO;
import org.cycop.reg.dataobjects.Person;
import org.cycop.reg.dataobjects.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class UserExtractorTest {

    @Mock
    PersonDAO personDAO;

    @Mock
    RoleDAO roleDAO;

    @InjectMocks
    UserExtractor userExtractor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserExtractor(){
        List<Person> plist = new ArrayList<Person>();
        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Doe");
        p.setPersonID((long)1);
        plist.add(p);
        Mockito.when(personDAO.get((long)1)).thenReturn(plist);

        ResultSet rs = Mockito.mock(ResultSet.class);
        List<User> list;
        User u;
        try {
            Mockito.when(rs.getLong("ACNT_SID")).thenReturn(Long.valueOf(1));
            Mockito.when(rs.getLong("PER_SID")).thenReturn(Long.valueOf(1));
            Mockito.when(rs.getString("EML_AD_X")).thenReturn("email");
            Mockito.when(rs.getString("ACNT_LOCK_I")).thenReturn("Y");
            Mockito.when(rs.getString("ACNT_VERIFIED_I")).thenReturn("Y");
            Mockito.when(rs.getString("PWD_EXP_I")).thenReturn("Y");
            Mockito.when(rs.getString("ROLE_C")).thenReturn("RC");
            Mockito.when(rs.getString("ROLE_DS")).thenReturn("RDS");
            Mockito.when(rs.getTimestamp("CRE_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.getTimestamp("UPD_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
            list = userExtractor.extractData(rs);

            u = list.get(0);

            assertEquals(1,u.getAccountID());
            assertEquals("email",u.getEmailAddress());
            assertEquals(true, u.getAccountLocked());
            assertEquals(true, u.getAccountVerified());
            assertEquals(true, u.getPasswordExpired());
        }catch(Exception e){
            assert(false);
        }
    }

    @Test
    public void testUserExtractor2(){
        List<Person> plist = new ArrayList<Person>();
        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Doe");
        p.setPersonID((long)1);
        plist.add(p);
        Mockito.when(personDAO.get((long)1)).thenReturn(plist);

        ResultSet rs = Mockito.mock(ResultSet.class);
        List<User> list;
        User u;
        try {
            Mockito.when(rs.getLong("ACNT_SID")).thenReturn(Long.valueOf(1));
            Mockito.when(rs.getLong("PER_SID")).thenReturn(Long.valueOf(1));
            Mockito.when(rs.getString("EML_AD_X")).thenReturn("email");
            Mockito.when(rs.getString("ACNT_LOCK_I")).thenReturn("N");
            Mockito.when(rs.getString("ACNT_VERIFIED_I")).thenReturn("N");
            Mockito.when(rs.getString("PWD_EXP_I")).thenReturn("N");
            Mockito.when(rs.getString("ROLE_C")).thenReturn("RC");
            Mockito.when(rs.getString("ROLE_DS")).thenReturn("RDS");
            Mockito.when(rs.getTimestamp("CRE_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.getTimestamp("UPD_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
            list = userExtractor.extractData(rs);

            u = list.get(0);

            assertEquals(1,u.getAccountID());
            assertEquals("email",u.getEmailAddress());
            assertEquals(false, u.getAccountLocked());
            assertEquals(false, u.getAccountVerified());
            assertEquals(false, u.getPasswordExpired());
        }catch(Exception e){
            assert(false);
        }
    }

    @Test
    public void testUserPermissions(){
        List<Person> plist = new ArrayList<Person>();
        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Doe");
        p.setPersonID((long)1);
        plist.add(p);
        Mockito.when(personDAO.get((long)1)).thenReturn(plist);

        ResultSet rs = Mockito.mock(ResultSet.class);
        List<User> list;
        User u;
        try {
            Mockito.when(rs.getLong("ACNT_SID")).thenReturn(Long.valueOf(1)).thenReturn(Long.valueOf(1));
            Mockito.when(rs.getLong("PER_SID")).thenReturn(Long.valueOf(1)).thenReturn(Long.valueOf(1));
            Mockito.when(rs.getString("EML_AD_X")).thenReturn("email");
            Mockito.when(rs.getString("ACNT_LOCK_I")).thenReturn("N");
            Mockito.when(rs.getString("ACNT_VERIFIED_I")).thenReturn("N");
            Mockito.when(rs.getString("PWD_EXP_I")).thenReturn("N");
            Mockito.when(rs.getString("ROLE_C")).thenReturn("RC");
            Mockito.when(rs.getString("ROLE_DS")).thenReturn("RDS");
            Mockito.when(rs.getTimestamp("CRE_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.getTimestamp("UPD_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
            list = userExtractor.extractData(rs);

            u = list.get(0);

            assertEquals(1,u.getAccountID());
            assertEquals("email",u.getEmailAddress());
            assertEquals(false, u.getAccountLocked());
            assertEquals(false, u.getAccountVerified());
            assertEquals(false, u.getPasswordExpired());
        }catch(Exception e){
            assert(false);
        }
    }
}
