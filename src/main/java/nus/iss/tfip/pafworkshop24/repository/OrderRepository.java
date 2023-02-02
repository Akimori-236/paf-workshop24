package nus.iss.tfip.pafworkshop24.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

        public int insertOrder(Order order) {
                return template.update(insertOrderSQL,
                                order.getOrderDate(),
                                order.getCustomerName(),
                                order.getShippingAddress(),
                                order.getNotes(),
                                order.getTax());
        }
}
