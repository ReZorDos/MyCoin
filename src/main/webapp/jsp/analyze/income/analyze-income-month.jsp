<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Анализ доходов за месяц</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/head.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/analyze-income.css">
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

            <h1>Анализ доходов</h1>

            <div class="period-navigation">
                <a href="${pageContext.request.contextPath}/analyze-income/day" class="period-btn">За день</a>
                <a href="${pageContext.request.contextPath}/analyze-income/week" class="period-btn">За неделю</a>
                <a href="${pageContext.request.contextPath}/analyze-income/month" class="period-btn active">За месяц</a>
                <a href="${pageContext.request.contextPath}/analyze-income/year" class="period-btn">За год</a>
            </div>
        </div>

        <div class="period-info">
            Период: <fmt:formatDate value="${startDate}" pattern="dd.MM.yyyy"/> - <fmt:formatDate value="${endDate}" pattern="dd.MM.yyyy"/>
        </div>

        <div class="chart-stats-container">
            <div class="chart-section">
                <div class="chart-title">Распределение доходов за месяц</div>
                <div class="chart-container">
                    <img src="${pageContext.request.contextPath}/chart/income?period=month"
                         alt="График доходов по категориям за месяц"
                         style="max-width: 100%; height: auto;" />
                </div>
            </div>

            <div class="stats-cards-vertical">
                <div class="stat-card income">
                    <div class="stat-label">Доходы за месяц</div>
                    <div class="stat-value">
                        <fmt:formatNumber value="${currentTotal}" pattern="#,##0.00"/> ₽
                    </div>
                </div>

                <div class="stat-card change">
                    <div class="stat-label">Изменение за прошлый месяц</div>
                    <div class="stat-value ${percentageChange >= 0 ? 'positive' : 'negative'}">
                        <c:choose>
                            <c:when test="${percentageChange > 0}">
                                +<fmt:formatNumber value="${percentageChange}" pattern="0.0"/>%
                            </c:when>
                            <c:when test="${percentageChange < 0}">
                                <fmt:formatNumber value="${percentageChange}" pattern="0.0"/>%
                            </c:when>
                            <c:otherwise>
                                0.0%
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="stat-label yesterday">
                        Прошлый месяц: <fmt:formatNumber value="${previousTotal}" pattern="#,##0.00"/> ₽
                    </div>
                </div>
            </div>
        </div>

        <div class="data-section">
            <div class="section-card">
                <div class="section-title">Топ категорий доходов за месяц</div>
                <c:choose>
                    <c:when test="${not empty incomeCategories}">
                        <c:forEach var="category" items="${incomeCategories}">
                            <div class="category-item">
                                <div class="category-name">${category.name}</div>
                                <div class="category-amount">
                                    <fmt:formatNumber value="${category.sum}" pattern="#,##0.00"/> ₽
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="no-data">Нет доходов за месяц</div>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="section-card">
                <div class="section-title">Топ транзакций за месяц</div>
                <c:choose>
                    <c:when test="${not empty lastTransactions}">
                        <c:forEach var="transaction" items="${lastTransactions}">
                            <div class="transaction-item">
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
                                    <span class="transaction-amount income-amount">
                                        <fmt:formatNumber value="${transaction.sum}" pattern="#,##0.00"/> ₽
                                    </span>
                                </div>
                                <div class="transaction-footer">
                                    <span class="transaction-date">
                                        <fmt:formatDate value="${transaction.date}" pattern="dd.MM.yyyy HH:mm"/>
                                    </span>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="no-data">Нет транзакций за месяц</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
</body>
</html>