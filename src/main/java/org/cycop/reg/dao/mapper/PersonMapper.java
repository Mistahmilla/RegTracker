package org.cycop.reg.dao.mapper;

import org.cycop.reg.dataobjects.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person>{

    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        Person p = new Person();
        p.setPersonID(resultSet.getLong("PER_SID"));
        p.setFirstName(resultSet.getString("PER_FIRST_NM"));
        p.setLastName(resultSet.getString("PER_LAST_NM"));
        if(resultSet.getDate("BIRTH_D") != null) {
            p.setBirthDate(resultSet.getDate("BIRTH_D").toLocalDate());
        }
        return p;
    }
}
