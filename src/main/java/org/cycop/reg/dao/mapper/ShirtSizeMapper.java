package org.cycop.reg.dao.mapper;

import org.cycop.reg.dataobjects.ShirtSize;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShirtSizeMapper implements RowMapper<ShirtSize> {

    @Override
    public ShirtSize mapRow(ResultSet resultSet, int i) throws SQLException {
        ShirtSize s = new ShirtSize();
        s.setShirtSizeCode(resultSet.getString("SHIRT_SIZE_C"));
        s.setShirtSizeDescription(resultSet.getString("SHIRT_SIZE_DS"));
        s.setSortOrder(resultSet.getInt("SORT_ORD"));
        s.setCreateTime(resultSet.getTimestamp("CRE_T").toLocalDateTime());
        s.setUpdateTime(resultSet.getTimestamp("UPD_T").toLocalDateTime());
        return s;
    }
}
