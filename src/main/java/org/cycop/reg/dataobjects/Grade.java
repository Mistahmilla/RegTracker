package org.cycop.reg.dataobjects;

public class Grade extends DataObject {

    private String gradeCode;
    private String gradeDescription;
    private int sortOrder;

    public Grade(){

    }

    public Grade(String gradeCode, String gradeDescription, int sortOrder){
        this();
        this.gradeCode = gradeCode;
        this.gradeDescription = gradeDescription;
        this.sortOrder = sortOrder;
    }

    public String getGradeCode(){
        return gradeCode;
    }

    public String getGradeDescription(){
        return gradeDescription;
    }

    public int getSortOrder(){
        return sortOrder;
    }
}
