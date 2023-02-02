package nus.iss.tfip.pafworkshop24.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import nus.iss.tfip.pafworkshop24.model.Order;

@Repository
public class OrderRepository {
        public static final String insertOrderSQL = """
                        INSERT INTO order(order_date, customer_name, shipping_address, notes, tax)
                        VALUES(?, ?, ?, ?, ?)
                        """;
       
        @Autowired
        private JdbcTemplate template;
        @Autowired
        KeyHolder holder;

        public int insertOrder(Order order) {
                template.update(new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS);
                        ps.setDate(1, order.getOrderDate());
                        ps.setString(2, order.getCustomerName());
                        ps.setString(3, order.getShippingAddress());
                        ps.setString(4, order.getNotes());
                        ps.setDouble(5, order.getTax());
                        return ps;
                    }
                }, holder);
                return holder.getKey().intValue();
            }
            
}
