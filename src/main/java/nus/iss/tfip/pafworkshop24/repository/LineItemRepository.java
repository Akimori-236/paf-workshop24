package nus.iss.tfip.pafworkshop24.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import nus.iss.tfip.pafworkshop24.model.LineItem;

@Repository
public class LineItemRepository {

    @Autowired
    private JdbcTemplate template;

    public int[] insertLineItems(List<LineItem> itemList, Integer orderId) {
        List<Object[]> params = itemList.stream()
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

        return template.batchUpdate(SQL.SQLInsertLineItem, params);
    }
}
