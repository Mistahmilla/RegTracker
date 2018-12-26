package org.cycop.reg.dao;

import org.cycop.reg.dataobjects.Person;
import org.cycop.reg.dataobjects.Program;
import org.cycop.reg.dataobjects.Registration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;

public class RegistrationDAOTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @Spy
    @InjectMocks
    RegistrationDAO registrationDAO;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveOrUpdate(){
        List rList = new ArrayList();
        Registration r = new Registration();
        Mockito.doReturn(rList).when(registrationDAO).getRegistrationsByProgramAndPerson(1, 2);

        Program prog = new Program();
        prog.setProgramID(1);
        Person per = new Person();
        per.setPersonID(2);

        r.setProgram(prog);
        r.setPerson(per);
        r.setRegistrationDate(LocalDate.of(2018, 12, 01));

        registrationDAO.saveOrUpdateRegistration(r);

        ArgumentCaptor<Object[]> argument = ArgumentCaptor.forClass(Object[].class);
        verify(jdbcTemplate).update(Mockito.anyString(), argument.capture());
        //verify(jdbcTemplate).update(anyString(), any(Object[].class));

        List l = argument.getAllValues();
        assertEquals((long)1, l.get(0));
    }
}
