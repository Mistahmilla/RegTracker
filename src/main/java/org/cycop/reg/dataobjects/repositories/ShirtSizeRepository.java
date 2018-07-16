package org.cycop.reg.dataobjects.repositories;

import org.cycop.reg.dao.ShirtSizeDAO;
import org.cycop.reg.dataobjects.ShirtSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShirtSizeRepository {

    @Autowired
    ShirtSizeDAO shirtSizeDAO;

    @Cacheable("shirtsizes")
    public List<ShirtSize> getShirtSizeByCode(String shirtSizeCode){
        return shirtSizeDAO.getShirtSize(shirtSizeCode);
    }

    @Cacheable("shirtsizes")
    public List<ShirtSize> getShirtSize(){
        return shirtSizeDAO.getShirtSize();
    }
}
