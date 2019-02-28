package org.cycop.reg.controller;

import org.cycop.reg.dao.ChurchDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

public class ChurchControllerTest {

    @Mock
    ChurchDAO churchDAO;

    @Spy
    @InjectMocks
    ChurchController churchController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testChurchSearch(){
        churchController.churchSearch(1, "", "", "");
        Mockito.verify(churchDAO).get(1, "", "", "");
        churchController.churchSearch(0, "test", "", "");
        Mockito.verify(churchDAO).get(0, "test", "", "");
        churchController.churchSearch(0, "test", "test", "");
        Mockito.verify(churchDAO).get(0, "test", "test", "");
        churchController.churchSearch(0, "test", "test", "test");
        Mockito.verify(churchDAO).get(0, "test", "test", "test");

        churchController.getChurch(1);
        Mockito.verify(churchDAO, Mockito.times(2)).get(1, "", "", "");
    }
}
