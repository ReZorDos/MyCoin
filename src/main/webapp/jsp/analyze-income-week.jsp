<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Анализ доходов за день</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
            color: #333;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .header {
            text-align: center;
            margin-bottom: 30px;
            padding-bottom: 15px;
            border-bottom: 2px solid #e0e0e0;
        }

        .chart-section {
            text-align: center;
            margin-bottom: 30px;
        }

        .chart-container {
            display: inline-block;
            border: 1px solid #ddd;
            padding: 10px;
            border-radius: 8px;
            background-color: white;
        }

        .chart-title {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 10px;
            color: #2c3e50;
        }

        .data-section {
            display: flex;
            gap: 20px;
            margin-bottom: 30px;
        }

        .income-categories, .last-transactions {
            flex: 1;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 15px;
            background-color: #fafafa;
        }

        .section-title {
            font-size: 16px;
            font-weight: bold;
            margin-bottom: 15px;
            color: #2c3e50;
            border-bottom: 1px solid #e0e0e0;
            padding-bottom: 8px;
        }

        .category-item, .transaction-item {
            padding: 10px;
            margin-bottom: 8px;
            background-color: white;
            border-radius: 4px;
            border-left: 4px solid #27ae60;
        }

        .category-name {
            font-weight: bold;
            color: #2c3e50;
        }

        .category-amount {
            color: #27ae60;
            font-weight: bold;
        }

        .transaction-amount {
            font-weight: bold;
        }

        .transaction-amount.income {
            color: #27ae60;
        }

        .transaction-amount.expense {
            color: #e74c3c;
        }

        .transaction-date {
            color: #7f8c8d;
            font-size: 12px;
        }

        .transaction-description {
            margin-top: 5px;
            color: #34495e;
        }

        .no-data {
            text-align: center;
            color: #7f8c8d;
            font-style: italic;
            padding: 20px;
        }

        .summary {
            background-color: #ecf0f1;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }

        .summary-title {
            font-size: 16px;
            font-weight: bold;
            margin-bottom: 10px;
            color: #2c3e50;
        }

        .summary-item {
            display: flex;
            justify-content: space-between;
            margin-bottom: 5px;
        }

        .summary-label {
            font-weight: bold;
        }

        .summary-value {
            font-weight: bold;
        }

        .daily-stats {
            display: flex;
            gap: 15px;
            margin-bottom: 20px;
        }

        .stat-card {
            flex: 1;
            background: linear-gradient(135deg, #27ae60 0%, #2ecc71 100%);
            color: white;
            padding: 20px;
            border-radius: 8px;
            text-align: center;
        }

        .stat-value {
            font-size: 24px;
            font-weight: bold;
            margin: 10px 0;
        }

        .stat-label {
            font-size: 14px;
            opacity: 0.9;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Анализ доходов за неделю</h1>
        <p>Дата: <fmt:formatDate value="${startDate}" pattern="dd.MM.yyyy"/></p>
    </div>

    <div class="daily-stats">
        <div class="stat-card">
            <div class="stat-label">Доходы за неделю</div>
            <div class="stat-value">
                <fmt:formatNumber value="${currentTotal}" type="currency" currencyCode="RUB"/>
            </div>
        </div>

        <div class="stat-card">
            <div class="stat-label">Изменение за прошлую неделю</div>
            <div class="stat-value" style="color:
            <c:choose>
            <c:when test="${percentageChange >= 0}">#2c3e50</c:when>
            <c:when test="${percentageChange < 0}">#e74c3c</c:when>
            <c:otherwise>#ffffff</c:otherwise>
            </c:choose>;">
                <c:choose>
                    <c:when test="${percentageChange > 0}">
                        +<fmt:formatNumber value="${percentageChange}" pattern="0.0"/>% ↗
                    </c:when>
                    <c:when test="${percentageChange < 0}">
                        <fmt:formatNumber value="${percentageChange}" pattern="0.0"/>% ↘
                    </c:when>
                    <c:otherwise>
                        0.0% →
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="stat-label" style="font-size: 12px; margin-top: 5px;">
                Прошлая неделя: <fmt:formatNumber value="${previousTotal}" type="currency" currencyCode="RUB"/>
            </div>
        </div>

        <div class="stat-card">
            <div class="stat-label">Категорий доходов</div>
            <div class="stat-value">${empty incomeCategories ? 0 : incomeCategories.size()}</div>
        </div>
        <div class="stat-card">
            <div class="stat-label">Транзакций</div>
            <div class="stat-value">${empty lastTransactions ? 0 : lastTransactions.size()}</div>
        </div>
    </div>

    <div class="chart-section">
        <div class="chart-title">Распределение доходов за неделю</div>
        <div class="chart-container">
            <img src="${pageContext.request.contextPath}/chart/income?period=week"
                 alt="График доходов по категориям за день"
                 style="max-width: 600px; height: auto;" />
        </div>
    </div>

    <div class="data-section">
        <div class="income-categories">
            <div class="section-title">Категории доходов за неделю</div>
            <c:choose>
                <c:when test="${not empty incomeCategories}">
                    <c:forEach var="category" items="${incomeCategories}">
                        <div class="category-item">
                            <div class="category-name">${category.name}</div>
                            <div class="category-amount">
                                <fmt:formatNumber value="${category.sum}" type="currency" currencyCode="RUB"/>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="no-data">Нет доходов за неделю</div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="last-transactions">
            <div class="section-title">Транзакции за день</div>
            <c:choose>
                <c:when test="${not empty lastTransactions}">
                    <c:forEach var="transaction" items="${lastTransactions}">
                        <div class="transaction-item">
                            <div style="display: flex; justify-content: space-between; align-items: center;">
                                <span class="transaction-description">
                                    <c:choose>
                                        <c:when test="${not empty transaction.title}">
                                            ${transaction.title}
                                        </c:when>
                                        <c:otherwise>
                                            Без описания
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                                <span class="transaction-amount ${transaction.type == 'INCOME' ? 'income' : 'expense'}">
                                    <fmt:formatNumber value="${transaction.sum}" type="currency" currencyCode="RUB"/>
                                </span>
                            </div>
                            <div class="transaction-date">
                                <fmt:formatDate value="${transaction.date}" pattern="dd.MM.yyyy HH:mm"/>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="no-data">Нет транзакций за сегодня</div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>