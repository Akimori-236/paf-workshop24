package nus.iss.tfip.pafworkshop24.service;

import java.util.UUID;

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
    public void insertOrder(Order order) {
        // String orderId = UUID.randomUUID().toString().substring(8);
        // order.setOrderId(orderId);
        int orderId = orderRepo.insertOrder(order);
        itemRepo.insertLineItems(order.getItemList(), orderId);
    }
}
