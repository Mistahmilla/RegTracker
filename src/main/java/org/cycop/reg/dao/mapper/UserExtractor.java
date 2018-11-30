package org.cycop.reg.dao.mapper;

import org.cycop.reg.dao.PersonDAO;
import org.cycop.reg.dao.RoleDAO;
import org.cycop.reg.dataobjects.Permission;
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

    @Autowired
    RoleDAO roleDAO;

    @Override
    public List<User> extractData(ResultSet resultSet) throws SQLException {
        List<Permission> pList;
        Map<Long, User> map = new HashMap<>();
        User a = null;
        while(resultSet.next()){
            Long id = resultSet.getLong("ACNT_SID");
            a = map.get(id);
            if(a == null){
                a = new User();
                logger.info("Retrieving user: {}", id);
                a.setAccountID(id);
                a.setEmailAddress(resultSet.getString("EML_AD_X"));
                a.setCreateTime(resultSet.getTimestamp("CRE_T").toLocalDateTime());
                a.setUpdateTime(resultSet.getTimestamp("UPD_T").toLocalDateTime());
                if (resultSet.getLong("PER_SID") != 0){
                    a.setPerson(personDAO.get(resultSet.getLong("PER_SID")).get(0));
                }
                if(resultSet.getString("ACNT_LOCK_I").equals("Y")){
                    a.setAccountLocked(true);
                }else{
                    a.setAccountLocked(false);
                }
                if(resultSet.getString("ACNT_VERIFIED_I").equals("Y")){
                    a.setAccountVerified(true);
                }else{
                    a.setAccountVerified(false);
                }
                if(resultSet.getString("PWD_EXP_I").equals("Y")){
                    a.setPasswordExpired(true);
                }else{
                    a.setPasswordExpired(false);
                }
                map.put(id, a);
            }
            Role role = new Role(resultSet.getString("ROLE_C"), resultSet.getString("ROLE_DS"));
            a.addRole(role);

            //get the list of permissions for this role and add them to the user
            pList = roleDAO.getRolePermissions(role.getRoleCode());
            for (int i = 0; i<pList.size(); i++){
                a.addPermission(pList.get(i));
            }
        }
        return new ArrayList<>(map.values());
    }
}
