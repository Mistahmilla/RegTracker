package org.cycop.reg.dao;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class KeyHolderFactory {

    public KeyHolder newKeyHolder() {
        return new GeneratedKeyHolder();
    }

}
