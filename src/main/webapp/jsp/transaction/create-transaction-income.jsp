<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Создать доходную транзакцию</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/forms.css">
</head>
<body>
<div class="container">
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

        <form action="${pageContext.request.contextPath}/create-transaction/income" method="post">
            <div class="form-group">
                <label><strong>Название транзакции:</strong></label>
                <input class="form-input" type="text" name="title" required
                       placeholder="Введите название транзакции">
            </div>

            <div class="form-group">
                <label><strong>Сумма:</strong></label>
                <input class="form-input" type="number" name="sum" required
                       min="0.01" step="0.01"
                       placeholder="Введите сумму в рублях">
            </div>

            <input type="hidden" name="incomeId" value="${preselectedCategoryId != null ? preselectedCategoryId : ''}">

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

<script src="${pageContext.request.contextPath}/static/js/transaction-common.js"></script>
</body>
</html>