<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
    Your email: <%=request.getAttribute("email")%>

    <a href="${pageContext.request.contextPath}/sign-in">SIGN-IN</a><br>
    <a href="${pageContext.request.contextPath}/logout">LOGOUT</a><br>

</body>
</html>
