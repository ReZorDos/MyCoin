<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Welcome Page</title>
    <style>
        .button-container {
            margin: 20px 0;
        }
        .button {
            display: inline-block;
            padding: 10px 20px;
            margin: 5px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .button:hover {
            background-color: #45a049;
        }
        .sign-in {
            background-color: #008CBA;
        }
        .sign-in:hover {
            background-color: #007B9A;
        }
        .profile {
            background-color: #f44336;
        }
        .profile:hover {
            background-color: #da190b;
        }
        .logout {
            background-color: #ff9800;
        }
        .logout:hover {
            background-color: #e68900;
        }
    </style>
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

    <c:if test="${not empty sessionScope.user}">
        <div style="margin: 20px 0; padding: 10px; background-color: #f8f9fa; border-radius: 4px;">
            <p>Добро пожаловать, <strong>${sessionScope.user.name}</strong>!</p>
        </div>
    </c:if>

</body>
</html>