<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Welcome Page</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/welcome.css">
</head>
<body>
    <h2>Добро пожаловать в систему управления финансами!</h2>

    <div class="button-container">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <button class="button profile" onclick="location.href='${pageContext.request.contextPath}/profile'">
                    Мой профиль
                </button>
                <button class="button logout" onclick="location.href='${pageContext.request.contextPath}/logout'">
                    Выйти
                </button>
            </c:when>
            <c:otherwise>
                <button class="button" onclick="location.href='${pageContext.request.contextPath}/sign-up'">
                    Регистрация
                </button>
                <button class="button sign-in" onclick="location.href='${pageContext.request.contextPath}/sign-in'">
                    Войти
                </button>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>