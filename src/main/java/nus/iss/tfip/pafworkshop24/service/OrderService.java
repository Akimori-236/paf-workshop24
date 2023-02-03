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
        // String orderId = UUID.randomUUID().toString().substring(8);
        // order.setOrderId(orderId);

        // get the auto increment orderID
        final int orderId = orderRepo.insertOrder(order);
        
        if (order.getItemList().size() > 3){
             OrderException oe = new OrderException("Maximum 3 types of items per order");
             throw oe;
        }
        itemRepo.insertLineItems(order.getItemList(), orderId);
        return orderId;
    }
}
