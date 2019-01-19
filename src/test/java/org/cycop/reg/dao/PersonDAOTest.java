package org.cycop.reg.dao;

import org.cycop.reg.dao.mapper.PersonMapper;
import org.cycop.reg.dataobjects.Person;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.time.LocalDate;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;

public class PersonDAOTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @Mock
    PersonMapper personMapper;

    @Mock
    KeyHolderFactory keyHolderFactory;

    @Mock
    GeneratedKeyHolder key;

    @Spy
    @InjectMocks
    PersonDAO personDAO;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void personDeleteTest(){
        personDAO.delete(1);
        verify(jdbcTemplate).update("DELETE FROM T_PER WHERE PER_SID = ?", (long)1);
    }

    @Test
    public void personGetTest(){
        personDAO.get(1, "Matt", 2);
        ArgumentCaptor<Object[]> argument = ArgumentCaptor.forClass(Object[].class);
        verify(jdbcTemplate).query(Mockito.anyString(), argument.capture(), Mockito.any(PersonMapper.class));
        assertEquals(3, argument.getValue().length);
    }

    @Test
    public void personSaveOrUpdateTest(){
        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Smith");
        p.setPhoneNumber("(123) 456-7890");
        p.setEmailAddress("test@test.com");
        p.setBirthDate(LocalDate.of(1995, 12, 31));
        p.setGender("M");

        Mockito.doReturn(key).when(keyHolderFactory).newKeyHolder();
        Mockito.doReturn(1).when(key).getKey();
        personDAO.saveOrUpdate(p);
        verify(jdbcTemplate).update(Mockito.any(PreparedStatementCreator.class), Mockito.any(GeneratedKeyHolder.class));
    }
}
