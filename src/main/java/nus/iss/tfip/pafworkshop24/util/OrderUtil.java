package nus.iss.tfip.pafworkshop24.util;

import java.util.LinkedList;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import nus.iss.tfip.pafworkshop24.model.LineItem;

public class OrderUtil {
    public static List<LineItem> getCart(HttpSession session) {
        List<LineItem> itemList = (List<LineItem>) session.getAttribute("cart");
        if (itemList == null) {
            itemList = new LinkedList<>();
            session.setAttribute("cart", itemList);
        }
        return itemList;
    }
}
