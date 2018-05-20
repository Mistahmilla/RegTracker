package org.cycop.reg.dao;

import org.cycop.reg.dao.mapper.PersonMapper;
import org.cycop.reg.dataobjects.Person;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class PersonDAOImpl implements PersonDAO {

    private JdbcTemplate jdbcTemplate;
    private PersonMapper personMapper;

    public PersonDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        personMapper = new PersonMapper();
    }

    public long saveOrUpdate(Person person) {
        String genderCode = "";

        if (person.getGender() != null){
            genderCode = person.getGender().getGenderCode();
        }
        if(person.getPersonID() != null){
            String sql = "UPDATE T_PER SET PER_FIRST_NM = ?, PER_LAST_NM = ?, SEX_C = ?, UPD_T = CURRENT_TIMESTAMP WHERE PER_SID = ?;";
            jdbcTemplate.update(sql, person.getFirstName(), person.getLastName(), genderCode, person.getPersonID());
            return person.getPersonID();
        }else{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            String sql = "INSERT INTO T_PER (PER_FIRST_NM, PER_LAST_NM, SEX_C, CRE_T, UPD_T) VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);";
            jdbcTemplate.update(sql, keyHolder, person.getFirstName(), person.getLastName(), genderCode);
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

    public List<Person> list() {
        String sql = "SELECT * FROM T_PER";
        List<Person> personList = jdbcTemplate.query(sql, personMapper);
        return personList;
    }
}
