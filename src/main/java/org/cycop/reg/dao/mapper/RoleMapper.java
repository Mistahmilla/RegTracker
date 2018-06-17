package org.cycop.reg.dao.mapper;

import org.cycop.reg.security.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet resultSet, int i) throws SQLException {
        Role r = new Role(resultSet.getString("ROLE_C"), resultSet.getString("ROLE_DS"));
        return r;
    }
}
