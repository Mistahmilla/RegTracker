package org.cycop.reg.dao;

import org.cycop.reg.dao.mapper.ShirtSizeMapper;
import org.cycop.reg.dataobjects.ShirtSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class ShirtSizeDAO {

    private JdbcTemplate jdbcTemplate;
    private ShirtSizeMapper shirtSizeMapper;

    @Autowired
    public void init(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        shirtSizeMapper = new ShirtSizeMapper();

    }

    public List<ShirtSize> getShirtSize(){
        String sql = "SELECT * FROM T_SHIRT_SIZE";
        return jdbcTemplate.query(sql, shirtSizeMapper);
    }

    public List<ShirtSize> getShirtSize(String shirtSizeCode){
        String sql = "SELECT * FROM T_SHIRT_SIZE WHERE SHIRT_SIZE_C = ?";
        Object[] params = new Object[1];
        params[0] = shirtSizeCode;
        return jdbcTemplate.query(sql, params, shirtSizeMapper);
    }

}
