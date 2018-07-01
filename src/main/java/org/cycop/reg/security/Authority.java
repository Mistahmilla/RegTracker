package org.cycop.reg.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

    private String authorityCode;
    private String authorityName;
    private String authorityDescription;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Authority(String authorityCode, String authorityName, String authorityDescription){
        this.authorityCode = authorityCode;
        this.authorityDescription = authorityDescription;
        this.authorityName = authorityName;
    }

    @Override
    public String getAuthority() {
        return authorityCode;
    }

    public String getAuthorityDescription(){
        return authorityDescription;
    }

    @Override
    public boolean equals(Object a){
        if (a != null && a instanceof Authority) {
            Authority auth = (Authority) a;
            return auth.getAuthority().equals(this.authorityCode);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return authorityCode.hashCode();
    }
}
