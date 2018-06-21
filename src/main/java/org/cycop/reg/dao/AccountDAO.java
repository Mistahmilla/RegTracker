package org.cycop.reg.dao;

import org.cycop.reg.dao.mapper.AccountExtractor;
import org.cycop.reg.security.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.sql.DataSource;
import java.util.List;

public class AccountDAO implements UserDetailsService {

    private JdbcTemplate jdbcTemplate;
    private AccountExtractor accountExtractor;
    private String accountSQL;

    public AccountDAO(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.accountExtractor = new AccountExtractor();

        accountSQL = "SELECT T_ACNT.ACNT_SID, T_ACNT.EML_AD_X, T_ACNT.PWD_X, T_ACNT.SALT_X, T_ACNT.ACNT_LOCK_I, T_ACNT.ACNT_VERIFIED_I, " +
                "T_ACNT.PWD_EXP_I, T_ACNT.CRE_T, T_ACNT.UPD_T, T_ACNT_ROLE.ROLE_C, T_ROLE.ROLE_DS " +
                "FROM T_ACNT LEFT JOIN T_ACNT_ROLE ON T_ACNT.ACNT_SID = T_ACNT_ROLE.ACNT_SID LEFT JOIN T_ROLE ON T_ACNT_ROLE.ROLE_C = T_ROLE.ROLE_C " +
                "WHERE T_ACNT_ROLE.ACTIVE_ROLE_I = 'Y'";
    }

    public List<Account> getAccounByID(long accountID) {
        String sql = accountSQL + " AND ACNT_SID = ?";
        Object[] params = new Object[1];
        params[0] = accountID;
        return (List<Account>)jdbcTemplate.query(sql, params, accountExtractor);
    }

    public List<Account> getAccountByEmailAddress(String emailAddress){
        String sql = accountSQL + " AND EML_AD_X = ?";
        Object[] params = new Object[1];
        params[0] = emailAddress;
        return (List<Account>)jdbcTemplate.query(sql, params, accountExtractor);
    }

    public List<Account> list(){
        return (List<Account>)jdbcTemplate.query(accountSQL, accountExtractor);
    }

    @Override
    public UserDetails loadUserByUsername(String s) {
        List<Account> u = getAccountByEmailAddress(s);
        if (u.size()!=1){
            throw new UsernameNotFoundException("Username was not found.");
        }
        return u.get(0);
    }
}
