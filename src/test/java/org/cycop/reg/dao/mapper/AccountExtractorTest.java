package org.cycop.reg.dao.mapper;

import org.cycop.reg.security.Account;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class AccountExtractorTest {

    @Test
    public void testAccountExractor(){
        List<Account> alist;
        Account a;
        AccountExtractor ae = new AccountExtractor();
        ResultSet rs = Mockito.mock(ResultSet.class);
        try {
            Mockito.when(rs.getLong("ACNT_SID")).thenReturn(Long.valueOf(1));
            Mockito.when(rs.getLong("PER_SID")).thenReturn(Long.valueOf(1));
            Mockito.when(rs.getString("EML_AD_X")).thenReturn("email");
            Mockito.when(rs.getString("PWD_X")).thenReturn("email");
            Mockito.when(rs.getString("SALT_X")).thenReturn("email");
            Mockito.when(rs.getString("ACNT_LOCK_I")).thenReturn("Y");
            Mockito.when(rs.getString("ACNT_VERIFIED_I")).thenReturn("Y");
            Mockito.when(rs.getString("PWD_EXP_I")).thenReturn("Y");
            Mockito.when(rs.getString("PERM_C")).thenReturn("RC");
            Mockito.when(rs.getString("PERM_DS")).thenReturn("RDS");
            Mockito.when(rs.getTimestamp("CRE_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.getTimestamp("UPD_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
            alist = ae.extractData(rs);

            a = alist.get(0);

            assertEquals(1,a.getAccountID());
            assertEquals("email",a.getUsername());
            assertEquals(false, a.isAccountNonLocked());
        }catch(Exception e){
            assert(false);
        }
    }
}
