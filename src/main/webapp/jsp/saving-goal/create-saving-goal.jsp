<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Создать цель</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/forms.css">
</head>
<body>
<div class="container">
    <h2>Создать новую цель</h2>

    <c:if test="${not empty errors}">
        <div class="error-container">
            <c:forEach var="error" items="${errors}">
                <div class="error-item">${error.message}</div>
            </c:forEach>
        </div>
    </c:if>

    <div class="form-container">
        <form action="${pageContext.request.contextPath}/saving-goal/create" method="post">
            <div class="form-group">
                <label for="name">Название цели:</label>
                <input class="form-input" type="text" id="name" name="name" required
                       placeholder="Введите название цели">
            </div>

            <div class="form-group">
                <label for="title">Описание:</label>
                <input class="form-input" type="text" id="title" name="title"
                       placeholder="Введите описание цели (необязательно)">
            </div>

            <div class="form-group">
                <label for="total_amount">Сумма накопления:</label>
                <input class="form-input" type="number" id="total_amount" name="total_amount"
                       step="0.01" min="0.01" required
                       placeholder="Введите сумму накопления">
            </div>

            <div>
                <button type="submit" class="create-btn">Создать цель</button>
                <a href="${pageContext.request.contextPath}/profile" class="cancel-btn">Отмена</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>