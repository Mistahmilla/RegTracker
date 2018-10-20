package org.cycop.reg.dao.mapper;

import org.cycop.reg.dao.PersonDAO;
import org.cycop.reg.dao.ProgramDAO;
import org.cycop.reg.dataobjects.Address;
import org.cycop.reg.dataobjects.Registration;
import org.cycop.reg.dataobjects.repositories.GradeRepository;
import org.cycop.reg.dataobjects.repositories.RankRepository;
import org.cycop.reg.dataobjects.repositories.ShirtSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RegistrationMapper implements RowMapper<Registration> {

    @Autowired
    PersonDAO personDAO;

    @Autowired
    ProgramDAO programDAO;

    @Autowired
    RankRepository rankRepository;

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    ShirtSizeRepository shirtSizeRepository;

    @Override
    public Registration mapRow(ResultSet resultSet, int i) throws SQLException {
        Registration r = new Registration();
        r.setProgram(programDAO.get(resultSet.getLong("PROGRAM_SID")).get(0));
        r.setPerson(personDAO.get(resultSet.getLong("PER_SID")).get(0));
        r.setRegistrationDate(resultSet.getDate("REG_D").toLocalDate());

        if(rankRepository.getRank(resultSet.getString("RANK_C")).get(0) != null) {
            r.setRank(rankRepository.getRank(resultSet.getString("RANK_C")).get(0));
        }

        if (gradeRepository.getGrade(resultSet.getString("GRADE_C")).get(0) != null) {
            r.setGrade(gradeRepository.getGrade(resultSet.getString("GRADE_C")).get(0));
        }

        if(shirtSizeRepository.getShirtSizeByCode(resultSet.getString("SHIRT_SIZE_C")).get(0) != null) {
            r.setShirtSize(shirtSizeRepository.getShirtSizeByCode(resultSet.getString("SHIRT_SIZE_C")).get(0));
        }

        r.setCreateTime(resultSet.getTimestamp("PROG_CRE_T").toLocalDateTime());
        r.setUpdateTime(resultSet.getTimestamp("PROG_UPD_T").toLocalDateTime());

        if (resultSet.getString("STREET_ADDRESS") != null){
            Address a = new Address();
            a.setStreetAddress(resultSet.getString("STREET_ADDRESS"));
            a.setCity(resultSet.getString("CITY"));
            a.setState(resultSet.getString("STATE"));
            a.setZipCode(resultSet.getString("ZIP_CODE"));
            a.setAddressID(resultSet.getLong("ADDRESS_SID"));
            a.setCreateTime(resultSet.getTimestamp("ADDRESS_CRE_T").toLocalDateTime());
            a.setUpdateTime(resultSet.getTimestamp("ADDRESS_UPD_T").toLocalDateTime());
            r.setAddress(a);
        }
        return r;
    }
}
