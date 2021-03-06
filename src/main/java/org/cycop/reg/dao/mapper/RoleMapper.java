package org.cycop.reg.dao.mapper;

import org.cycop.reg.dataobjects.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Role(resultSet.getString("ROLE_C"), resultSet.getString("ROLE_DS"));
    }
}
