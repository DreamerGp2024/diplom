package servlets;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

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
            List<Order> realOrders = new ArrayList<>();
            Set<Integer> orderIDs = new HashSet<>();
            for (Order order : orders) {
                orderIDs.add(order.getOrderID());
            }
            for (Integer orderID : orderIDs) {
                List<String> barcodes = new ArrayList<>();
                List<Integer> qtys = new ArrayList<>();
                double total = 0.0;
                for (Order order : orders) {
                    if (order.getOrderID() == orderID) {
                        barcodes.add(order.getBarcode().get(0));
                        qtys.add(order.getQuantity().get(0));
                        total += order.getTotal();
                    }
                }
                for (Order order : orders) {
                    if (order.getOrderID() == orderID) {
                        realOrders.add(new Order( order.getOrderID(),order.getCustomer(), barcodes, order.getPrice(), qtys, price, order.getManager(), order.getStatus() ));
                        break;
                    }
                }
            }
            pw.println("""
                    <script>
                            function submitForm(event) {
                                event.preventDefault(); // Предотвращаем стандартное поведение формы
                                        
                                var xhr = new XMLHttpRequest();
                                xhr.open("POST", "pushOrders", true);
                                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                                        
                                xhr.onreadystatechange = function() {
                                    if (xhr.readyState === 4 && xhr.status === 200) {
                                        document.getElementById("response").innerHTML = xhr.responseText;
                                    }
                                };
                                        
                                var form = event.target.closest('form');
                                var orderID = form.querySelector('input[name="orderID"]').value;
                                        
                                var params = "orderID=" + encodeURIComponent(orderID);
                                xhr.send(params);
                                location.reload();
                            }
                        </script>
                    """);
            pw.println("<div id='topmid' style='background-color:grey'>Orders</div>");
            pw.println("<table class=\"table table-hover\" style='background-color:white'>\r\n"
                    + "  <thead>\r\n"
                    + "    <tr style='background-color:black; color:white;'>\r\n"
                    + "      <th scope=\"col\">OrderID</th>\r\n"
                    + "      <th scope=\"col\">Customer</th>\r\n"
                    + "      <th scope=\"col\">Books</th>\r\n"
                    + "      <th scope=\"col\">Total</th>\r\n"
                    + "      <th scope=\"col\">Manager</th>\r\n"
                    + "      <th scope=\"col\">Status</th>\r\n"
                    + "      <th scope=\"col\">Action</th>\r\n"
                    + "    </tr>\r\n"
                    + "  </thead>\r\n"
                    + "  <tbody>\r\n");
            if (orders == null || orders.isEmpty()) {
                pw.println("    <tr style='background-color:green'>\r\n"
                        + "      <th scope=\"row\" colspan='6' style='color:yellow; text-align:center;'> No Active Orders in the store </th>\r\n"
                        + "    </tr>\r\n");
            }
            for (Order order : realOrders) {
                Random random = new Random();
                int id = random.nextInt(Integer.MAX_VALUE);
                pw.println(getRowData(order, id));
            }

            pw.println("  </tbody>\r\n"
                    + "</table></div>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getRowData(Order order, int id) {
        String books = "";
        for (int i = 0; i < order.getBarcode().size(); i++) {
            books += order.getBarcode().get(i) + " - " + order.getQuantity().get(i) + "pcs.<br>";
        }
        try {
            return "    <tr>\r\n"
                    + "      <th scope=\"row\">" + order.getOrderID() + "</th>\r\n"
                    + "      <td>" + order.getStatus().name() + "</td>\r\n"
                    + "      <td>" + userService.getNameByUserID(order.getCustomer()) + "</td>\r\n"
                    + "      <td>" + books + "</td>\r\n"
                    + "      <td>" + order.getPrice() + "</td>\r\n"
                    + "      <td>" + userService.getNameByUserID(order.getManager()) + "</td>\r\n"
                    + "      <td><form onsubmit=\"submitForm(event)\">"
                    + "          <input type='hidden' id='" + id +"' name='orderID' value='" + order.getOrderID() + "'/>"
                    + "          <button type='submit' id='" + id +"' class=\"btn btn-success\">"+ order.getStatus().toString() +"</button>"
                    + "          </form>"
                    + "    </tr>\r\n";
        } catch (StoreException ignored) {

        }
        return "";
    }
}
