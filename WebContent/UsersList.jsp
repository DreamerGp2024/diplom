<%@ page import="java.util.ArrayList" %>
<%@ page import="com.DreamerGp2024.model.Post" %>
<%@ page import="java.util.List" %>
<%@ page import="com.DreamerGp2024.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Карточка новости</title>
    <style>
        .news-container {
            display: flex;
            flex-direction: column;
            align-items: center;
            width: 100%;
        }
        .news-card {
            border: 1px solid #987856; /* Коричневый оттенок, взятый из деревянных рам магазина */
            padding: 16px;
            margin: 16px 0;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(143, 101, 55, 0.3); /* Мягкая тень с оттенком из изображения */
            background-color: #f3f1e7; /* Кремовый фон для контраста */
            width: 80%; /* Ширина карточки */
            max-width: 600px; /* Максимальная ширина карточки */
        }
        .news-header {
            font-size: 1.5em;
            margin-bottom: 8px;
            color: #763423; /* Темный оттенок, похожий на вывеску магазина */
        }
        .news-meta {
            font-size: 0.9em;
            color: #a89a91; /* Приглушенный красный оттенок из кирпичей вокруг окна магазина */
            margin-bottom: 16px;
        }
        .news-body {
            font-size: 1.1em;
            color: #5c5346; /* Темный земляной оттенок для читаемости текста */
        }
    </style>
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
        var activeTab = 'news';
    </script>
</head>
<body>
<header>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
            background-color: #f2f2f2;
        }
        th {
            background-color: #f2f2f2;
        }
        td {

        }
    </style>
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
                <!--					<li class="nav-item"><span><a-->
                <!--							class="nav-link" href="storebooks" id="home">Home</a></span></li>-->
                <li class="nav-item "><span><a class="nav-link"
                                               href="storebooks" id="storebooks">Store Books</a></span></li>
                <li class="nav-item "><span><a class="nav-link"
                                               href="addbook" id="addbook">Add Books</a></span></li>
                <li class="nav-item "><span><a class="nav-link"
                                               href="orders" id="orders">Orders</a></span></li>
                <li class="nav-item "><span><a class="nav-link" href="addpost" id="addpost">Add Post</a></span></li>
                <li class="nav-item "><span><a class="nav-link" href="users" id="users">Users</a></span></li>
                <li class="nav-item "><span><a class="nav-link"
                                               href="about" id="about">About Us</a></span></li>
                <li class="nav-item "><span><a class="nav-link"
                                               href="logout" id="logout">Logout</a></span></li>
            </ul>
        </div>
    </nav>
</header>
<div style="text-align: center;">
<h3 style="width: 75%; margin-left: auto; margin-right: auto;background:#ffff80">List of Users</h3>
<table style="width: 75%; margin-left: auto; margin-right: auto;">
    <thead>
    <tr>
        <th>ID</th>
        <th>Email</th>
        <th>FirstName</th>
        <th>LastName</th>
        <th>Phone</th>
        <th>Address</th>
        <th>Role</th>
        <th>Make manager</th>
        <th>Delete</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<User> users = (List<User>) request.getAttribute("users");
        for (User user : users) {
    %>

    <tr>
        <td><%= user.getUserID() %></td>
        <td><%= user.getEmail() %></td>
        <td><%= user.getFirstName() %></td>
        <td><%= user.getLastName() %></td>
        <td><%= user.getPhone() %></td>
        <td><%= user.getAddress() %></td>
        <td><%= user.getRole().get(0).toString() %></td>
        <td>
            <form action="setmngr" method="post">
                <input type="hidden" name="userID" value="<%= user.getUserID() %>">
                <button type="submit">Make Manager</button>
            </form>

        </td>
        <td>
            <form action="deleteUser" method="post">
                <input type="hidden" name="userID" value="<%= user.getUserID() %>">
                <button type="submit">Удалить</button>
            </form>
        </td>
    </tr>

    <%
        }
    %>
    </tbody>
</table>
</div>
</body>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.2.1/dist/js/bootstrap.min.js"></script>
</html>
