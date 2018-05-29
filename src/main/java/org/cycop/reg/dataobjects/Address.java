package org.cycop.reg.dataobjects;

public class Address extends DataObject{

    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;

    public Address(){
        super();
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
