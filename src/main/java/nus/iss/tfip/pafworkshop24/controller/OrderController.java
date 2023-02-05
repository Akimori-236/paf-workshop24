package nus.iss.tfip.pafworkshop24.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import nus.iss.tfip.pafworkshop24.model.LineItem;
import nus.iss.tfip.pafworkshop24.exception.OrderException;
import nus.iss.tfip.pafworkshop24.model.Order;
import nus.iss.tfip.pafworkshop24.service.OrderService;
import nus.iss.tfip.pafworkshop24.util.OrderUtil;

@Controller
@RequestMapping(path = "/cart")
public class OrderController {

    @Autowired
    private OrderService orderSvc;

    @GetMapping
    public String getCart(Model model, HttpSession session) {
        List<LineItem> itemList = (List<LineItem>) session.getAttribute("cart");
        if (itemList == null) {
            itemList = new LinkedList<>();
            session.setAttribute("cart", itemList);
        }
        // pass list to thymeleaf
        model.addAttribute("itemList", itemList);
        return "cart";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addToCart(@Valid LineItem item, Model model, HttpSession session) {
        List<LineItem> itemList = OrderUtil.getCart(session);
        // add incoming item to item list
        itemList.add(item);

        // store new list back into sessionStorage
        session.setAttribute("cart", itemList);
        // pass list to thymeleaf
        model.addAttribute("itemList", itemList);
        return "cart";
    }

    @GetMapping(path = "/checkout")
    public String getCheckout(Model model, HttpSession session) {
        // retrieve cart from session
        List<LineItem> itemList = OrderUtil.getCart(session);
        // add list to order
        Order order = new Order();
        order.setItemList(itemList);
        // bind order to page
        model.addAttribute("order", order);
        return "checkout";
    }

    @PostMapping(path = "/checkout")
    public String Checkout(@ModelAttribute Order order, Model model, HttpSession session) throws OrderException {
        List<LineItem> itemList = OrderUtil.getCart(session);
        System.out.println("    ITEMLISTSIZE >>> " + itemList);
        order.setItemList(itemList);

        int orderId = orderSvc.insertOrder(order);
        if (orderId > 0) {
            // successful insert
            OrderUtil.resetCart(session);
            return "success";
        }
        return "checkout";
    }
}
