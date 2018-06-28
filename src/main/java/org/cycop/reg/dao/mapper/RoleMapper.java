package org.cycop.reg.dao.mapper;

import org.cycop.reg.security.Authority;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<Authority> {

    @Override
    public Authority mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Authority(resultSet.getString("ROLE_C"), resultSet.getString("ROLE_NM"), resultSet.getString("ROLE_DS"));
    }
}
