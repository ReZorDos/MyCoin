<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Изменить цель</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/forms.css">
</head>
<body>
<div class="container">
    <h2>Изменить цель</h2>

    <c:if test="${not empty errors}">
        <div class="error-container">
            <c:forEach var="error" items="${errors}">
                <div class="error-item">${error.message}</div>
            </c:forEach>
        </div>
    </c:if>

    <div class="form-container">
        <form method="post" action="${pageContext.request.contextPath}/saving-goal/update">
            <input type="hidden" name="uuid" value="${savingGoal.id}">

            <div class="form-group">
                <label for="name">Название цели:</label>
                <input class="form-input" type="text" id="name" name="name" value="${savingGoal.name}" required
                       placeholder="Введите новое название цели">
            </div>

            <div class="form-group">
                <label for="title">Описание:</label>
                <input class="form-input" type="text" id="title" name="title" value="${savingGoal.title}"
                       placeholder="Введите новое описание (необязательно)">
            </div>

            <div class="form-group">
                <label for="total_amount">Цель накопления:</label>
                <input class="form-input" type="number" id="total_amount" name="total_amount"
                       value="${savingGoal.total_amount}" step="0.01" min="0.01" required
                       placeholder="Введите новую цель накопления">
            </div>

            <div>
                <button type="submit" class="create-btn">Обновить цель</button>
                <a href="${pageContext.request.contextPath}/profile" class="cancel-btn">Отмена</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>