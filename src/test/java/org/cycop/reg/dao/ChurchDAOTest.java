package org.cycop.reg.dao;

import org.cycop.reg.dao.mapper.ChurchMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ChurchDAOTest {

    @Mock
    ChurchMapper churchMapper;

    @Mock
    JdbcTemplate jdbcTemplate;

    @Spy
    @InjectMocks
    ChurchDAO churchDAO;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGet(){
        churchDAO.get(1, "", "", "");
        ArgumentCaptor<Object[]> argument = ArgumentCaptor.forClass(Object[].class);
        verify(jdbcTemplate).query(Mockito.anyString(), argument.capture(), Mockito.any(ChurchMapper.class));
        assertEquals(1, Long.valueOf(String.valueOf(argument.getValue()[0])).longValue());

        churchDAO.get(0, "test", "test2", "test3");
        argument = ArgumentCaptor.forClass(Object[].class);
        verify(jdbcTemplate, times(2)).query(Mockito.anyString(), argument.capture(), Mockito.any(ChurchMapper.class));
        assertEquals("test", argument.getValue()[0]);
        assertEquals("test2", argument.getValue()[1]);
        assertEquals("test3", argument.getValue()[2]);
    }

}
