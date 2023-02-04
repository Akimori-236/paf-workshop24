package nus.iss.tfip.pafworkshop24.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import nus.iss.tfip.pafworkshop24.model.Order;

@Repository
public class OrderRepository {

    @Autowired
    private JdbcTemplate template;

    // returns auto_increment id of inserted order
    public Integer insertOrder(Order order) {
        KeyHolder holder = new GeneratedKeyHolder();

        template.update((PreparedStatementCreator) con -> {
            PreparedStatement ps = con.prepareStatement(SQL.SQLInsertOrder, Statement.RETURN_GENERATED_KEYS);
            // (customer_name, shipping_address, notes, tax)
            ps.setString(1, order.getCustomerName());
            ps.setString(2, order.getShippingAddress());
            ps.setString(3, order.getNotes());
            ps.setFloat(4, order.getTax());
            return ps;
        }, holder);

        // comes back as BigInteger
        return holder.getKey().intValue();
    }
}
