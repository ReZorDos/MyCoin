<%@ page import="java.util.List" %>
<%@ page import="ru.kpfu.itis.model.TransactionEntity" %>
<%@ page import="java.util.UUID" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Мои транзакции</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }
        .transaction-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .transaction-table th,
        .transaction-table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        .transaction-table th {
            background-color: #f8f9fa;
            font-weight: bold;
            color: #555;
        }
        .transaction-table tr:hover {
            background-color: #f8f9fa;
        }
        .type-income {
            color: #28a745;
            font-weight: bold;
        }
        .type-expense {
            color: #dc3545;
            font-weight: bold;
        }
        .empty-message {
            text-align: center;
            color: #666;
            font-style: italic;
            padding: 40px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Мои транзакции</h1>

    <%
        List<TransactionEntity> transactionList = (List<TransactionEntity>) request.getAttribute("transactionList");
        if (transactionList != null && !transactionList.isEmpty()) {
    %>
    <table class="transaction-table">
        <thead>
        <tr>
            <th>Название</th>
            <th>Тип</th>
            <th>Дата</th>
            <th>Сумма</th>
            <th>Категория дохода</th>
            <th>Категория расхода</th>
            <th>Цель накопления</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (TransactionEntity transaction : transactionList) {
        %>
        <tr>
            <td><%= transaction.getTitle() != null ? transaction.getTitle() : "" %></td>
            <td>
                            <span class="<%= "income".equals(transaction.getType()) ? "type-income" : "type-expense" %>">
                                <%= transaction.getType() != null ? transaction.getType() : "" %>
                            </span>
            </td>
            <td><%= transaction.getDate() != null ? transaction.getDate() : "" %></td>
            <td><%= transaction.getSum() %></td>
            <td><%= transaction.getIncomeCategoryId() != null ? transaction.getIncomeCategoryId() : "-" %></td>
            <td><%= transaction.getExpenseCategoryId() != null ? transaction.getExpenseCategoryId() : "-" %></td>
            <td><%= transaction.getSavingGoalId() != null ? transaction.getSavingGoalId() : "-" %></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
    } else {
    %>
    <div class="empty-message">
        Транзакции не найдены
    </div>
    <%
        }
    %>
</div>
</body>
</html>