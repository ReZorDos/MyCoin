<%@ page import="ru.kpfu.itis.dto.FieldErrorDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign In</title>
</head>
<body>

    <h1>Sign in Page</h1>

    <form action="${pageContext.request.contextPath}/sign-in" method="post">
        Email: <input type="text" name="email"><br>
        Password <input type="text" name="password"> <br>
        <input type="submit" value="Sign in">
    </form>

    <% if (request.getAttribute("errors") != null) {
        for (FieldErrorDto errorDto : (List<FieldErrorDto>) request.getAttribute("errors")) { %>
    <span style="color: red">Field <%=errorDto.getField()%>, errors <%=errorDto.getMessage()%> <br></span>
    <% }
    }%>

</body>
</html>
