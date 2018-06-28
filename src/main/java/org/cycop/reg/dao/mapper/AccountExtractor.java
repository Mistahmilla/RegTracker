package org.cycop.reg.dao.mapper;

import org.cycop.reg.security.Account;
import org.cycop.reg.security.Authority;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountExtractor implements ResultSetExtractor {
    @Override
    public List<Account> extractData(ResultSet resultSet) throws SQLException {
        Map<Long, Account> map = new HashMap<>();
        Account a = null;
        while(resultSet.next()){
            Long id = resultSet.getLong("ACNT_SID");
            a = map.get(id);
            if(a == null){
                a = new Account();
                a.setAccountID(resultSet.getLong("ACNT_SID"));
                a.setUsername(resultSet.getString("EML_AD_X"));
                a.setPassword(resultSet.getString("PWD_X"));
                a.setPasswordSalt(resultSet.getString("SALT_X"));
                a.setAccountLocked(!resultSet.getString("ACNT_LOCK_I").equals("N"));
                a.setAccountVerified(resultSet.getString("ACNT_VERIFIED_I").equals("Y"));
                a.setPasswordExpired(!resultSet.getString("PWD_EXP_I").equals("N"));
                map.put(id, a);
            }
            Authority authority = new Authority(resultSet.getString("PERM_C"), resultSet.getString("PERM_NM"), resultSet.getString("PERM_DS"));
            a.addAuthority(authority);
        }
        return new ArrayList<>(map.values());
    }
}
