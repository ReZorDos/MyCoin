<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Мои транзакции</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/head.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/transactions.css">
</head>
<body>
<header class="top-header">
    <div class="app-container">
        <div class="header-content">
            <a href="${pageContext.request.contextPath}/profile" class="site-title">
                MyCoin
            </a>
            <nav class="header-nav">
                <a href="${pageContext.request.contextPath}/all-transactions" class="nav-link">
                    Все транзакции
                </a>
                <a href="${pageContext.request.contextPath}/analyze/overview" class="nav-link">
                    Общий анализ
                </a>
                <a href="${pageContext.request.contextPath}/analyze-expense/day" class="nav-link">
                    Анализ расходов
                </a>
                <a href="${pageContext.request.contextPath}/analyze-income/day" class="nav-link">
                    Анализ доходов
                </a>
                <a href="${pageContext.request.contextPath}/logout" class="nav-link logout">
                    Выход
                </a>
            </nav>
        </div>
    </div>
</header>

<div class="app-container">
    <div class="main-content">
        <div class="page-header">
            <a href="${pageContext.request.contextPath}/profile" class="back-link">
                ← Вернуться в профиль
            </a>
            <h1>Мои транзакции</h1>
        </div>

        <c:choose>
            <c:when test="${not empty transactionList}">
                <div class="table-container">
                    <table class="transaction-table">
                        <thead>
                        <tr>
                            <th>Название</th>
                            <th>Тип</th>
                            <th>Дата</th>
                            <th>Сумма</th>
                            <th>Категория</th>
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
                                            <span class="empty-value">-</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${transaction.type eq 'INCOME'}">
                                            <span class="type-income">Доход</span>
                                        </c:when>
                                        <c:when test="${transaction.type eq 'EXPENSE'}">
                                            <span class="type-expense">Расход</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="empty-value">-</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <fmt:formatDate value="${transaction.date}" pattern="dd.MM.yyyy HH:mm"/>
                                </td>
                                <td>
                                        <span class="amount <c:if test="${transaction.type eq 'EXPENSE'}">expense-amount</c:if>">
                                            <fmt:formatNumber value="${transaction.sum}" type="number" maxFractionDigits="2"/> ₽
                                        </span>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${transaction.type eq 'INCOME' and not empty transaction.incomeCategoryName}">
                                            <span class="category-name">${transaction.incomeCategoryName}</span>
                                        </c:when>
                                        <c:when test="${transaction.type eq 'EXPENSE' and not empty transaction.expenseCategoryName}">
                                            <span class="category-name">${transaction.expenseCategoryName}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="empty-value">-</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <div class="empty-message">
                    Транзакции не найдены
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>