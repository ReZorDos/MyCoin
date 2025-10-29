<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Создать расходную транзакцию</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/forms.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/transactions.css">
</head>
<body>
    <div class="transaction-container">
        <h1 class="transaction-title">Создать расходную транзакцию</h1>

        <c:if test="${not empty preselectedCategoryId}">
            <div class="category-info">
                <strong>💡 Транзакция будет создана для выбранной категории</strong>
                <input type="hidden" id="expenseId" name="expenseId" value="${preselectedCategoryId}">
            </div>
        </c:if>

        <c:if test="${not empty errors}">
            <div class="error-messages">
                <c:forEach var="error" items="${errors}">
                    <div class="error-message">
                        <strong>Ошибка:</strong>
                        <c:choose>
                            <c:when test="${not empty error.message}">
                                ${error.message}
                            </c:when>
                            <c:otherwise>
                                Неизвестная ошибка
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <form class="transaction-form" action="${pageContext.request.contextPath}/create-transaction/expense" method="post">
            <div class="transaction-form-group">
                <label class="transaction-label" for="title">Название транзакции:</label>
                <input class="transaction-input" type="text" id="title" name="title" required
                       placeholder="Введите название транзакции (например: Покупка продуктов, Оплата коммунальных услуг)">
            </div>

            <div class="transaction-form-group">
                <label class="transaction-label" for="sum">Сумма:</label>
                <input class="transaction-input" type="number" id="sum" name="sum" required
                       min="0.01" step="0.01"
                       placeholder="Введите сумму в рублях">
            </div>

            <input type="hidden" name="expenseId" value="${preselectedCategoryId != null ? preselectedCategoryId : ''}">

            <button type="submit" class="transaction-btn">Создать транзакцию</button>
        </form>

        <a href="${pageContext.request.contextPath}/profile" class="back-link">← Вернуться в профиль</a>
    </div>

<script src="${pageContext.request.contextPath}/static/js/transaction-common.js"></script>
</body>
</html>