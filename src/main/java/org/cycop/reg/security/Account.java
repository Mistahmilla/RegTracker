package org.cycop.reg.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class Account implements UserDetails {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private long accountID;
    private String emailAddress;
    private String password;
    private String passwordSalt;
    private boolean passwordExpired;
    private boolean accountVerified;
    private boolean accountLocked;
    private List<Role> authorities;

    public void setAccountID(long accountID){
        this.accountID = accountID;
    }

    public long getAccountID(){
        return accountID;
    }

    @Override
    public Collection<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Role> authorities){
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        logger.info("getting password");
        return password;
    }

    public void setPassword(String password){
        logger.info("password: "+password);
        this.password = password;
    }

    public void setPasswordSalt(String passwordSalt){
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordSalt(){
        return passwordSalt;
    }

    @Override
    public String getUsername() {
        return emailAddress;
    }

    public void setUsername(String emailAddress){
        this.emailAddress = emailAddress;
    }

    public void setPasswordExpired(boolean passwordExpired){
        this.passwordExpired = passwordExpired;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountLocked;
    }

    public void setAccountLocked(boolean accountLocked){
        this.accountLocked = accountLocked;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !passwordExpired;
    }

    public void setAccountVerified(boolean accountVerified){
        this.accountVerified = accountVerified;
    }

    @Override
    public boolean isEnabled() {
        logger.info("accountLocked: "+accountLocked);
        logger.info("accountVerified: "+accountVerified);
        logger.info("passwordExpired: "+passwordExpired);
        if (accountLocked == true || accountVerified == false || passwordExpired == true){
            return false;
        }
        return true;
    }
}
