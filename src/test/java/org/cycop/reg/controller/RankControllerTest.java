package org.cycop.reg.controller;

import org.cycop.reg.dataobjects.repositories.RankRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class RankControllerTest {

    @Mock
    RankRepository rankRepository;

    @InjectMocks
    RankController rankController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRankController(){
        rankController.getRank("R");
        Mockito.verify(rankRepository).getRank("R");
        rankController.getAllRanks();
        Mockito.verify(rankRepository).getAll();
    }
}
