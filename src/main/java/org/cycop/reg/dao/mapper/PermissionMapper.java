package org.cycop.reg.dao.mapper;

import org.cycop.reg.dataobjects.Permission;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PermissionMapper implements RowMapper<Permission> {

    @Override
    public Permission mapRow(ResultSet resultSet, int i) throws SQLException {
        Permission p = new Permission();
        p.setPermissionCode(resultSet.getString("PERM_C"));
        p.setPermissionDescription(resultSet.getString("PERM_DS"));
        p.setPermissionName(resultSet.getString("PERM_NM"));
        p.setCreateTime(resultSet.getTimestamp("CRE_T").toLocalDateTime());
        p.setUpdateTime(resultSet.getTimestamp("UPD_T").toLocalDateTime());
        return p;
    }
}
