<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Общий анализ финансов</title>
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

        .stats-grid {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 15px;
            margin-bottom: 30px;
        }

        .stat-card {
            background: white;
            padding: 20px;
            border-radius: 8px;
            text-align: center;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            border-left: 4px solid;
        }

        .stat-card.balance { border-left-color: #3498db; }
        .stat-card.income { border-left-color: #27ae60; }
        .stat-card.expense { border-left-color: #e74c3c; }
        .stat-card.transactions { border-left-color: #f39c12; }

        .stat-value {
            font-size: 24px;
            font-weight: bold;
            margin: 10px 0;
        }

        .stat-label {
            font-size: 14px;
            color: #7f8c8d;
        }

        .percentage {
            font-size: 12px;
            font-weight: bold;
        }

        .percentage.positive { color: #27ae60; }
        .percentage.negative { color: #e74c3c; }

        .chart-section {
            text-align: center;
            margin-bottom: 30px;
            padding: 20px;
            background: #fafafa;
            border-radius: 8px;
        }

        .chart-container {
            display: inline-block;
            border: 1px solid #ddd;
            padding: 10px;
            border-radius: 8px;
            background-color: white;
        }

        .data-section {
            display: grid;
            grid-template-columns: 1fr 1fr 1fr;
            gap: 20px;
            margin-bottom: 30px;
        }

        .section-card {
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
            border-left: 4px solid;
        }

        .expense-category { border-left-color: #e74c3c; }
        .income-category { border-left-color: #27ae60; }
        .transaction-item { border-left-color: #3498db; }

        .category-name, .transaction-title {
            font-weight: bold;
            color: #2c3e50;
        }

        .category-amount, .transaction-amount {
            font-weight: bold;
        }

        .expense-amount { color: #e74c3c; }
        .income-amount { color: #27ae60; }

        .transaction-date {
            color: #7f8c8d;
            font-size: 12px;
        }

        .transaction-type {
            font-size: 11px;
            padding: 2px 6px;
            border-radius: 3px;
            background-color: #ecf0f1;
            color: #7f8c8d;
        }

        .no-data {
            text-align: center;
            color: #7f8c8d;
            font-style: italic;
            padding: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Общий анализ финансов</h1>
        <p>Период: <fmt:formatDate value="${monthStart}" pattern="dd.MM.yyyy"/> - <fmt:formatDate value="${monthEnd}" pattern="dd.MM.yyyy"/></p>
    </div>

    <div class="stats-grid">
        <div class="stat-card balance">
            <div class="stat-label">Текущий баланс</div>
            <div class="stat-value" style="color: #3498db;">
                <fmt:formatNumber value="${currentBalance}" type="currency" currencyCode="RUB"/>
            </div>
        </div>

        <div class="stat-card income">
            <div class="stat-label">Доходы за месяц</div>
            <div class="stat-value" style="color: #27ae60;">
                <fmt:formatNumber value="${currentMonthIncome}" type="currency" currencyCode="RUB"/>
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
            <div class="stat-value" style="color: #e74c3c;">
                <fmt:formatNumber value="${currentMonthExpense}" type="currency" currencyCode="RUB"/>
            </div>
            <div class="percentage ${expensePercentageChange <= 0 ? 'positive' : 'negative'}">
                <c:choose>
                    <c:when test="${expensePercentageChange > 0}">+</c:when>
                </c:choose>
                <fmt:formatNumber value="${expensePercentageChange}" pattern="0.0"/>%
            </div>
        </div>

        <div class="stat-card transactions">
            <div class="stat-label">Последние транзакции</div>
            <div class="stat-value" style="color: #f39c12;">
                ${empty lastTransactions ? 0 : lastTransactions.size()}
            </div>
            <div class="stat-label">в системе</div>
        </div>
    </div>

    <div class="chart-section">
        <div class="chart-title">Динамика доходов и расходов за последний год</div>
        <div class="chart-container">
            <img src="${pageContext.request.contextPath}/chart/income-expense"
                 alt="Динамика доходов и расходов за последний год"
                 style="max-width: 800px; height: auto;" />
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
                            <div class="category-amount expense-amount">
                                <fmt:formatNumber value="${category.sum}" type="currency" currencyCode="RUB"/>
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
                            <div class="category-amount income-amount">
                                <fmt:formatNumber value="${category.sum}" type="currency" currencyCode="RUB"/>
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
                        <div class="transaction-item">
                            <div style="display: flex; justify-content: space-between; align-items: start; margin-bottom: 5px;">
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
                                    <fmt:formatNumber value="${transaction.sum}" type="currency" currencyCode="RUB"/>
                                </span>
                            </div>
                            <div style="display: flex; justify-content: space-between; align-items: center;">
                                <span class="transaction-date">
                                    <fmt:formatDate value="${transaction.date}" pattern="dd.MM.yyyy HH:mm"/>
                                </span>
                                <span class="transaction-type">
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
</body>
</html>