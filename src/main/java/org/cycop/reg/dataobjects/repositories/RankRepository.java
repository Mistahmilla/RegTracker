package org.cycop.reg.dataobjects.repositories;

import org.cycop.reg.dao.RankDAO;
import org.cycop.reg.dataobjects.Rank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RankRepository {

    @Autowired
    RankDAO rankDAO;

    @Cacheable("ranks")
    public List<Rank> getRank(String rankCode){
        return rankDAO.get(rankCode);
    }

    @Cacheable("ranks")
    public List<Rank> getAll(){
        return rankDAO.list();
    }
}
