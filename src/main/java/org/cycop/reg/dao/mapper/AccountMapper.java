package org.cycop.reg.dao.mapper;

import org.cycop.reg.dao.AccountRoleDAO;
import org.cycop.reg.security.Account;
import org.cycop.reg.security.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountMapper implements RowMapper<Account> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountRoleDAO accountRoleDAO;

    @Override
    public Account mapRow(ResultSet resultSet, int i) throws SQLException {
        Account a = new Account();
        a.setAccountID(resultSet.getLong("ACNT_SID"));
        a.setUsername(resultSet.getString("EML_AD_X"));
        a.setPassword(resultSet.getString("PWD_X"));
        a.setPasswordSalt(resultSet.getString("SALT_X"));
        a.setAccountLocked(!resultSet.getString("ACNT_LOCK_I").equals("N"));
        a.setAccountVerified(resultSet.getString("ACNT_VERIFIED_I").equals("Y"));
        a.setPasswordExpired(!resultSet.getString("PWD_EXP_I").equals("N"));
        logger.info("about to set authorities");
        List<Role> roles = new ArrayList();
        roles.add(new Role("USER", "User role."));
        a.setAuthorities(roles);
        logger.info("auth count: " +a.getAuthorities().size());
        return a;
    }

}
