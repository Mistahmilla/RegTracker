package org.cycop.reg.dao;

import com.mysql.jdbc.Statement;
import org.cycop.reg.dao.mapper.UserExtractor;
import org.cycop.reg.dataobjects.Person;
import org.cycop.reg.dataobjects.User;
import org.cycop.reg.security.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private AccountDAO accountDAO;
    //TODO: Try to figure out the circular reference
    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;
    private String userSQL;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void init(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        userSQL = "SELECT T_ACNT.ACNT_SID, T_ACNT.EML_AD_X, T_ACNT.SALT_X, ACNT_LOCK_I, ACNT_VERIFIED_I, PWD_EXP_I, " +
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

    public long createNew(User user){
        String insertSQL = "INSERT INTO T_ACNT (EML_AD_X, PWD_X, SALT_X, PER_SID, CRE_T, UPD_T) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);";
        String roleSQL = "INSERT INTO T_ACNT_ROLE (ACNT_SID, ROLE_C, ADD_D, CRE_T, UPD_T) VALUES (?, ?, NOW(), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);";

        Person per = personDAO.get(personDAO.saveOrUpdate(user.getPerson()), "", Long.valueOf(0)).get(0);

        if (Long.valueOf(user.getAccountID()) == null || user.getAccountID() == 0) {
            logger.info("Creating new user");
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement p = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
                    p.setString(1, user.getEmailAddress());
                    p.setString(2, passwordEncoder.encode(user.getPassword()));
                    //TODO: add actual salt
                    p.setString(3, "test");
                    p.setLong(4, per.getPersonID());
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
        throw new IllegalArgumentException("User already exists, update instead.");
    }

    public long updateExisting(User user, User existingUser){
        Account a;
        long personID = 0;
        String userUpdateSQL = "UPDATE T_ACNT SET EML_AD_X = ?, PWD_X = ?, SALT_X = ?, PER_SID = ?, ACNT_LOCK_I = ?, ACNT_VERIFIED_I = ?, PWD_EXP_I = ?, UPD_T = CURRENT_TIMESTAMP WHERE ACNT_SID = ?";

        if(user.getPerson() != null){
            personID = personDAO.saveOrUpdate(user.getPerson());
        }

        a = accountDAO.getAccounByID(user.getAccountID()).get(0);
        Object[] params = new Object[8];
        params[0] = user.getEmailAddress();
        if(user.getPassword() == null){
            params[1] = a.getPassword();
        }else {
            params[1] = user.getPassword();
        }
        params[2] = a.getPasswordSalt();
        if (personID != 0) {
            params[3] = personID;
        }else{
            params[3] = null;
        }
        if (user.getAccountLocked()){
            params[4] = "Y";
        }else{
            params[4] = "N";
        }
        if (user.getAccountVerified()){
            params[5] = "Y";
        }else{
            params[5] = "N";
        }
        if (user.getPasswordExpired()){
            params[6] = "Y";
        }else{
            params[6] = "N";
        }
        params[7] = user.getAccountID();

        jdbcTemplate.update(userUpdateSQL, params);

        return user.getAccountID();
    }

    public void addPersonToAccount(long accountID, long personID){
        String sql = "INSERT INTO T_ACNT_PER (ACNT_SID, PER_SID, ADD_D, ACTIVE_PER_I, CRE_T, UPD_T) VALUES (?, ?, CURRENT_DATE, 'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        Object[] params = new Object[2];
        params[0] = accountID;
        params[1]= personID;
        jdbcTemplate.update(sql, params);
    }

    public void removePersonFromAccount(long accountID, long personID){
        String sql = "UPDATE T_ACNT_PER SET REMOVE_D = CURRENT_DATE, ACTIVE_PER_I = 'N', UPD_T = CURRENT_TIMESTAMP WHERE ACNT_SID = ? AND PER_SID = ?";
        Object[] params = new Object[2];
        params[0] = accountID;
        params[1]= personID;
        jdbcTemplate.update(sql, params);
    }
}
