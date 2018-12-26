package org.cycop.reg.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Account implements UserDetails {

    private long accountID;
    private String emailAddress;
    private String password;
    private String passwordSalt;
    private boolean passwordExpired;
    private boolean accountVerified;
    private boolean accountLocked;
    private List<Authority> authorities;

    public Account(){
        authorities = new ArrayList();
    }

    public void setAccountID(long accountID){
        this.accountID = accountID;
    }

    public long getAccountID(){
        return accountID;
    }

    @Override
    public Collection<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities){
        this.authorities = authorities;
    }

    public void addAuthority(Authority authority){
        if(!authorities.contains(authority)) {
            authorities.add(authority);
        }
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password){
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

        return !(accountLocked || !accountVerified || passwordExpired);

    }
}
