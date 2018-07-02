package org.cycop.reg.dao.mapper;

import org.cycop.reg.dataobjects.Role;
import org.cycop.reg.dataobjects.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserExtractor implements ResultSetExtractor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<User> extractData(ResultSet resultSet) throws SQLException {
        Map<Long, User> map = new HashMap<>();
        User a = null;
        while(resultSet.next()){
            Long id = resultSet.getLong("ACNT_SID");
            a = map.get(id);
            if(a == null){
                a = new User();
                logger.info("Retrieving user: " + a.getAccountID());
                a.setAccountID(resultSet.getLong("ACNT_SID"));
                a.setEmailAddress(resultSet.getString("EML_AD_X"));

                map.put(id, a);
            }
            Role role = new Role(resultSet.getString("ROLE_C"), resultSet.getString("ROLE_DS"));
            a.addRole(role);
        }
        return new ArrayList<>(map.values());
    }
}
