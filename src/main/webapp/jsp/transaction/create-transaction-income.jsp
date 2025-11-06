<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Создать доходную транзакцию</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/forms.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/saving-goals.css">
</head>
<body>
<div class="transaction-container">
    <div class="transaction-main-content">
        <h2>Создать доходную транзакцию</h2>

        <c:if test="${not empty errors}">
            <div class="error-container">
                <c:forEach var="error" items="${errors}">
                    <div class="error-item">${error.message}</div>
                </c:forEach>
            </div>
        </c:if>

        <div class="form-container">
            <c:if test="${not empty preselectedCategoryId}">
                <div class="category-preview">
                    <div class="category-preview-header">
                        <c:if test="${not empty iconIncome}">
                            <img src="${pageContext.request.contextPath}/static/icons/income/${iconIncome}?v=1.0"
                                 alt="${nameIncome}" class="category-preview-icon"
                                 onerror="this.style.display='none'">
                        </c:if>
                        <span class="category-preview-name">${nameIncome}</span>
                    </div>
                    <div class="category-preview-label">Категория дохода</div>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/create-transaction/income" method="post" id="transactionForm">
                <div class="form-group">
                    <label><strong>Название транзакции:</strong></label>
                    <input class="form-input" type="text" name="title" required
                           placeholder="Введите название транзакции" id="transactionTitle">
                </div>

                <div class="form-group">
                    <label><strong>Сумма:</strong></label>
                    <input class="form-input" type="number" name="sum" required
                           min="0.01" step="0.01"
                           placeholder="Введите сумму в рублях" id="transactionSum">
                </div>

                <input type="hidden" name="incomeId" value="${preselectedCategoryId != null ? preselectedCategoryId : ''}">

                <div id="distributionFields"></div>

                <div>
                    <button type="submit" class="create-btn">
                        Создать транзакцию
                    </button>
                    <a href="${pageContext.request.contextPath}/profile" class="cancel-btn">
                        Отмена
                    </a>
                </div>
            </form>
        </div>
    </div>

    <div class="saving-goals-sidebar">
        <h3>Распределение по целям</h3>

        <c:if test="${not empty savingGoals}">
            <div class="saving-goals-scrollable">
                <c:forEach var="goal" items="${savingGoals}">
                    <div class="saving-goal-item">
                        <div class="goal-header">
                            <span class="goal-name">${goal.name}</span>
                        </div>
                        <div class="goal-progress">
                            Накоплено: ${goal.current_amount} / ${goal.total_amount} руб.
                        </div>
                        <div class="progress-bar">
                            <div class="progress-fill"
                                 style="width: ${goal.total_amount > 0 ? (goal.current_amount / goal.total_amount * 100) : 0}%">
                            </div>
                        </div>
                        <input type="number"
                               class="distribution-input"
                               min="0"
                               step="0.01"
                               placeholder="0.00"
                               data-goal-id="${goal.id}">
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${empty savingGoals}">
            <div class="saving-goals-empty">
                <p>У вас пока нет целей накопления.</p>
            </div>
        </c:if>
    </div>
</div>

<script src="${pageContext.request.contextPath}/static/js/transaction-common.js"></script>
<script src="${pageContext.request.contextPath}/static/js/income-transaction.js"></script>
</body>
</html>