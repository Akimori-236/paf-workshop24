package nus.iss.tfip.pafworkshop24.repository;

import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import nus.iss.tfip.pafworkshop24.exception.OrderException;
import nus.iss.tfip.pafworkshop24.model.LineItem;

@Repository
public class LineItemRepository {

    @Autowired
    private JdbcTemplate template;

    public int[] batchInsertLineItems(List<LineItem> itemList, Integer orderId) throws OrderException {
        // List<Object[]> params = itemList.stream()
        // .map(item -> {
        // Object[] obj = new Object[6];
        // obj[0] = item.getProduct();
        // obj[1] = item.getUnitPrice();
        // obj[2] = item.getDiscount();
        // obj[3] = item.getQuantity();
        // obj[4] = orderId;
        // return obj;
        // })
        // .toList();
        return template.batchUpdate(SQL.SQLInsertLineItem,
                (BatchPreparedStatementSetter) new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        // inject variables into the SQL statement
                        ps.setString(1, itemList.get(i).getProduct());
                        ps.setFloat(2, itemList.get(i).getUnitPrice());
                        ps.setFloat(3, itemList.get(i).getDiscount());
                        ps.setInt(4, itemList.get(i).getQuantity());
                        ps.setInt(5, orderId);
                    }

                    public int getBatchSize() {
                        return itemList.size();
                    }
                });
    }
}
