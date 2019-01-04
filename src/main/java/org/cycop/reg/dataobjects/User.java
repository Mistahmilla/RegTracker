package org.cycop.reg.dataobjects;

import java.util.ArrayList;
import java.util.List;

public class User extends DataObject {

    private long accountID;
    private String emailAddress;
    private String password;
    private String salt;
    private List<Role> roles;
    private List<Permission> permissions;
    private Person person;
    private boolean accountLocked;
    private boolean accountVerified;
    private boolean passwordExpired;

    public User(){
        roles = new ArrayList();
        permissions = new ArrayList();
    }

    public long getAccountID(){
        return accountID;
    }

    public void setAccountID(long accountID){
        this.accountID = accountID;
    }

    public String getEmailAddress(){
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getSalt(){
        return salt;
    }

    public void setSalt(String salt){
        this.salt = salt;
    }

    public void addRole(Role role){
        if (!roles.contains(role)){
            roles.add(role);
        }
    }

    public void removeRole(Role role){
        roles.remove(role);
    }

    public void removePermission(Permission p){
        permissions.remove(p);
    }

    public List<Role> getRoles(){
        return roles;
    }

    public void addPermission(Permission p){
        if(!permissions.contains(p)){
            permissions.add(p);
        }
    }

    public List<Permission> getPermissions(){
        return permissions;
    }

    public void setPerson(Person person){
        this.person = person;
    }

    public Person getPerson(){
        return person;
    }

    public boolean getAccountLocked(){
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked){
        this.accountLocked = accountLocked;
    }

    public boolean getAccountVerified(){
        return accountVerified;
    }

    public void setAccountVerified(boolean accountVerified){
        this.accountVerified = accountVerified;
    }

    public boolean getPasswordExpired(){
        return passwordExpired;
    }

    public void setPasswordExpired(boolean passwordExpired){
        this.passwordExpired = passwordExpired;
    }
}
