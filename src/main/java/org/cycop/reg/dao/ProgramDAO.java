package org.cycop.reg.dao;

import org.cycop.reg.dao.mapper.ProgramMapper;
import org.cycop.reg.dataobjects.Program;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class ProgramDAO {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProgramMapper programMapper;

    @Autowired
    public void init(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Program> get(long programID) {
        logger.info("Getting program: {}", programID);
        String sql = "SELECT * FROM T_PROGRAM WHERE PROGRAM_SID = ?";
        Object[] params = new Object[1];
        params[0] = programID;
        return jdbcTemplate.query(sql, params, programMapper);
    }

    public List<Program> get(){
        logger.info("Getting all programs");
        String sql = "SELECT * FROM T_PROGRAM";
        return jdbcTemplate.query(sql, programMapper);
    }
}
