package servlets;

import com.DreamerGp2024.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PushOrders extends HttpServlet {
    OrderServiceImpl orderService = new OrderServiceImpl();
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession session = req.getSession();
        String orderID = req.getParameter("orderID");
        orderService.attachManagerToOrderByID(orderID, Integer.parseInt(session.getAttribute("userID").toString()));
        orderService.pushOrderStatusByID(orderID);
    }
}
