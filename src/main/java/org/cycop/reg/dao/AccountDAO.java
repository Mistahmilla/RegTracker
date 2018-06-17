package org.cycop.reg.dao;

import org.cycop.reg.dao.mapper.AccountMapper;
import org.cycop.reg.security.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.sql.DataSource;
import java.util.List;

public class AccountDAO implements UserDetailsService {

    private JdbcTemplate jdbcTemplate;
    private AccountMapper accountMapper;

    public AccountDAO(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.accountMapper = new AccountMapper();
    }

    public List<Account> getAccounByID(long AccountID) {
        String sql = "SELECT * FROM T_ACNT WHERE ACNT_SID = ?";
        Object[] params = new Object[1];
        params[0] = AccountID;
        return jdbcTemplate.query(sql, params, accountMapper);
    }

    public List<Account> getAccountByEmailAddress(String emailAddress){
        String sql = "SELECT * FROM T_ACNT WHERE EML_AD_X = ?";
        Object[] params = new Object[1];
        params[0] = emailAddress;
        return jdbcTemplate.query(sql, params, accountMapper);
    }

    public List<Account> list(){
        return jdbcTemplate.query("SELECT * FROM T_ACNT", accountMapper);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return getAccountByEmailAddress(s).get(0);
    }
}
