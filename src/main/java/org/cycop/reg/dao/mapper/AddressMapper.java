package org.cycop.reg.dao.mapper;

import org.cycop.reg.dataobjects.Address;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressMapper implements RowMapper<Address>{
    @Override
    public Address mapRow(ResultSet resultSet, int i) throws SQLException {
        Address a = new Address();
        a.setAddressID(resultSet.getLong("ADDRESS_SID"));
        a.setStreetAddress(resultSet.getString("STREET_ADDRESS"));
        a.setCity(resultSet.getString("CITY"));
        a.setState(resultSet.getString("STATE"));
        a.setZipCode(resultSet.getString("ZIP_CODE"));
        a.setCreateTime(resultSet.getTimestamp("CRE_T").toLocalDateTime());
        a.setUpdateTime(resultSet.getTimestamp("UPD_T").toLocalDateTime());
        return a;
    }
}
