package org.cycop.reg.dao.mapper;

import org.cycop.reg.dao.PersonDAO;
import org.cycop.reg.dataobjects.Person;
import org.cycop.reg.dataobjects.Role;
import org.cycop.reg.dataobjects.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserExtractor implements ResultSetExtractor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PersonDAO personDAO;

    @Override
    public List<User> extractData(ResultSet resultSet) throws SQLException {
        Map<Long, User> map = new HashMap<>();
        User a = null;
        Person p = null;
        while(resultSet.next()){
            Long id = resultSet.getLong("ACNT_SID");
            a = map.get(id);
            if(a == null){
                a = new User();
                logger.info("Retrieving user: " + resultSet.getLong("ACNT_SID"));
                a.setAccountID(resultSet.getLong("ACNT_SID"));
                a.setEmailAddress(resultSet.getString("EML_AD_X"));
                if (resultSet.getLong("PER_SID") != 0){
                    a.setPerson(personDAO.get(resultSet.getLong("PER_SID")).get(0));
                }
                map.put(id, a);
            }
            Role role = new Role(resultSet.getString("ROLE_C"), resultSet.getString("ROLE_DS"));
            a.addRole(role);
        }
        return new ArrayList<>(map.values());
    }
}
