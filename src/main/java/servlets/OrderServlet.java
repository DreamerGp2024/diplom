package servlets;

import com.DreamerGp2024.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderServlet {

    OrderServiceImpl orderService = new OrderServiceImpl();

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

    }
}
