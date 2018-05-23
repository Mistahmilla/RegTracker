package org.cycop.reg.dataobjects;

public class Rank extends DataObject {

    private String rankCode;
    private String rankDescription;
    private int sortOrder;
    private String nextRankCode;

    public Rank(String rankCode, String rankDescription, String nextRankCode, int sortOrder){
        this.rankCode = rankCode;
        this.rankDescription = rankDescription;
        this.nextRankCode = nextRankCode;
        this.sortOrder = sortOrder;
    }

    public String getRankCode(){
        return rankCode;
    }

    public String getRankDescription(){
        return rankDescription;
    }

    public String getNextRankCode(){
        return nextRankCode;
    }

    public int getSortOrder(){
        return sortOrder;
    }
}
