package org.cycop.reg.dao;

import org.cycop.reg.dao.mapper.RankMapper;
import org.cycop.reg.dataobjects.Rank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class RankDAO {

    private JdbcTemplate jdbcTemplate;
    private RankMapper rankMapper;

    @Autowired
    public void init(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        rankMapper = new RankMapper();
    }

    public List<Rank> get(String rankCode) {
        String sql = "SELECT * FROM T_RANK WHERE RANK_C = ?";
        Object[] params = new Object[1];
        params[0] = rankCode;
        return jdbcTemplate.query(sql, params, rankMapper);
    }

    public List<Rank> list() {
        String sql = "SELECT * FROM T_RANK";
        return jdbcTemplate.query(sql, rankMapper);
    }
}
