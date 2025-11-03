<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sign In</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/auth.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/footer.css">
</head>
<body>
<div class="auth-page">
    <h1>Sign in</h1>

    <form class="auth-form" action="${pageContext.request.contextPath}/sign-in" method="post">
        <label>Email:
            <input class="auth-input" type="text" name="email" value="${param.email}"
                   placeholder="Enter your email">
        </label>
        <label>Password:
            <input class="auth-input" type="password" name="password"
                   placeholder="Enter your password">
        </label>
        <input class="auth-submit" type="submit" value="Sign in">
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
    <div class="footer">
        Сайт разработан в рамках учебного проекта. GitHub
        <a href="https://github.com/ReZorDos" target="_blank">тык</a>
    </div>
</div>
</body>
</html>