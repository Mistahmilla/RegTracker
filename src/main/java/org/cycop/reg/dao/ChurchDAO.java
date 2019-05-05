package org.cycop.reg.dao;

import org.cycop.reg.dao.mapper.ChurchMapper;
import org.cycop.reg.dataobjects.Church;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChurchDAO {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ChurchMapper churchMapper;

    @Autowired
    public void init(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Church> get(long churchID, String churchName, String pastorName, String zipCode){
        String sql = "SELECT T_CHURCH.CHURCH_SID, T_CHURCH.CHURCH_NM, T_CHURCH.ADDRESS_SID, T_CHURCH.PASTOR_SID, T_CHURCH.PHONE_NUM, T_CHURCH.EML_AD_X, T_CHURCH.CRE_T, T_CHURCH.UPD_T FROM T_CHURCH LEFT JOIN T_PER ON T_CHURCH.PASTOR_SID = T_PER.PER_SID LEFT JOIN T_ADDRESS ON T_CHURCH.ADDRESS_SID = T_ADDRESS.ADDRESS_SID ";
        List params = new ArrayList();

        if (churchID != 0){
            sql = addWhere(sql);
            sql = sql.concat(" CHURCH_SID = ?");
            params.add(churchID);
        }
        if(!churchName.isEmpty()){
            sql = addWhere(sql);
            sql = sql.concat(" CHURCH_NM LIKE ?");
            params.add(churchName);
        }
        if(!pastorName.isEmpty()){
            sql = addWhere(sql);
            sql = sql.concat(" CONCAT(PER_FIRST_NM, ' ', PER_LAST_NM) LIKE ?");
            params.add(pastorName);
        }
        if(!zipCode.isEmpty()){
            sql = addWhere(sql);
            sql = sql.concat(" T_ADDRESS.ZIP_CODE = ?");
            params.add(zipCode);
        }
        return jdbcTemplate.query(sql, params.toArray(), churchMapper);
    }

    public void saveOrUpdate(Church church){
        if(church.getChurchID() != 0){

        }
    }

    private String addWhere(String inputSQL){
        if (!inputSQL.contains("WHERE")){
            return inputSQL.concat(" WHERE");
        }else {
            return inputSQL.concat(" AND");
        }
    }
}
