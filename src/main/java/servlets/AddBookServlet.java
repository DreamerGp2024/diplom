package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DreamerGp2024.constant.BookStoreConstants;
import com.DreamerGp2024.constant.db.BooksDBConstants;
import com.DreamerGp2024.model.Book;
import com.DreamerGp2024.model.UserRole;
import com.DreamerGp2024.service.BookService;
import com.DreamerGp2024.service.impl.BookServiceImpl;
import com.DreamerGp2024.util.StoreUtil;

public class AddBookServlet extends HttpServlet {
    BookService bookService = new BookServiceImpl();

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        PrintWriter pw = res.getWriter();
        res.setContentType(BookStoreConstants.CONTENT_TYPE_TEXT_HTML);

        if (!StoreUtil.isLoggedIn(UserRole.MANAGER, req.getSession())) {
            RequestDispatcher rd = req.getRequestDispatcher("SellerLogin.html");
            rd.include(req, res);
            pw.println("<table class=\"tab\"><tr><td>Please Login First to Continue!!</td></tr></table>");
            return;
        }

        String bName = req.getParameter(BooksDBConstants.COLUMN_NAME);
        RequestDispatcher rd = req.getRequestDispatcher("SellerHome.html");
        rd.include(req, res);
        StoreUtil.setActiveTab(pw, "addbook");
        pw.println("<div style=\"align-items: center;display: flex;justify-content: center;\">");
        if(bName == null || bName.isBlank()) {
            //render the add book form;
            showAddBookForm(pw);
            return;
        } //else process the add book
        
 
        try {
            String uniqueID = UUID.randomUUID().toString();
            String bCode = uniqueID;
            String bAuthor = req.getParameter(BooksDBConstants.COLUMN_AUTHOR);
            double bPrice = Integer.parseInt(req.getParameter(BooksDBConstants.COLUMN_PRICE));
            int bQty = Integer.parseInt(req.getParameter(BooksDBConstants.COLUMN_QUANTITY));
            String bDescription = req.getParameter(BooksDBConstants.COLUMN_DESCRIPTIONBOOK);
            Book book = new Book(bCode, bName, bAuthor, bPrice, bQty,bDescription);
            String message = bookService.addBook(book);
            if ("SUCCESS".equalsIgnoreCase(message)) {
                pw.println(
                        "<table class=\"tab\"><tr><td>Book Detail Updated Successfully!<br/>Add More Books</td></tr></table>");
            } else {
                pw.println("<table class=\"tab\"><tr><td>Failed to Add Books! Fill up CareFully</td></tr></table>");
                //rd.include(req, res);
            }
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<table class=\"tab\"><tr><td>Failed to Add Books! Fill up CareFully</td></tr></table>");
        }
    }
    
    private static void showAddBookForm(PrintWriter pw) {
        String form = """
                <div class="form-container">
                     <form action="addbook" method="post">
                        <!-- <label for="bookCode">Book Code : </label><input type="text" name="barcode" id="bookCode" placeholder="Enter Book Code" required><br/> -->
                        <label for="bookName">Book Name : </label> <input type="text" name="name" id="bookName" placeholder="Enter Book's name" required><br/>
                        <label for="bookAuthor">Book Author : </label><input type="text" name="author" id="bookAuthor" placeholder="Enter Author's Name" required><br/>
                        <label for="bookPrice">Book Price : </label><input type="number" name="price" placeholder="Enter the Price" required><br/>
                        <label for="bookQuantity">Book Qnty : </label><input type="number" name="quantity" id="bookQuantity" placeholder="Enter the quantity" required><br/>
                        <label for="bookDescription">Book Description : </label> <input type="text" name="bookdescription" id="bookdescription" placeholder="Enter Book's description" required><br/>
                        <input class="btn btn-success my-2" type="submit" value="Add Book">
                     </form>
                </div>
                """;
        pw.println(form);
    }
}
