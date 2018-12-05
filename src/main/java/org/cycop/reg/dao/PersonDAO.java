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
import java.util.ArrayList;
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

        if(person.getPersonID() != 0){
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

    public void delete(long personId) {
        logger.info("Deleting person: {}", personId);
        String sql = "DELETE FROM T_PER WHERE PER_SID = ?";
        jdbcTemplate.update(sql, personId);
    }

    public List<Person> get(long personID, String personName, long accountID){
        String sql = "SELECT * FROM T_PER";

        List params = new ArrayList();

        if(personID != 0){
            sql = addWhere(sql);
            sql = sql.concat(" PER_SID = ?");
            params.add(personID);
        }
        if(!personName.isEmpty()){
            sql = addWhere(sql);
            sql = sql.concat(" CONCAT(PER_FIRST_NM, ' ', PER_LAST_NM) LIKE ?");
            params.add(personName);
        }
        if(accountID != 0){
            sql = addWhere(sql);
            sql = sql.concat(" PER_SID IN (SELECT PER_SID FROM T_ACNT_PER WHERE ACNT_SID = ? AND ACTIVE_PER_I = 'Y')");
            params.add(accountID);
        }
        return jdbcTemplate.query(sql, params.toArray(), personMapper);
    }

    private String addWhere(String inputSQL){
        if (!inputSQL.contains("WHERE")){
            return inputSQL.concat(" WHERE");
        }else {
            return inputSQL.concat(" AND");
        }
    }

}
