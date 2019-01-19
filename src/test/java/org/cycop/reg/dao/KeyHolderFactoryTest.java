package org.cycop.reg.dao;

import org.junit.Test;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class KeyHolderFactoryTest {

    @Test
    public void newKeyHolderTest(){
        KeyHolderFactory keyHolderFactory = new KeyHolderFactory();
        KeyHolder k = keyHolderFactory.newKeyHolder();
        assert(k.getClass()==GeneratedKeyHolder.class);

    }

}
