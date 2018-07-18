package org.cycop.reg.dataobjects;

import java.time.LocalDate;

public class Registration extends DataObject {

    private Program program;
    private Person person;
    private LocalDate registrationDate;
    private LocalDate registrationCancelDate;
    private Rank rank;
    private Grade grade;
    private Address address;
    private ShirtSize shirtSize;

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getRegistrationCancelDate() {
        return registrationCancelDate;
    }

    public void setRegistrationCancelDate(LocalDate registrationCancelDate) {
        this.registrationCancelDate = registrationCancelDate;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ShirtSize getShirtSize() {
        return shirtSize;
    }

    public void setShirtSize(ShirtSize shirtSize) {
        this.shirtSize = shirtSize;
    }

}
