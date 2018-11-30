package org.cycop.reg.dao.mapper;

import org.cycop.reg.dao.PersonDAO;
import org.cycop.reg.dao.ProgramDAO;
import org.cycop.reg.dataobjects.*;
import org.cycop.reg.dataobjects.repositories.GradeRepository;
import org.cycop.reg.dataobjects.repositories.RankRepository;
import org.cycop.reg.dataobjects.repositories.ShirtSizeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class RegistrationMapperTest {

    @Mock
    PersonDAO personDAO;

    @Mock
    ProgramDAO programDAO;

    @Mock
    RankRepository rankRepository;

    @Mock
    GradeRepository gradeRepository;

    @Mock
    ShirtSizeRepository shirtSizeRepository;

    @Mock
    ResultSet rs;

    @InjectMocks
    RegistrationMapper registrationMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegistrationMapper(){
        Registration r;
        try {
            List<Program> pList = new ArrayList();
            Program p = new Program();
            p.setProgramID(1);
            pList.add(p);
            Mockito.when(rs.getLong("PROGRAM_SID")).thenReturn(Long.valueOf(1));
            Mockito.when(programDAO.get(Long.valueOf(1))).thenReturn(pList);

            Person per = new Person();
            per.setPersonID(Long.valueOf(2));
            List<Person> perList = new ArrayList();
            perList.add(per);
            Mockito.when(rs.getLong("PER_SID")).thenReturn(Long.valueOf(2));
            Mockito.when(personDAO.get(Long.valueOf(2))).thenReturn(perList);

            Mockito.when(rs.getDate("REG_D")).thenReturn(Date.valueOf(LocalDate.now()));

            Mockito.when(rs.getTimestamp("PROG_CRE_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.getTimestamp("PROG_UPD_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));

            r = registrationMapper.mapRow(rs, 1);
            assertEquals(Long.valueOf(1), (Long)r.getProgram().getProgramID());
            assertEquals(Long.valueOf(2), r.getPerson().getPersonID());
        }catch (Exception e){
            assert(false);
        }
    }

    @Test
    public void testRegistrationMapper2(){
        Registration r;
        try {
            List<Program> pList = new ArrayList();
            Program p = new Program();
            p.setProgramID(1);
            pList.add(p);
            Mockito.when(rs.getLong("PROGRAM_SID")).thenReturn(Long.valueOf(1));
            Mockito.when(programDAO.get(Long.valueOf(1))).thenReturn(pList);

            Person per = new Person();
            per.setPersonID(Long.valueOf(2));
            List<Person> perList = new ArrayList();
            perList.add(per);
            Mockito.when(rs.getLong("PER_SID")).thenReturn(Long.valueOf(2));
            Mockito.when(personDAO.get(Long.valueOf(2))).thenReturn(perList);

            Mockito.when(rs.getDate("REG_D")).thenReturn(Date.valueOf(LocalDate.now()));

            Rank rnk = new Rank("R", "R", "R2",1);
            List rnkList = new ArrayList();
            rnkList.add(rnk);
            Mockito.when(rs.getString("RANK_C")).thenReturn("R");
            Mockito.when(rankRepository.getRank("R")).thenReturn(rnkList);

            Grade g = new Grade("G", "G", 1);
            List gList = new ArrayList();
            gList.add(g);
            Mockito.when(rs.getString("GRADE_C")).thenReturn("G");
            Mockito.when(gradeRepository.getGrade("G")).thenReturn(gList);

            ShirtSize s = new ShirtSize();
            s.setShirtSizeCode("S");
            List sList = new ArrayList();
            sList.add(s);
            Mockito.when(rs.getString("SHIRT_SIZE_C")).thenReturn("S");
            Mockito.when(shirtSizeRepository.getShirtSizeByCode("S")).thenReturn(sList);

            Mockito.when(rs.getString("STREET_ADDRESS")).thenReturn("Street");
            Mockito.when(rs.getString("CITY")).thenReturn("City");
            Mockito.when(rs.getString("STATE")).thenReturn("State");
            Mockito.when(rs.getString("ZIP_CODE")).thenReturn("Zip");
            Mockito.when(rs.getLong("ADDRESS_SID")).thenReturn(Long.valueOf(1));
            Mockito.when(rs.getTimestamp("ADDRESS_CRE_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.getTimestamp("ADDRESS_UPD_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));

            Mockito.when(rs.getTimestamp("PROG_CRE_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            Mockito.when(rs.getTimestamp("PROG_UPD_T")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));

            r = registrationMapper.mapRow(rs, 1);
            assertEquals(Long.valueOf(1), (Long)r.getProgram().getProgramID());
            assertEquals(Long.valueOf(2), r.getPerson().getPersonID());
            assertEquals("R", r.getRank().getRankCode());
            assertEquals("G", r.getGrade().getGradeCode());
            assertEquals("S", r.getShirtSize().getShirtSizeCode());
            assertEquals("Street", r.getAddress().getStreetAddress());
            assertEquals("City", r.getAddress().getCity());
            assertEquals("State", r.getAddress().getState());
            assertEquals("Zip", r.getAddress().getZipCode());
        }catch (Exception e){
            assert(false);
        }
    }
}
