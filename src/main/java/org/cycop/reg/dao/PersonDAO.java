package org.cycop.reg.dao;

import com.mysql.jdbc.Statement;
import org.cycop.reg.dao.mapper.PersonMapper;
import org.cycop.reg.dataobjects.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Component
public class PersonDAO {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    public void init(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public long saveOrUpdate(Person person) {
        String genderCode = "";

        if (person.getGender() != null){
            genderCode = person.getGender().getGenderCode();
        }

        if(person.getPersonID() != null){
            logger.info("Updating person: {}", person.getPersonID());
            String sql = "UPDATE T_PER SET PER_FIRST_NM = ?, PER_LAST_NM = ?, SEX_C = ?, BIRTH_D = ?, UPD_T = CURRENT_TIMESTAMP WHERE PER_SID = ?;";
            jdbcTemplate.update(sql, person.getFirstName(), person.getLastName(), genderCode, Date.valueOf(person.getBirthDate()), person.getPersonID());
            return person.getPersonID();
        }else{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String sql = "INSERT INTO T_PER (PER_FIRST_NM, PER_LAST_NM, SEX_C, BIRTH_D, CRE_T, UPD_T) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);";
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement p = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    p.setString(1, person.getFirstName());
                    p.setString(2, person.getLastName());
                    if(person.getGender() == null){
                        p.setNull(3,Types.NULL);
                    }else {
                        p.setString(3, person.getGender().getGenderCode());
                    }
                    if(person.getBirthDate() == null){
                        p.setNull(4, Types.NULL);
                    }else{
                        p.setDate(4, Date.valueOf(person.getBirthDate()));
                    }
                    return p;
                }
            }, keyHolder);
            logger.info("Created person: {}", keyHolder.getKey().longValue());
            return keyHolder.getKey().longValue();
        }
    }

    public void delete(Long personId) {
        logger.info("Deleting person: {}", personId);
        String sql = "DELETE FROM T_PER WHERE PER_SID = ?";
        jdbcTemplate.update(sql, personId);
    }

    public List<Person> get(Long personId) {
        logger.info("Getting person: {}", personId);
        String sql = "SELECT * FROM T_PER WHERE PER_SID = ?";
        Object[] params = new Object[1];
        params[0] = personId;
        return jdbcTemplate.query(sql, params, personMapper);
    }

    public List<Person> get(String personName){
        logger.info("Getting person by name");
        String nameParameter = personName;
        Object[] params = new Object[1];
        if (nameParameter != null){
            nameParameter = "%".concat(nameParameter.concat("%"));
        }
        params[0] = nameParameter;
        String sql = "SELECT * FROM T_PER WHERE CONCAT(PER_FIRST_NM, ' ', PER_LAST_NM) LIKE ?";
        return jdbcTemplate.query(sql, params, personMapper);
    }

    public List<Person> getByAccountID(Long accountID){
        logger.info("Getting person by account: {}", accountID);
        Object[] params = new Object[1];
        params[0] = accountID;
        String sql = "SELECT * FROM T_PER WHERE PER_SID IN (SELECT PER_SID FROM T_ACNT_PER WHERE ACNT_SID = ? AND ACTIVE_PER_I = 'Y')";
        return jdbcTemplate.query(sql, params, personMapper);
    }

    public List<Person> get(){
        logger.info("Getting all people");
        String sql = "SELECT * FROM T_PER";
        return jdbcTemplate.query(sql, personMapper);
    }

}
