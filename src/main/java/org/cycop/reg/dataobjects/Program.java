package org.cycop.reg.dataobjects;

import java.time.LocalDate;

public class Program extends DataObject {

    private long programID;
    private String programName;
    private LocalDate startDate;
    private LocalDate endDate;

    public Program(){

    }

    public void setProgramID(long programID){
        this.programID = programID;
    }

    public long getProgramID(){
        return programID;
    }

    public void setProgramName(String programName){
        this.programName = programName;
    }

    public String getProgramName(){
        return programName;
    }

    public void setStartDate(LocalDate startDate){
        this.startDate = startDate;
    }

    public LocalDate getStartDate(){
        return startDate;
    }

    public void setEndDate(LocalDate endDate){
        this.endDate = endDate;
    }

    public LocalDate getEndDate(){
        return endDate;
    }
}