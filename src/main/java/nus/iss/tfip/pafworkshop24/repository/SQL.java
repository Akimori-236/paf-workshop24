package nus.iss.tfip.pafworkshop24.repository;

public interface SQL {

    public static final String SQLInsertLineItem = """
            INSERT INTO line_item(product, unit_price, discount, quantity, order_id)
            VALUES(?, ?, ?, ?, ?)
            """;

    public static final String SQLInsertOrder = """
            INSERT INTO order(order_date, customer_name, shipping_address, notes, tax)
            VALUES(?, ?, ?, ?, ?)
            """;
}
