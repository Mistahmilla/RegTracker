package org.cycop.reg.dao;

import org.cycop.reg.dao.mapper.AddressMapper;
import org.cycop.reg.dataobjects.Address;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class PersonAddressDAO implements AddressDAO {

    private JdbcTemplate jdbcTemplate;
    private AddressMapper addressMapper;

    public PersonAddressDAO(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        addressMapper = new AddressMapper();
    }

    @Override
    public List<Address> get(long entityID) {
        String sql = "SELECT T_ADDRESS.ADDRESS_SID, T_ADDRESS.STREET_ADDRESS, T_ADDRESS.CITY, T_ADDRESS.STATE, T_ADDRESS.ZIP_CODE, T_ADDRESS.CRE_T, T_ADDRESS.UPD_T" +
                " FROM T_ADDRESS INNER JOIN T_PER_ADDRESS ON T_ADDRESS.ADDRESS_SID = T_PER_ADDRESS.ADDRESS_SID WHERE T_PER_ADDRESS.CURRENT_ADDRESS_I = 'Y' AND T_PER_ADDRESS.PER_SID = ?";
        Object[] params = new Object[1];
        params[0] = entityID;
        return jdbcTemplate.query(sql, params, addressMapper);
    }
}
