<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Общий анализ финансов</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/head.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/analyze-overview.css">
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
            <h1>Общий анализ</h1>
        </div>

        <div class="period-info">
            <fmt:formatDate value="${monthStart}" pattern="dd.MM.yyyy"/> - <fmt:formatDate value="${monthEnd}" pattern="dd.MM.yyyy"/>
        </div>


        <div class="chart-stats-container">
            <div class="chart-section">
                <div class="chart-container">
                    <img src="${pageContext.request.contextPath}/chart/income-expense"
                         alt="Динамика доходов и расходов за последний год"
                         style="max-width: 100%; height: auto;" />
                </div>
            </div>

            <div class="stats-cards-vertical">
                <div class="stat-card balance">
                    <div class="stat-label">Текущий баланс</div>
                    <div class="stat-value">
                        <fmt:formatNumber value="${currentBalance}" pattern="#,##0.00"/> ₽
                    </div>
                </div>

                <div class="stat-card income">
                    <div class="stat-label">Доходы за месяц</div>
                    <div class="stat-value">
                        <fmt:formatNumber value="${currentMonthIncome}" pattern="#,##0.00"/> ₽
                    </div>
                    <div class="percentage ${incomePercentageChange >= 0 ? 'positive' : 'negative'}">
                        <c:choose>
                            <c:when test="${incomePercentageChange > 0}">+</c:when>
                        </c:choose>
                        <fmt:formatNumber value="${incomePercentageChange}" pattern="0.0"/>%
                    </div>
                </div>

                <div class="stat-card expense">
                    <div class="stat-label">Расходы за месяц</div>
                    <div class="stat-value">
                        <fmt:formatNumber value="${currentMonthExpense}" pattern="#,##0.00"/> ₽
                    </div>
                    <div class="percentage ${expensePercentageChange <= 0 ? 'positive' : 'negative'}">
                        <c:choose>
                            <c:when test="${expensePercentageChange > 0}">+</c:when>
                        </c:choose>
                        <fmt:formatNumber value="${expensePercentageChange}" pattern="0.0"/>%
                    </div>
                </div>
            </div>
        </div>

        <div class="data-section">
            <div class="section-card">
                <div class="section-title">Топ категорий расходов</div>
                <c:choose>
                    <c:when test="${not empty topExpenseCategories}">
                        <c:forEach var="category" items="${topExpenseCategories}">
                            <div class="category-item expense-category">
                                <div class="category-name">${category.name}</div>
                                <div class="category-amount">
                                    <fmt:formatNumber value="${category.sum}" pattern="#,##0.00"/> ₽
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="no-data">Нет данных о расходах</div>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="section-card">
                <div class="section-title">Топ категорий доходов</div>
                <c:choose>
                    <c:when test="${not empty topIncomeCategories}">
                        <c:forEach var="category" items="${topIncomeCategories}">
                            <div class="category-item income-category">
                                <div class="category-name">${category.name}</div>
                                <div class="category-amount">
                                    <fmt:formatNumber value="${category.sum}" pattern="#,##0.00"/> ₽
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="no-data">Нет данных о доходах</div>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="section-card">
                <div class="section-title">Последние транзакции</div>
                <c:choose>
                    <c:when test="${not empty lastTransactions}">
                        <c:forEach var="transaction" items="${lastTransactions}">
                            <div class="transaction-item ${transaction.type == 'INCOME' ? 'income' : 'expense'}">
                                <div class="transaction-header">
                                    <span class="transaction-title">
                                        <c:choose>
                                            <c:when test="${not empty transaction.title}">
                                                ${transaction.title}
                                            </c:when>
                                            <c:otherwise>
                                                Без описания
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                    <span class="transaction-amount ${transaction.type == 'INCOME' ? 'income-amount' : 'expense-amount'}">
                                        <fmt:formatNumber value="${transaction.sum}" pattern="#,##0.00"/> ₽
                                    </span>
                                </div>
                                <div class="transaction-footer">
                                    <span class="transaction-date">
                                        <fmt:formatDate value="${transaction.date}" pattern="dd.MM.yyyy HH:mm"/>
                                    </span>
                                    <span class="transaction-type ${transaction.type == 'INCOME' ? 'income' : 'expense'}">
                                            ${transaction.type == 'INCOME' ? 'Доход' : 'Расход'}
                                    </span>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="no-data">Нет транзакций</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
</body>
</html>