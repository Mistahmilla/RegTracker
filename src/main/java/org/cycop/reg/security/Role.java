package org.cycop.reg.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {

    private String authorityCode;
    private String authorityDescription;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Role(String authorityCode, String authorityDescription){
        this.authorityCode = authorityCode;
        this.authorityDescription = authorityDescription;
    }

    @Override
    public String getAuthority() {
        logger.info("getting authority");
        return authorityCode;
    }

    public String getAuthorityDescription(){
        return authorityDescription;
    }
}
