package org.cycop.reg.dao;

import org.cycop.reg.dao.mapper.RegistrationMapper;
import org.cycop.reg.dataobjects.Registration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class RegistrationDAO {

    @Autowired
    private RegistrationMapper registrationMapper;
    private JdbcTemplate jdbcTemplate;
    private String selectSQL;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void init(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
        selectSQL = "SELECT PROGRAM_SID, PER_SID, REG_D, RANK_C, GRADE_C, SHIRT_SIZE_C, T_PROGRAM_REG.ADDRESS_SID, T_PROGRAM_REG.CRE_T AS PROG_CRE_T, T_PROGRAM_REG.UPD_T AS PROG_UPD_T, " +
                "STREET_ADDRESS, CITY, STATE, ZIP_CODE, T_ADDRESS.CRE_T AS ADDRESS_CRE_T, T_ADDRESS.UPD_T AS ADDRESS_UPD_T " +
                "FROM T_PROGRAM_REG " +
                "LEFT JOIN T_ADDRESS ON T_PROGRAM_REG.ADDRESS_SID = T_ADDRESS.ADDRESS_SID ";
    }

    public List<Registration> getRegistrationByProgram(long programID){
        logger.info("retrieving all registrations for program: {}", programID);
        Object[] params = new Object[1];
        params[0] = programID;
        return jdbcTemplate.query(selectSQL.concat("WHERE PROGRAM_SID = ?"), params, registrationMapper);
    }

    public List<Registration> getRegistrationByPerson(long personID){
        Object[] params = new Object[1];
        params[0] = personID;
        return jdbcTemplate.query(selectSQL.concat("WHERE PER_SID = ?"), params, registrationMapper);
    }

    public List<Registration> getRegistrationByAccount(long accountID){
        Object[] params = new Object[1];
        params[0] = accountID;
        return jdbcTemplate.query(selectSQL.concat("WHERE PER_SID IN (SELECT PER_SID FROM T_ACNT_PER WHERE ACNT_SID = ? AND ACTIVE_PER_I = 'Y')"), params, registrationMapper);
    }

}
