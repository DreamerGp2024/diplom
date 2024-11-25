package servlets;

import com.DreamerGp2024.model.Comment;
import com.DreamerGp2024.service.CommentService;
import com.DreamerGp2024.service.UserService;
import com.DreamerGp2024.service.impl.CommentServiceImpl;
import com.DreamerGp2024.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

public class CommentsServlet extends HttpServlet {

    private final CommentService commentService = new CommentServiceImpl();

    UserService userService = new UserServiceImpl();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String title = request.getParameter("title");
        String comment = request.getParameter("comment");
        String booksID = request.getParameter("book");
        String commentAuthor = userService.getFIOByUserID(Integer.parseInt(session.getAttribute("userID").toString()));
        Random random = new Random();
        int commentID = random.nextInt(Integer.MAX_VALUE);
        Comment tempComment = new Comment(commentID, booksID, commentAuthor, title, comment, "");

        commentService.addComment(tempComment);
        response.setContentType("text/html");
        response.getWriter().println("<h2>Thank you for your comment!</h2>");
    }
}
