<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sign Up</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/auth.css">
</head>
<body>
<div class="auth-page">
    <h1>Sign Up Page</h1>

    <form class="auth-form" action="${pageContext.request.contextPath}/sign-up" method="post">
        <label>Nickname:
            <input class="auth-input" type="text" name="nickname" value="${param.nickname}"
                   placeholder="Enter your nickname">
        </label>

        <label>Email:
            <input class="auth-input" type="text" name="email" value="${param.email}"
                   placeholder="Enter your email">
        </label>

        <label>Password:
            <input class="auth-input" type="password" name="password"
                   placeholder="Enter your password">
        </label>

        <input class="auth-submit" type="submit" value="Sign up">
    </form>

    <c:if test="${not empty errors}">
        <div class="error-container">
            <c:forEach var="error" items="${errors}">
                <div class="error-item">
                    <strong>Field: ${error.field}</strong> - ${error.message}
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>
</body>
</html>