package org.cycop.reg.dao;

import org.cycop.reg.dao.mapper.AddressMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class ChurchAddressDAOTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @Mock
    AddressMapper addressMapper;

    @InjectMocks
    @Spy
    ChurchAddressDAO churchAddressDAO;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGet(){
        churchAddressDAO.get(1);
        ArgumentCaptor<Object[]> argument = ArgumentCaptor.forClass(Object[].class);
        verify(jdbcTemplate).query(Mockito.anyString(), argument.capture(), Mockito.any(AddressMapper.class));
        assertEquals(1, Long.valueOf(String.valueOf(argument.getValue()[0])).longValue());
    }
}
