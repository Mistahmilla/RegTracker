package org.cycop.reg.dao;

import org.cycop.reg.dao.mapper.RegistrationMapper;
import org.cycop.reg.dataobjects.Registration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
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

    public List<Registration> getRegistrationsByProgramAndPerson(long programID, long personID){
        Object[] params = new Object[2];
        params[0] = programID;
        params[1] = personID;
        return jdbcTemplate.query(selectSQL.concat("WHERE PROGRAM_SID = ? AND PER_SID = ?"), params, registrationMapper);
    }

    public List<Registration> saveOrUpdateRegistration(Registration registration){

        Object[] params;
        boolean bNewReg;
        String insertSQL = "INSERT INTO T_PROGRAM_REG (PROGRAM_SID, PER_SID, REG_D, RANK_C, GRADE_C, SHIRT_SIZE_C, ADDRESS_SID, CRE_T, UPD_T) VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        String updateSQL = "UPDATE T_PROGRAM_REG SET PROGRAM_SID = ?, PER_SID = ?, REG_D = ?, RANK_C = ?, GRADE_C = ?, SHIRT_SIZE_C = ?, ADDRESS_SID = ?, UPD_T = CURRENT_TIMESTAMP, REG_CNCL_D = ? WHERE PROGRAM_SID = ? AND PER_SID = ?";

        List<Registration> regList = getRegistrationsByProgramAndPerson(registration.getProgram().getProgramID(), registration.getPerson().getPersonID());

        if (regList.isEmpty()){
            bNewReg = true;
            params = new Object[7];
        }else{
            //existing reg
            bNewReg = false;
            params = new Object[10];
        }

        params[0] = registration.getProgram().getProgramID();
        params[1] = registration.getPerson().getPersonID();
        if(bNewReg){
            params[2] = Date.valueOf(LocalDate.now());
        }else {
            params[2] = Date.valueOf(registration.getRegistrationDate());
        }
        params[3] = registration.getRank().getRankCode();
        params[4] = registration.getGrade().getGradeCode();
        params[5] = registration.getShirtSize().getShirtSizeCode();
        params[6] = registration.getAddress().getAddressID();

        if (!bNewReg){
            if(registration.getRegistrationCancelDate() != null) {
                params[7] = Date.valueOf(registration.getRegistrationCancelDate());
            }
            params[8] = registration.getProgram().getProgramID();
            params[9] = registration.getPerson().getPersonID();
        }


        jdbcTemplate.update(bNewReg?insertSQL:updateSQL, params);

        return getRegistrationsByProgramAndPerson(registration.getProgram().getProgramID(), registration.getPerson().getPersonID());
    }

}
