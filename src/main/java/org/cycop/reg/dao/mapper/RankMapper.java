package org.cycop.reg.dao.mapper;

import org.cycop.reg.dataobjects.Rank;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RankMapper  implements RowMapper<Rank>{

    @Override
    public Rank mapRow(ResultSet resultSet, int i) throws SQLException {
        Rank r = new Rank(resultSet.getString("RANK_C"), resultSet.getString("RANK_DS"), resultSet.getString("NEXT_RANK_C"), resultSet.getInt("SORT_ORD"));
        r.setCreateTime(resultSet.getTimestamp("CRE_T").toLocalDateTime());
        r.setUpdateTime(resultSet.getTimestamp("UPD_T").toLocalDateTime());
        return r;
    }

}
