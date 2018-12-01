package org.cycop.reg.controller;

import org.cycop.reg.dataobjects.repositories.ShirtSizeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ShirtSizeControllerTest {

    @Mock
    ShirtSizeRepository shirtSizeRepository;

    @InjectMocks
    ShirtSizeController shirtSizeController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShirtSizeController(){
        shirtSizeController.getShirtSize("S");
        Mockito.verify(shirtSizeRepository).getShirtSizeByCode("S");
        shirtSizeController.getAll();
        Mockito.verify(shirtSizeRepository).getShirtSize();
    }


}
