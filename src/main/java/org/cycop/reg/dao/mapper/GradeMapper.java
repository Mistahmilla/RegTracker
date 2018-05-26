package org.cycop.reg.dao.mapper;

import org.cycop.reg.dataobjects.Grade;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GradeMapper implements RowMapper<Grade>{

    @Override
    public Grade mapRow(ResultSet resultSet, int i) throws SQLException {
        Grade g = new Grade(resultSet.getString("GRADE_C"), resultSet.getString("GRADE_DS"), resultSet.getInt("SORT_ORD"));
        g.setCreateTime(resultSet.getTimestamp("CRE_T").toLocalDateTime());
        g.setUpdateTime(resultSet.getTimestamp("UPD_T").toLocalDateTime());
        return g;
    }

}
