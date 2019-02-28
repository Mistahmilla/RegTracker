package org.cycop.reg.dataobjects;

public class Church {

    private long churchID;
    private String churchName;
    private Address address;
    private String emailAddress;
    private String phoneNumber;
    private Person pastor;

    public Church(){
        super();
    }

    public void setChurchID(long churchID){
        this.churchID = churchID;
    }

    public long getChurchID(){
        return churchID;
    }

    public void setChurchName(String churchName){
        this.churchName = churchName;
    }

    public String getChurchName(){
        return churchName;
    }

    public void setAddress(Address address){
        this.address = address;
    }

    public Address getAddress(){
        return address;
    }

    public void setPastor(Person pastor){
        this.pastor = pastor;
    }

    public Person getPastor(){
        return pastor;
    }

    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress(){
        return emailAddress;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }
}
