package org.cycop.reg.security;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public class Authority implements GrantedAuthority, Serializable {

    private String authorityCode;
    private String authorityName;
    private String authorityDescription;

    public Authority(String authorityCode, String authorityName, String authorityDescription){
        this.authorityCode = authorityCode;
        this.authorityDescription = authorityDescription;
        this.authorityName = authorityName;
    }

    @Override
    public String getAuthority() {
        return authorityCode;
    }

    public String getAuthorityName(){
        return authorityName;
    }

    public String getAuthorityDescription(){
        return authorityDescription;
    }

    @Override
    public boolean equals(Object a){
        if (a instanceof Authority) {
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
