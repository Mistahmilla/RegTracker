package org.cycop.reg.dao;

import com.mysql.jdbc.Statement;
import org.cycop.reg.dao.mapper.PersonMapper;
import org.cycop.reg.dataobjects.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class PersonDAO {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PersonMapper personMapper;

    public PersonDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public long saveOrUpdate(Person person) {
        String genderCode = "";

        if (person.getGender() != null){
            genderCode = person.getGender().getGenderCode();
        }

        if(person.getPersonID() != null){
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

            return keyHolder.getKey().longValue();
        }
    }

    public void delete(Long personId) {
        String sql = "DELETE FROM T_PER WHERE PER_SID = ?";
        jdbcTemplate.update(sql, personId);
    }

    public List<Person> get(Long personId) {
        String sql = "SELECT * FROM T_PER WHERE PER_SID = ?";
        Object[] params = new Object[1];
        params[0] = personId;
        return jdbcTemplate.query(sql, params, personMapper);
    }

    public List<Person> get(String personName){
        String nameParameter = personName;
        Object[] params = new Object[1];
        if (nameParameter != null){
            nameParameter = "%".concat(nameParameter.concat("%"));
        }
        params[0] = nameParameter;
        String sql = "SELECT * FROM T_PER WHERE CONCAT(PER_FIRST_NM, ' ', PER_LAST_NM) LIKE ?";
        return jdbcTemplate.query(sql, params, personMapper);
    }

    public List<Person> get(){
        String sql = "SELECT * FROM T_PER";
        return jdbcTemplate.query(sql, personMapper);
    }

}
