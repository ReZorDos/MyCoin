<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Мои транзакции</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/tables.css">
</head>
<body>
    <div class="container">
        <h1>Мои транзакции</h1>

        <c:choose>
            <c:when test="${not empty transactionList}">
                <div class="table-container">
                    <div class="table-responsive">
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
                                                <span class="type-income">доход</span>
                                            </c:when>
                                            <c:when test="${transaction.type eq 'expense'}">
                                                <span class="type-expense">расход</span>
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
                    </div>
                </div>
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