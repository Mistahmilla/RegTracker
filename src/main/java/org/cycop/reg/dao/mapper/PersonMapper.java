package org.cycop.reg.dao.mapper;

import org.cycop.reg.dao.AddressDAO;
import org.cycop.reg.dataobjects.Address;
import org.cycop.reg.dataobjects.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class PersonMapper implements RowMapper<Person>{

    @Autowired
    private AddressDAO addressDAO;

    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        Person p = new Person();
        List<Address> a;
        p.setPersonID(resultSet.getLong("PER_SID"));
        p.setFirstName(resultSet.getString("PER_FIRST_NM"));
        p.setLastName(resultSet.getString("PER_LAST_NM"));
        if(resultSet.getDate("BIRTH_D") != null) {
            p.setBirthDate(resultSet.getDate("BIRTH_D").toLocalDate());
        }
        if(resultSet.getString("SEX_C") != null){
            p.setGender(resultSet.getString("SEX_C"));
        }
        if(resultSet.getTimestamp("CRE_T") != null){
            p.setCreateTime(resultSet.getTimestamp("CRE_T").toLocalDateTime());
        }
        if(resultSet.getTimestamp("UPD_T") != null){
            p.setUpdateTime(resultSet.getTimestamp("UPD_T").toLocalDateTime());
        }
        a = addressDAO.get(p.getPersonID());
        if (a.size()==1){
            p.setCurrentAddress(a.get(0));
        }
        return p;
    }
}
