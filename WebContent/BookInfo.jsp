<%--
  Created by IntelliJ IDEA.
  User: kostya89
  Date: 12.11.2024
  Time: 23:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book Store</title>
    <link rel="apple-touch-icon" sizes="180x180"
          href="./favicons/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="32x32"
          href="./favicons/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16"
          href="./favicons/favicon-16x16.png">
    <link rel="manifest" href="./favicons/site.webmanifest">
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="styles.css">
    <!-- JavaScript Bundle with Popper -->
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js">
    </script>
    <script
            src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js">
    </script>
    <script>
        var activeTab = 'viewbook';
    </script>
<%--    <title>Детали книги</title>--%>
</head>
<body>
    <header>
        <nav class="navbar navbar-expand-sm bg-dark"
             style="margin-bottom: 0px">
            <a class="navbar-brand"> <!-- The below line can be an image or a h1, either will work -->
                <img src="logo.png" alt="Google logo" width="60" height="30px">
            </a>

            <button style="background-color: white;" class="navbar-toggler"
                    type="button" data-toggle="collapse" data-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false"
                    aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon" style="color: #fff; font-size: 28px;"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item"><span><a
                            class="nav-link" href="viewbook" id="home">Home</a></span></li>
                    <li class="nav-item "><span><a class="nav-link" href="viewbook" id="books">Available
                                    Books</a></span></li>
                    <li class="nav-item "><span><a
                            class="nav-link glyphicon  glyphicon-shopping-cart " href="cart"
                            id="cart"> Cart</a></span></li>
                    <li class="nav-item "><span><a
                            class="nav-link glyphicon " href="news"
                            id="news"> News</a></span></li>
                    <li class="nav-item "><span><a class="nav-link" href="about" id="about">About Us</a></span></li>
                    <li class="nav-item "><span><a class="nav-link" href="logout" id="logout">Logout</a></span></li>
                </ul>
            </div>
        </nav>
    </header>
    <h1>${book.getName()}</h1>
    <p>Автор: ${book.getAuthor()}</p>
    <p>Цена: ${book.getPrice()}</p>
    <p>Штрихкод: ${book.getBarcode()}</p>

</body>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/js/bootstrap.min.js"></script>
</html>