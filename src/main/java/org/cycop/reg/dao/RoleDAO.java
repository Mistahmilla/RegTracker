package org.cycop.reg.dao;

import org.cycop.reg.dao.mapper.RoleMapper;
import org.cycop.reg.dataobjects.Role;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class RoleDAO {

    private JdbcTemplate jdbcTemplate;
    private RoleMapper roleMapper;

    public RoleDAO(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.roleMapper = new RoleMapper();
    }

    public List<Role> get(String roleCode){
        String sql = "SELECT * FROM T_ROLE WHERE ROLE_C = ?";
        Object[] params = new Object[1];
        params[0] = roleCode;
        return jdbcTemplate.query(sql, params, roleMapper);
    }

}