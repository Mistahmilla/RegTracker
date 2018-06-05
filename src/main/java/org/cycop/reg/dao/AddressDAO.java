package org.cycop.reg.dao;

import org.cycop.reg.dataobjects.Address;

import java.util.List;

public interface AddressDAO {

    public List<Address> get(long entityID);
    public long set(long entityID, Address address);

}
