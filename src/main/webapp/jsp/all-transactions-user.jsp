<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        <c:choose>
            <c:when test="${not empty transactionList}">
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
                    <c:forEach var="transaction" items="${transactionList}">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty transaction.title}">
                                        ${transaction.title}
                                    </c:when>
                                    <c:otherwise>
                                        &nbsp;
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${transaction.type eq 'income'}">
                                        <span class="type-income">${transaction.type}</span>
                                    </c:when>
                                    <c:when test="${transaction.type eq 'expense'}">
                                        <span class="type-expense">${transaction.type}</span>
                                    </c:when>
                                    <c:when test="${not empty transaction.type}">
                                        ${transaction.type}
                                    </c:when>
                                    <c:otherwise>
                                        &nbsp;
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:if test="${not empty transaction.date}">
                                    ${transaction.date}
                                </c:if>
                            </td>
                            <td>${transaction.sum}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty transaction.incomeCategoryId}">
                                        ${transaction.incomeCategoryId}
                                    </c:when>
                                    <c:otherwise>
                                        -
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty transaction.expenseCategoryId}">
                                        ${transaction.expenseCategoryId}
                                    </c:when>
                                    <c:otherwise>
                                        -
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty transaction.savingGoalId}">
                                        ${transaction.savingGoalId}
                                    </c:when>
                                    <c:otherwise>
                                        -
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="empty-message">
                    Транзакции не найдены
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>