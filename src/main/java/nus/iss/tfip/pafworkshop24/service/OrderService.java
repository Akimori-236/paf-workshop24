package nus.iss.tfip.pafworkshop24.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nus.iss.tfip.pafworkshop24.repository.OrderRepository;
import nus.iss.tfip.pafworkshop24.exception.OrderException;
import nus.iss.tfip.pafworkshop24.model.Order;
import nus.iss.tfip.pafworkshop24.repository.LineItemRepository;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepo;
    @Autowired
    LineItemRepository itemRepo;

    @Transactional(rollbackFor = OrderException.class)
    public int insertOrder(Order order) throws OrderException {
        System.out.println("*** TXN >>> START ***");

        // HEXA ID (not using)
        // String orderId = UUID.randomUUID().toString().substring(8);
        // order.setOrderId(orderId);

        // TODO: add back date insertion (currently on DATETIME.NOW())
        // https://stackoverflow.com/questions/16645724/how-to-insert-date-into-mysql-database-table-in-java
        // initialise util.date then convert into sql.date
        // Date d = new Date();
        // java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        // get the auto increment orderID
        System.out.println("*** TXN >>> START ORDER INSERT ***");
        final int orderId = orderRepo.insertOrder(order);
        System.out.println("*** TXN >>> END ORDER INSERT ***");
        System.out.println("ORDER SIZE > " + order.getItemList().size());
        if (order.getItemList().size() > 3) {
            OrderException oe = new OrderException("Maximum 3 types of items per order");
            throw oe;
        } else if (order.getItemList().size() == 0) {
            OrderException oe = new OrderException("Need to order something");
            throw oe;
        }
        System.out.println("*** TXN >>> START BATCH ITEM INSERT ***");
        itemRepo.batchInsertLineItems(order.getItemList(), orderId);
        System.out.println("*** TXN >>> END BATCH ITEM INSERT ***");
        System.out.println("*** TXN >>> END ***");
        return orderId;
    }
}
