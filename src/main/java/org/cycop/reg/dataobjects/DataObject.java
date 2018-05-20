package org.cycop.reg.dataobjects;

import java.time.LocalDateTime;

public abstract class DataObject {

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public LocalDateTime getCreateTime(){
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime){
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime(){
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime){
        this.updateTime = updateTime;
    }
}
