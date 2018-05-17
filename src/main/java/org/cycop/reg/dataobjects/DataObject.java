package org.cycop.reg.dataobjects;

import java.time.LocalDate;

public abstract class DataObject {

    private LocalDate createTime;
    private LocalDate updateTime;

    public LocalDate getCreateTime(){
        return createTime;
    }

    public LocalDate getUpdateTime(){
        return updateTime;
    }
}
