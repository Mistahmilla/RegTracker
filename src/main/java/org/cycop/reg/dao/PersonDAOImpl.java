package org.cycop.reg.dao;

import org.cycop.reg.dataobjects.Person;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class PersonDAOImpl implements PersonDAO {

    private JdbcTemplate jdbcTemplate;

    public PersonDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
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

    public Person get(Long personId) {
        String sql = "SELECT * FROM T_PER WHERE PER_SID = "+personId;
        return jdbcTemplate.query(sql, new ResultSetExtractor<Person>() {
            @Override
            public Person extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()){
                    Person p = new Person();
                    p.setPersonID(rs.getLong("PER_SID"));
                    p.setFirstName(rs.getString("PER_FIRST_NM"));
                    p.setLastName(rs.getString("PER_LAST_NM"));
                    p.setBirthDate(rs.getDate("BIRTH_D").toLocalDate());

                    return p;
                }
                return null;
            }
        });
    }

    public List<Person> list() {
        String sql = "SELECT * FROM T_PER";
        List<Person> personList = jdbcTemplate.query(sql, new RowMapper<Person>() {
            @Override
            public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
                Person p = new Person();
                p.setPersonID(rs.getLong("PER_SID"));
                p.setFirstName(rs.getString("PER_FIRST_NM"));
                p.setLastName(rs.getString("PER_LAST_NM"));
                return p;
            }
        });
        return personList;
    }
}
