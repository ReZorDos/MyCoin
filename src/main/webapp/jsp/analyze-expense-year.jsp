<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Анализ расходов за месяц</title>
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

        .expense-categories, .last-transactions {
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
            border-left: 4px solid #3498db;
        }

        .category-name {
            font-weight: bold;
            color: #2c3e50;
        }

        .category-amount {
            color: #e74c3c;
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
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Анализ расходов за год</h1>
        <p>Период: с <fmt:formatDate value="${startDate}" pattern="dd.MM.yyyy"/> по <fmt:formatDate value="${endDate}" pattern="dd.MM.yyyy"/></p>
    </div>

    <div class="chart-section">
        <div class="chart-title">Распределение расходов по категориям</div>
        <div class="chart-container">
            <img src="${pageContext.request.contextPath}/chart/expense?period=year"
                 alt="График расходов по категориям"
                 style="max-width: 600px; height: auto;" />
        </div>
    </div>

    <div class="summary">
        <div class="summary-title">Сводная информация</div>
        <c:set var="totalExpenses" value="0" />
        <c:forEach var="category" items="${expenseCategories}">
            <c:set var="totalExpenses" value="${totalExpenses + category.sum}" />
        </c:forEach>

        <div class="summary-item">
            <span class="summary-label">Общая сумма расходов:</span>
            <span class="summary-value" style="color: #e74c3c;">
                <fmt:formatNumber value="${totalExpenses}" type="currency" currencyCode="RUB"/>
            </span>
        </div>
        <div class="summary-item">
            <span class="summary-label">Количество категорий расходов:</span>
            <span class="summary-value">${empty expenseCategories ? 0 : expenseCategories.size()}</span>
        </div>
        <div class="summary-item">
            <span class="summary-label">Последние транзакции:</span>
            <span class="summary-value">${empty lastTransactions ? 0 : lastTransactions.size()}</span>
        </div>
    </div>

    <div class="data-section">
        <div class="expense-categories">
            <div class="section-title">Самые затратные категории</div>
            <c:choose>
                <c:when test="${not empty expenseCategories}">
                    <c:forEach var="category" items="${expenseCategories}">
                        <div class="category-item">
                            <div class="category-name">${category.name}</div>
                            <div class="category-amount">
                                <fmt:formatNumber value="${category.sum}" type="currency" currencyCode="RUB"/>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="no-data">Нет данных о расходах за текущий месяц</div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="last-transactions">
            <div class="section-title">Последние транзакции</div>
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
                                <span class="transaction-amount ${transaction.sum > 0 ? 'income' : 'expense'}">
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
                    <div class="no-data">Нет данных о транзакциях</div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>