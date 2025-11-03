<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Welcome Page</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/footer.css">
</head>
<body>
<div class="page-wrapper">
    <div></div>

    <div class="container">
        <h1 class="logo">MyCoin</h1>
        <p class="tagline">Умное управление финансами для вашего спокойствия</p>

        <div class="btn-group">
            <a href="${pageContext.request.contextPath}/sign-in" class="btn btn-primary">Войти</a>
            <a href="${pageContext.request.contextPath}/sign-up" class="btn btn-secondary">Зарегистрироваться</a>
        </div>
    </div>

    <div class="footer">
        Сайт разработан в рамках учебного проекта. GitHub
        <a href="https://github.com/ReZorDos" target="_blank">тык</a>
    </div>
</div>
</body>
</html>