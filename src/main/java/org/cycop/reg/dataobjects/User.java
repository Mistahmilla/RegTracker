package org.cycop.reg.dataobjects;

import java.util.ArrayList;
import java.util.List;

public class User extends DataObject {

    private long accountID;
    private String emailAddress;
    private String password;
    private List<Role> roles;
    private Person person;

    public User(){
        roles = new ArrayList();
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

    public void addRole(Role role){
        if (!roles.contains(role)){
            roles.add(role);
        }
    }

    public void removeRole(Role role){
        roles.remove(role);
    }

    public List<Role> getRoles(){
        return roles;
    }

    public void setPerson(Person person){
        this.person = person;
    }

    public Person getPerson(){
        return person;
    }
}
