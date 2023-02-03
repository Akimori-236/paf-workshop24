package nus.iss.tfip.pafworkshop24.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineItem {
    private Integer itemId;
    private String product;
    private Float unitPrice;
    private Float discount;
    private Integer quantity;
    private String orderId;

    public static LineItem create(String product, Float unitPrice, Float discount, int quantity) {
        LineItem li = new LineItem();
        li.setProduct(product);
        li.setUnitPrice(unitPrice);
        li.setDiscount(discount);
        li.setQuantity(quantity);
        return li;
    }
}
