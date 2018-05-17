package org.cycop.reg.dataobjects;

import java.time.LocalDate;

public class Person extends DataObject{

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Long personID;
    private Gender gender;

    public enum Gender {
        M("Male") , F("Female");

        private String genderDescription;

        Gender(String desc){
            this.genderDescription=desc;
        }

        public String getGenderDescription(){
            return this.genderDescription;
        }
    }

    public Person(){
    }

    public void setFirstName(String FirstName){
        firstName = FirstName;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setLastName(String LastName){
        lastName = LastName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setBirthDate(LocalDate BirthDate){
        birthDate = BirthDate;
    }

    public LocalDate getBirthDate(){
        return birthDate;
    }

    public void setPersonID(Long PersonID){
        this.personID = PersonID;
    }

    public Long getPersonID(){
        return personID;
    }

    public String toString(){
        return lastName + ", " + firstName;
    }

    public void setGender(Gender gender){
        this.gender = gender;
    }

    public Gender getGender(){
        return this.gender;
    }
}
