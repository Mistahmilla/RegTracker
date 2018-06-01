package org.cycop.reg.dataobjects;

public class Address extends DataObject{

    private long addressID;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;

    public Address(){
        super();
    }

    public void setAddressID(long addressID){
        this.addressID = addressID;
    }

    public long getAddressID(){
        return addressID;
    }

    public void setStreetAddress(String streetAddress){
        this.streetAddress = streetAddress;
    }

    public String getStreetAddress(){
        return streetAddress;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
        return city;
    }

    public void setState(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }

    public void setZipCode(String zipCode){
        this.zipCode = zipCode;
    }

    public String getZipCode(){
        return zipCode;
    }
}
