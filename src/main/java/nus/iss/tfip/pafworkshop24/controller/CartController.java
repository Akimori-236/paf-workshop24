package nus.iss.tfip.pafworkshop24.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import nus.iss.tfip.pafworkshop24.model.LineItem;

@Controller
@RequestMapping(path = "/cart")
public class CartController {
    
    @PostMapping
    public String postCart(@RequestBody MultiValueMap<String, String> cart, Model model, HttpSession session) {
        List<LineItem> itemList = (List<LineItem>) session.getAttribute("cart");

        if (itemList == null) {
            itemList = new LinkedList<>();
            session.setAttribute("cart", itemList);
        }

        String item = cart.getFirst("item");
        Integer quantity = Integer.parseInt(cart.getFirst("quantity"));
        // unitPrice
        // discount
        itemList.add(new LineItem());
        return "cart";
    }
}
