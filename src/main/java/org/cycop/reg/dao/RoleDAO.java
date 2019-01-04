package org.cycop.reg.dao;

import org.cycop.reg.dao.mapper.PermissionMapper;
import org.cycop.reg.dao.mapper.RoleMapper;
import org.cycop.reg.dataobjects.Permission;
import org.cycop.reg.dataobjects.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class RoleDAO {

    private JdbcTemplate jdbcTemplate;
    private RoleMapper roleMapper;
    private PermissionMapper permissionMapper;

    @Autowired
    public void init(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.roleMapper = new RoleMapper();
        this.permissionMapper = new PermissionMapper();
    }

    public List<Role> get(String roleCode){
        String sql = "SELECT * FROM T_ROLE WHERE ROLE_C = ?";
        Object[] params = new Object[1];
        params[0] = roleCode;
        return jdbcTemplate.query(sql, params, roleMapper);
    }

    public List<Permission> getRolePermissions(String roleCode){
        String sql = "SELECT T_PERM.PERM_C, T_PERM.PERM_DS, T_PERM.PERM_NM, T_PERM.CRE_T, T_PERM.UPD_T FROM T_ROLE_PERM INNER JOIN T_PERM ON T_ROLE_PERM.PERM_C = T_PERM.PERM_C WHERE T_ROLE_PERM.ROLE_C = ?";
        Object[] params = new Object[1];
        params[0] = roleCode;
        return jdbcTemplate.query(sql, params, permissionMapper);
    }

}
