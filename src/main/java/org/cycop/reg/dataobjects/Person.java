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

    public void setPersonID(Long personID){
        this.personID = personID;
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
