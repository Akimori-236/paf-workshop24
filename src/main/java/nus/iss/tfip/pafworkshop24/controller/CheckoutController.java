package nus.iss.tfip.pafworkshop24.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import nus.iss.tfip.pafworkshop24.exception.OrderException;
import nus.iss.tfip.pafworkshop24.model.LineItem;
import nus.iss.tfip.pafworkshop24.service.OrderService;

@Controller
@RequestMapping(path = "/checkout")
public class CheckoutController {
    @Autowired
    private OrderService orderSvc;

    @PostMapping
    public String confirmCheckout(Model model, HttpSession session) throws OrderException {
        List<LineItem> itemList = (List<LineItem>) session.getAttribute("cart");

        model.addAttribute("total", itemList.size());

        return "checkout";
    }
}
