package org.cycop.reg.dao;

import org.cycop.reg.dao.mapper.PersonMapper;
import org.cycop.reg.dataobjects.Person;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class PersonDAOImpl implements PersonDAO {

    private JdbcTemplate jdbcTemplate;
    private PersonMapper personMapper;

    public PersonDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        personMapper = new PersonMapper();
    }

    public void saveOrUpdate(Person person) {
        if(person.getPersonID() > 0){
            String sql = "UPDATE T_PER SET PER_FIRST_NM = ?, PER_LAST_NM = ? WHERE PER_SID = ?;";
            jdbcTemplate.update(sql, person.getFirstName(), person.getLastName(), person.getPersonID());
        }else{
            String sql = "INSERT INTO T_PER (PER_FIRST_NM, PER_LAST_NM) VALUES (?, ?);";
            jdbcTemplate.update(sql, person.getFirstName(), person.getLastName());
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
