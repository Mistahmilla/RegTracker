package org.cycop.reg.dataobjects;

public class ShirtSize extends DataObject{

    private String shirtSizeCode;
    private String shirtSizeDescription;
    private int sortOrder;

    public ShirtSize(){

    }

    public String getShirtSizeCode() {
        return shirtSizeCode;
    }

    public void setShirtSizeCode(String shirtSizeCode) {
        this.shirtSizeCode = shirtSizeCode;
    }

    public String getShirtSizeDescription() {
        return shirtSizeDescription;
    }

    public void setShirtSizeDescription(String shirtSizeDescription) {
        this.shirtSizeDescription = shirtSizeDescription;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

}
