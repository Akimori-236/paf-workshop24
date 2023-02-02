package nus.iss.tfip.pafworkshop24.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineItem {
    private Integer itemId;
    private String product;
    private BigDecimal unitPrice;
    private BigDecimal discount;
    private Integer quantity;
    private String orderId;
}
