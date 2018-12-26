package org.cycop.reg.dao.mapper;

import org.cycop.reg.dataobjects.Program;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProgramMapper  implements RowMapper<Program> {

    @Override
    public Program mapRow(ResultSet resultSet, int i) throws SQLException {
        Program p = new Program();
        p.setProgramID(resultSet.getLong("PROGRAM_SID"));
        p.setProgramName(resultSet.getString("PROGRAM_NM"));
        p.setStartDate(resultSet.getDate("START_D").toLocalDate());
        p.setEndDate(resultSet.getDate("END_D").toLocalDate());
        p.setCreateTime(resultSet.getTimestamp("CRE_T").toLocalDateTime());
        p.setUpdateTime(resultSet.getTimestamp("UPD_T").toLocalDateTime());
        return p;
    }
}
