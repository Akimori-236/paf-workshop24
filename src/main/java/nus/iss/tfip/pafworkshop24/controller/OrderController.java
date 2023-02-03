package nus.iss.tfip.pafworkshop24.controller;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public String getCheckout(Model model, HttpSession session) {
        // retrieve cart from session
        List<LineItem> itemList = (List<LineItem>) session.getAttribute("cart");
        // bind order with list to thymeleaf
        Order order = new Order();
        order.setItemList(itemList);
        model.addAttribute("order", order);
        return "checkout";
    }

    @PostMapping(path = "/checkout")
    public String Checkout(@RequestBody MultiValueMap<String, String> cart, Model model, HttpSession session) {
        List<LineItem> itemList = (List<LineItem>) session.getAttribute("cart");

        // populate order for insertion
        Order order = new Order();
        Date orderDate = Date.valueOf(cart.getFirst("order_date"));
        String item = cart.getFirst("item");
        Integer quantity = Integer.parseInt(cart.getFirst("quantity"));
        // unitPrice
        // discount
        itemList.add(new LineItem());

        int orderId = orderSvc.insertOrder(order);

        return "checkout";
    }
}
