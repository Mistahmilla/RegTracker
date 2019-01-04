package org.cycop.reg.dao;

import org.cycop.reg.dao.mapper.PersonMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;

public class PersonDAOTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @Mock
    PersonMapper personMapper;

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
}
