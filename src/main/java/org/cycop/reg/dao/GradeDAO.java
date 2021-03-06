package org.cycop.reg.dao;

import org.cycop.reg.dao.mapper.GradeMapper;
import org.cycop.reg.dataobjects.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class GradeDAO {
    private JdbcTemplate jdbcTemplate;
    private GradeMapper gradeMapper;

    @Autowired
    public void init(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
        gradeMapper = new GradeMapper();
    }
    public List<Grade> get(String gradeCode) {
        String sql = "SELECT * FROM T_GRADE WHERE GRADE_C = ?";
        Object[] params = new Object[1];
        params[0] = gradeCode;
        return jdbcTemplate.query(sql, params, gradeMapper);
    }

    public List<Grade> list() {
        String sql = "SELECT * FROM T_GRADE";
        return jdbcTemplate.query(sql, gradeMapper);
    }
}
