package nus.iss.tfip.pafworkshop24.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import nus.iss.tfip.pafworkshop24.model.LineItem;
import nus.iss.tfip.pafworkshop24.model.Order;
import nus.iss.tfip.pafworkshop24.service.OrderService;

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
        List<LineItem> itemList = (List<LineItem>) session.getAttribute("cart");
        if (itemList == null) {
            itemList = new LinkedList<>();
            session.setAttribute("cart", itemList);
        }
        // add incoming item to item list
        itemList.add(item);

        // store new list back into sessionStorage
        session.setAttribute("cart", itemList);
        // pass list to thymeleaf
        model.addAttribute("itemList", itemList);
        return "cart";
    }

    @GetMapping(path = "/checkout")
    public String getCheckout(@Valid Order order, Model model, HttpSession session) {
        // retrieve cart from session
        List<LineItem> itemList = (List<LineItem>) session.getAttribute("cart");
        // bind order with list to thymeleaf
        order.setItemList(itemList);
        model.addAttribute("order", order);
        return "checkout";
    }

    @PostMapping(path = "/checkout")
    public String Checkout(@Valid Order order, Model model, HttpSession session) {
        System.out.println("    ITEMLISTSIZE >>> " + order.getItemList().size());
        // populate order for insertion
        order.setOrderDate(Date.valueOf(LocalDate.now()));
        // order.setCustomerName(form.getFirst("name"));
        // order.setShippingAddress(form.getFirst("shippingAddress"));
        // order.setNotes(form.getFirst("notes"));
        // order.setTax(Float.parseFloat(form.getFirst("tax")));
        
        int orderId = orderSvc.insertOrder(order);
        if (orderId > 0) {
            return "success";
        }
        return "checkout";
    }
}
