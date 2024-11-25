package servlets;

import com.DreamerGp2024.constant.BookStoreConstants;
import com.DreamerGp2024.model.Post;
import com.DreamerGp2024.model.UserRole;
import com.DreamerGp2024.service.impl.PostServiceImpl;
import com.DreamerGp2024.util.StoreUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class AddNewsServlet extends HttpServlet {
    PostServiceImpl postService = new PostServiceImpl();

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType(BookStoreConstants.CONTENT_TYPE_TEXT_HTML);

        if (!StoreUtil.isLoggedIn(UserRole.MANAGER, req.getSession())) {
            RequestDispatcher rd = req.getRequestDispatcher("SellerLogin.html");
            rd.include(req, res);
            pw.println("<table class=\"tab\"><tr><td>Please Login As Admin To Continue!!</td></tr></table>");
            return;
        }

        String postHeader = req.getParameter("postHeader");
        RequestDispatcher rd = req.getRequestDispatcher("SellerHome.html");
        rd.include(req, res);
        StoreUtil.setActiveTab(pw, "addpost");
        pw.println("<div style=\"align-items: center;display: flex;justify-content: center;\">");
        if (postHeader == null || postHeader.isBlank()) {
            //render the add book form;
            showAddBookForm(pw);
            return;
        } //else process the add book


        try {
            HttpSession session = req.getSession();
            Random random = new Random();
            int postID = random.nextInt(Integer.MAX_VALUE);
            int postAuthor = Integer.parseInt(session.getAttribute("userID").toString());
            postHeader = req.getParameter("postHeader");
            String postBody = req.getParameter("postBody");

            Post post = new Post(postID, postAuthor, System.currentTimeMillis() / 1000L, postHeader, postBody);
            postService.addPost(post);
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<table class=\"tab\"><tr><td>Failed to Add Post! Fill up CareFully</td></tr></table>");
        }
    }

    private static void showAddBookForm(PrintWriter pw) {
        String form = """
                <div class="form-container">
                     <form action="addpost" method="post">
                        <label for="postHeader">Header : </label> <input type="text" name="postHeader" id="postHeader" placeholder="Enter posts header" required><br/>
                        <label for="postBody">Body : </label><input type="text" name="postBody" id="postBody" placeholder="Enter posts body" required><br/>
                        <input class="btn btn-success my-2" type="submit" value="Add Post">
                     </form>
                </div>
                """;
        pw.println(form);
    }
}
