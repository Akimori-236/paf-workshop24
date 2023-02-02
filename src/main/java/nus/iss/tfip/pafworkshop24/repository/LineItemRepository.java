package nus.iss.tfip.pafworkshop24.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import nus.iss.tfip.pafworkshop24.model.LineItem;
import nus.iss.tfip.pafworkshop24.model.Order;

@Repository
public class LineItemRepository {

    public static final String insertLineItemSQL = """
            INSERT INTO order(product, unit_price, discount, quantity, order_id)
            VALUES(?, ?, ?, ?, ?)
            """;

    @Autowired
    private JdbcTemplate template;

    public void insertLineItem(Order order) {

    }

    public void insertLineItems(List<LineItem> itemList, Integer orderId) {
        List<Object[]> arrData = itemList.stream()
                .map(item -> {
                    Object[] obj = new Object[6];
                    obj[0] = item.getProduct();
                    obj[1] = item.getUnitPrice();
                    obj[2] = item.getDiscount();
                    obj[3] = item.getQuantity();
                    obj[4] = item.getOrderId();
                    return obj;
                })
                .toList();

        template.batchUpdate(insertLineItemSQL, arrData);
    }
}
