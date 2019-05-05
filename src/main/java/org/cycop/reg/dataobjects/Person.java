package org.cycop.reg.dataobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

public class Person extends DataObject {

    private String firstName;
    private String lastName;
    @JsonFormat(pattern = "yyyy/MM/dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;
    private long personID;
    private Gender gender;
    private Address currentAddress;
    private String emailAddress;
    private String phoneNumber;

    public enum Gender {
        M("M","Male") , F("F","Female");

        private String genderDescription;
        private String genderCode;

        Gender(String code, String desc){
            this.genderDescription=desc;
            this.genderCode = code;
        }

        public String getGenderDescription(){
            return this.genderDescription;
        }

        public String getGenderCode(){
            return this.genderCode;
        }
    }

    public Person(){
        super();
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setBirthDate(LocalDate birthDate){
        this.birthDate = birthDate;
    }

    public LocalDate getBirthDate(){
        return birthDate;
    }

    public void setPersonID(long personID){
        this.personID = personID;
    }

    public long getPersonID(){
        return personID;
    }

    public String toString(){
        return lastName + ", " + firstName;
    }

    public void setGender(String gender){
        this.gender = Gender.valueOf(gender);
    }

    public Gender getGender(){
        return this.gender;
    }

    public void setCurrentAddress(Address currentAddress){
        this.currentAddress = currentAddress;
    }

    public Address getCurrentAddress(){
        return currentAddress;
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
