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

public class UserOrderServlet extends HttpServlet {

    OrderServiceImpl orderService = new OrderServiceImpl();
    UserServiceImpl userService = new UserServiceImpl();

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        if (!StoreUtil.isLoggedIn(UserRole.CUSTOMER, req.getSession())) {
            RequestDispatcher rd = req.getRequestDispatcher("CustomerLogin.html");
            rd.include(req, res);
            pw.println("<table class=\"tab\"><tr><td>Please Login First to Continue!!</td></tr></table>");
            return;
        }
        try {

            RequestDispatcher rd = req.getRequestDispatcher("CustomerHome.html");
            rd.include(req, res);
            pw.println("<div class='container'>");

            StoreUtil.setActiveTab(pw, "userorders");
            HttpSession session = req.getSession();
            int userID = Integer.parseInt(session.getAttribute("userID").toString());

            List<Order> orders = orderService.getOrders();
            List<Order> realOrders = new ArrayList<>();
            Set<Integer> orderIDs = new HashSet<>();
            for (Order order : orders) {
                if (order.getCustomer() == userID) {
                    orderIDs.add(order.getOrderID());
                }
            }
            for (Integer orderID : orderIDs) {
                List<String> barcodes = new ArrayList<>();
                List<Integer> qtys = new ArrayList<>();
                List<Double> prices = new ArrayList<>();
                double finishTotal = 0;
                for (Order order : orders) {
                    if (order.getOrderID() == orderID) {
                        barcodes.add(order.getBarcode().get(0));
                        qtys.add(order.getQuantity().get(0));
                        prices.add(order.getPrice().get(0));
                        finishTotal += order.getTotal();
                    }
                }
                for (Order order : orders) {
                    if (order.getOrderID() == orderID) {
                        realOrders.add(new Order( order.getOrderID(),order.getCustomer(), barcodes, prices, qtys, finishTotal, order.getManager(), order.getStatus() ));
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
                    + "      <th scope=\"col\">Books</th>\r\n"
                    + "      <th scope=\"col\">Prices</th>\r\n"
                    + "      <th scope=\"col\">Quantities</th>\r\n"
                    + "      <th scope=\"col\">Total</th>\r\n"
                    + "      <th scope=\"col\">Manager</th>\r\n"
                    + "      <th scope=\"col\">Status</th>\r\n"
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
            books += order.getBarcode().get(i) +"<br>";
        }
        String prices = "";
        for (int i = 0; i < order.getPrice().size(); i++) {
            prices += order.getPrice().get(i) + "<br>";
        }
        String quants = "";
        for (int i = 0; i < order.getQuantity().size(); i++) {
            quants += order.getQuantity().get(i) + " pcs.<br>";
        }
        double total=order.getTotal();

        try {
            return
                    "    <tr>\r\n"
                    + "      <th scope=\"row\">" + order.getOrderID() + "</th>\r\n"
                    + "      <td>" + books + "</td>\r\n"
                    + "      <td>" + prices + "</td>\r\n"
                    + "      <td>" + quants + "</td>\r\n"
                    + "      <td>" + total + "</td>\r\n"
                    + "      <td>" + userService.getFIOByUserID(order.getManager()) + "</td>\r\n"
                    + "      <td>" + order.getStatus().name() + "</td>\r\n"
                    + "          </form>"
                    + "    </tr>\r\n";
        } catch (StoreException ignored) {

        }
        return "";
    }
}
