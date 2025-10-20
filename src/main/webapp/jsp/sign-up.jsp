<%@ page import="ru.kpfu.itis.dto.FieldErrorDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up</title>
</head>
<body>

    <form action="${pageContext.request.contextPath}/sign-up" method="post">
        Nickname: <input type="text" name="nickname"> <br>
        Email: <input type="text" name="email"> <br>
        Password: <input type="text" name="password"> <br>
        <input type="submit" name="Sign up">
    </form>

    <% if (request.getAttribute("errors") != null ) {
        for (FieldErrorDto errorDto : (List<FieldErrorDto>) request.getAttribute("errors")) { %>
        <span style="color: red">Field <%=errorDto.getField()%>, erros <%=errorDto.getMessage()%> <br></span>
    <%    }
    } %>

</body>
</html>
