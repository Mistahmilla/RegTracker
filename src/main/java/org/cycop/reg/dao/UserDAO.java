package org.cycop.reg.dao;

import com.mysql.jdbc.Statement;
import org.cycop.reg.dao.mapper.UserExtractor;
import org.cycop.reg.dataobjects.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class UserDAO {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserExtractor userExtractor;
    private String userSQL;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void init(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        userSQL = "SELECT T_ACNT.ACNT_SID, T_ACNT.EML_AD_X, " +
                "T_ACNT.CRE_T, T_ACNT.UPD_T, T_ROLE.ROLE_C, T_ROLE.ROLE_DS, T_ACNT.PER_SID, " +
                "T_PER.PER_SID, T_PER.PER_FIRST_NM, T_PER.PER_LAST_NM, T_PER.BIRTH_D, T_PER.SEX_C " +
                "FROM T_ACNT " +
                "LEFT JOIN T_ACNT_ROLE ON T_ACNT.ACNT_SID = T_ACNT_ROLE.ACNT_SID " +
                "LEFT JOIN T_ROLE ON T_ACNT_ROLE.ROLE_C = T_ROLE.ROLE_C " +
                "LEFT JOIN T_PER ON T_ACNT.PER_SID = T_PER.PER_SID " +
                "WHERE T_ACNT_ROLE.ACTIVE_ROLE_I = 'Y'";
    }

    public List<User> getUserByEmailAddress(String emailAddress){
        String sql = userSQL + " AND T_ACNT.EML_AD_X = ?";
        Object[] params = new Object[1];
        params[0] = emailAddress;
        return (List<User>)jdbcTemplate.query(sql, params, userExtractor);
    }

    public List<User> getUserByAccountID(long accountID){
        String sql = userSQL + " AND T_ACNT.ACNT_SID = ?";
        Object[] params = new Object[1];
        params[0] = accountID;
        return (List<User>)jdbcTemplate.query(sql, params, userExtractor);
    }

    public long SaveOrUpdate(User user){
        logger.info("Updating user: " +user.getAccountID());
        String insertSQL = "INSERT INTO T_ACNT (EML_AD_X, PWD_X, SALT_X, CRE_T, UPD_T) VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);";
        String roleSQL = "INSERT INTO T_ACNT_ROLE (ACNT_SID, ROLE_C, ADD_D, CRE_T, UPD_T) VALUES (?, ?, NOW(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);";

        if (Long.valueOf(user.getAccountID()) == null || user.getAccountID() == 0) {
            logger.info("Creating new user");
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement p = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
                    p.setString(1, user.getEmailAddress());
                    //TODO: encrypt password before saving
                    p.setString(2, "{noop}"+user.getPassword());
                    //TODO: add actual salt
                    p.setString(3, "test");
                    return p;
                }
            }, keyHolder);

            for (int i = 0; i < user.getRoles().size(); i++){
                Object[] params = new Object[2];
                params[0] = keyHolder.getKey().longValue();
                params[1] = user.getRoles().get(i).getRoleCode();
                jdbcTemplate.update(roleSQL, params);
            }

            return keyHolder.getKey().longValue();
        }
        //TODO: add code to update users
        return 0;
    }
}
