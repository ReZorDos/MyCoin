<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Создать категорию заработка</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/forms.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/icons.css">
</head>
<body>
<div class="container">
    <h2>Создать категорию заработка</h2>

    <c:if test="${not empty errors}">
        <div class="error-container">
            <c:forEach var="error" items="${errors}">
                <div class="error-item">${error.message}</div>
            </c:forEach>
        </div>
    </c:if>

    <div class="form-container">
        <form action="create-income" method="post">
            <div class="form-group">
                <label><strong>Название категории:</strong></label>
                <input class="form-input" type="text" name="name" required
                       placeholder="Введите название категории">
            </div>

            <div class="form-group">
                <label><strong>Выберите иконку:</strong></label>

            <div class="icons-container">
                <c:choose>
                    <c:when test="${not empty availableIcons}">
                        <c:forEach var="icon" items="${availableIcons}">
                            <label class="icon-option">
                                <input type="radio" name="icon" value="${icon}"
                                       class="icon-radio" required>
                                <img src="${pageContext.request.contextPath}/static/icons/income/${icon}?v=1.0"
                                     alt="${icon}"
                                     onerror="this.style.display='none'">
                                <div class="icon-name">${icon}</div>
                            </label>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="no-icons-message">
                            Иконки не найдены. Проверьте папку /static/icons/income
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

                <small style="color: #666; display: block; margin-top: 8px;">* Выберите одну иконку из списка</small>
            </div>

            <div>
                <button type="submit" class="create-btn">
                    Создать категорию
                </button>
                <a href="../profile" class="cancel-btn">
                    Отмена
                </a>
            </div>
        </form>
    </div>
</div>

<script src="${pageContext.request.contextPath}/static/js/icon-selection.js"></script>
</body>
</html>