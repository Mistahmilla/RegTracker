package org.cycop.reg.dao.mapper;

import org.cycop.reg.dao.ChurchAddressDAO;
import org.cycop.reg.dao.PersonDAO;
import org.cycop.reg.dataobjects.Address;
import org.cycop.reg.dataobjects.Church;
import org.cycop.reg.dataobjects.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ChurchMapper implements RowMapper<Church> {

    @Autowired
    private ChurchAddressDAO churchAddressDAO;

    @Autowired
    private PersonDAO personDAO;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Church mapRow(ResultSet resultSet, int i) throws SQLException {
        List<Address> aList;
        List<Person> pList;

        Church c = new Church();
        c.setChurchID(resultSet.getLong("CHURCH_SID"));
        c.setChurchName(resultSet.getString("CHURCH_NM"));
        c.setPhoneNumber(resultSet.getString("PHONE_NUM"));
        c.setEmailAddress(resultSet.getString("EML_AD_X"));

        aList = churchAddressDAO.get(c.getChurchID());
        if(aList.size()==1){
            c.setAddress(aList.get(0));
        }

        if(resultSet.getLong("PASTOR_SID") != 0) {
            pList = personDAO.get(resultSet.getLong("PASTOR_SID"), "", 0);
            if(pList.size()==1){
                c.setPastor(pList.get(0));
            }
        }

        return c;
    }
}
