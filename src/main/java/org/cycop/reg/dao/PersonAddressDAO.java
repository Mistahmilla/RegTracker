package org.cycop.reg.dao;

import com.mysql.jdbc.Statement;
import org.cycop.reg.dao.mapper.AddressMapper;
import org.cycop.reg.dataobjects.Address;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    @Override
    public long set(long entityID, Address address) {

        long addressID;

        //deactivate current address if there is one
        String uSQL = "UPDATE T_PER_ADDRESS SET CURRENT_ADDRESS_I = 'N', REMOVE_D = CURRENT_DATE, UPD_T = CURRENT_TIMESTAMP WHERE CURRENT_ADDRESS_I = 'Y' AND PER_SID = ?";
        Object[] params = new Object [1];
        params[0] = entityID;
        jdbcTemplate.update(uSQL, params);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        //insert new address
        String iSQL = "INSERT INTO T_ADDRESS (STREET_ADDRESS, CITY, STATE, ZIP_CODE, CRE_T, UPD_T) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement p = connection.prepareStatement(iSQL, Statement.RETURN_GENERATED_KEYS);
                p.setString(1, address.getStreetAddress());
                p.setString(2, address.getCity());
                p.setString(3, address.getState());
                p.setString(4, address.getZipCode());
                return p;
            }
        }, keyHolder);

        addressID = keyHolder.getKey().longValue();
        String lSQL = "INSERT INTO T_PER_ADDRESS (PER_SID, ADDRESS_SID, ADD_D, CURRENT_ADDRESS_I, CRE_T, UPD_T) VALUES (?, ?, CURRENT_DATE, 'Y', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        params = new Object[2];
        params[0] = entityID;
        params[1] = addressID;
        jdbcTemplate.update(lSQL, params);

        return addressID;
    }
}
