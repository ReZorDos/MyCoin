<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.kpfu.itis.model.ExpenseCategoryEntity" %>
<%@ page import="ru.kpfu.itis.model.ExpenseCategoryEntity" %>
<html>
<head>
    <title>Profile</title>
    <style>
        .category {
            border: 1px solid #ddd;
            padding: 10px;
            margin: 10px 0;
            border-radius: 5px;
        }
        .add-btn {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            text-decoration: none;
            border-radius: 4px;
            display: inline-block;
            margin: 10px 0;
        }
        img {
            max-width: 100px;
            max-height: 100px;
            margin-top: 5px;
        }
    </style>
</head>
<body>
<h2>Profile</h2>
<p>Your email: <%= request.getAttribute("email") %></p>

<a href="<%= request.getContextPath() %>/create-expense" class="add-btn">
    + Add New Category
</a>

<%
    List<ExpenseCategoryEntity> categories = (List<ExpenseCategoryEntity>) request.getAttribute("categories");
    if (categories != null && !categories.isEmpty()) {
%>
<h3>Your Expense Categories:</h3>
<%
    for (ExpenseCategoryEntity category : categories) {
%>
<div class="category">
    <strong><%= category.getName() %></strong>
    <% if (category.getIcon() != null && !category.getIcon().isEmpty()) { %>
    <br>
    Photo: <%= category.getIcon() %>
    Name: <%= category.getName() %>
    <% } %>
</div>
<%
        }
    }
%>

<br>
<a href="<%= request.getContextPath() %>/sign-in">SIGN-IN</a><br>
<a href="<%= request.getContextPath() %>/logout">LOGOUT</a><br>
</body>
</html>