<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sign Up</title>
    <style>
        .error {
            color: red;
            margin: 5px 0;
        }
        form {
            margin: 20px 0;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            max-width: 300px;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 8px;
            margin: 5px 0 15px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #008CBA;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #007B9A;
        }
    </style>
</head>
<body>

    <h1>Sign Up Page</h1>

    <form action="${pageContext.request.contextPath}/sign-up" method="post">
        <label>Nickname: <input type="text" name="nickname" value="${param.nickname}"></label><br>
        <label>Email: <input type="text" name="email" value="${param.email}"></label><br>
        <label>Password: <input type="password" name="password"></label><br>
        <input type="submit" value="Sign up">
    </form>

    <c:if test="${not empty errors}">
        <div style="color: red; margin: 10px 0;">
            <c:forEach var="error" items="${errors}">
                <div class="error">
                    Field: ${error.field}, Error: ${error.message}
                </div>
            </c:forEach>
        </div>
    </c:if>

</body>
</html>