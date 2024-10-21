package servlets;

import com.DreamerGp2024.model.Book;
import com.DreamerGp2024.model.Order;
import com.DreamerGp2024.model.StoreException;
import com.DreamerGp2024.model.UserRole;
import com.DreamerGp2024.service.impl.OrderServiceImpl;
import com.DreamerGp2024.service.impl.UserServiceImpl;
import com.DreamerGp2024.util.StoreUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class OrderServlet extends HttpServlet {

    OrderServiceImpl orderService = new OrderServiceImpl();
    UserServiceImpl userService = new UserServiceImpl();

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        if (!StoreUtil.isLoggedIn(UserRole.MANAGER, req.getSession())) {
            RequestDispatcher rd = req.getRequestDispatcher("SellerLogin.html");
            rd.include(req, res);
            pw.println("<table class=\"tab\"><tr><td>Please Login First to Continue!!</td></tr></table>");
            return;
        }
        try {

            RequestDispatcher rd = req.getRequestDispatcher("SellerHome.html");
            rd.include(req, res);
            pw.println("<div class='container'>");

            StoreUtil.setActiveTab(pw, "orders");

            List<Order> orders = orderService.getOrders();
            pw.println("<div id='topmid' style='background-color:grey'>Orders</div>");
            pw.println("<table class=\"table table-hover\" style='background-color:white'>\r\n"
                    + "  <thead>\r\n"
                    + "    <tr style='background-color:black; color:white;'>\r\n"
                    + "      <th scope=\"col\">OrderID</th>\r\n"
                    + "      <th scope=\"col\">Status</th>\r\n"
                    + "      <th scope=\"col\">Customer</th>\r\n"
                    + "      <th scope=\"col\">Books</th>\r\n"
                    + "      <th scope=\"col\">Price</th>\r\n"
                    + "      <th scope=\"col\">Manager</th>\r\n"
                    + "      <th scope=\"col\">Action</th>\r\n"
                    + "    </tr>\r\n"
                    + "  </thead>\r\n"
                    + "  <tbody>\r\n");
            if (orders == null || orders.isEmpty()) {
                pw.println("    <tr style='background-color:green'>\r\n"
                        + "      <th scope=\"row\" colspan='6' style='color:yellow; text-align:center;'> No Active Orders in the store </th>\r\n"
                        + "    </tr>\r\n");
            }
            for (Order order : orders) {
                pw.println(getRowData(order));
            }

            pw.println("  </tbody>\r\n"
                    + "</table></div>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getRowData(Order order) {
        try {
            String books;
//        for(Book book: order.getBarcode())
            return "    <tr>\r\n"
                    + "      <th scope=\"row\">" + order.getOrderID() + "</th>\r\n"
                    + "      <td>" + order.getStatus().name() + "</td>\r\n"
                    + "      <td>" + userService.getNameByUserID(order.getCustomer()) + "</td>\r\n"
                    + "      <td>" + order.getBarcode() + "</td>\r\n"
                    + "      <td>" + order.getPrice() + "</td>\r\n"
                    + "      <td>" + userService.getNameByUserID(order.getManager()) + "</td>\r\n"
                    + "      <td><form method='post' action='updatebook'>"
                    + "          <input type='hidden' name='bookId' value='" + order.getBarcode() + "'/>"
                    + "          <button type='submit' class=\"btn btn-success\">Update</button>"
                    + "          </form>"
                    + "    </tr>\r\n";
        }
        catch (StoreException ignored) {

        }
        return "";
    }
}
