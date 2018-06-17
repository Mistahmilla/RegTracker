package org.cycop.reg.dataobjects.repositories;

import org.cycop.reg.dao.RoleDAO;
import org.cycop.reg.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleRepository {

    @Autowired
    RoleDAO roleDAO;

    @Cacheable("roles")
    public List<Role> getRole(String roleCode){
        return roleDAO.get(roleCode);
    }

}
