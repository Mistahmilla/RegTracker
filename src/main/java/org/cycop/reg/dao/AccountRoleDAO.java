package org.cycop.reg.dao;

import org.cycop.reg.dataobjects.repositories.RoleRepository;
import org.cycop.reg.security.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRoleDAO {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RoleRepository roleRepo;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AccountRoleDAO(DataSource dataSource){
        logger.info("constructor");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Role> getRoles(long accountID){
        logger.info("test");
        int i = 0;
        String sql = "SELECT ROLE_C FROM T_ACNT_ROLE WHERE ACTV_ROLE_I = 'Y' AND ACNT_SID = ?";
        i++;
        logger.info("step: " + i);
        Object[] params = new Object[1];
        i++;
        logger.info("step: " + i);
        params[0] = accountID;
        i++;
        logger.info("step: " + i);
        ResultSet r = (ResultSet)jdbcTemplate.queryForRowSet(sql, params);
        i++;
        logger.info("step: " + i);
        List<Role> roles = new ArrayList();
        try{
            logger.info("try");
            while (r.next()){
                logger.info("loop");
                roles.add(roleRepo.getRole(r.getString("ROLE_C")).get(0));
            }
        } catch (SQLException e) {
            logger.info("catch");
            logger.error(e.getMessage());
        }
        return roles;
    }
}
