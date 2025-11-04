<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create Saving Goal</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/forms.css">
</head>
<body>
<div class="container">
    <h2>Create New Saving Goal</h2>

    <c:if test="${not empty errors}">
        <div class="error-container">
            <c:forEach var="error" items="${errors}">
                <div class="error-item">${error.message}</div>
            </c:forEach>
        </div>
    </c:if>

    <div class="form-container">
        <form action="${pageContext.request.contextPath}/create-saving-goal" method="post">
            <div class="form-group">
                <label for="name">Goal Name:</label>
                <input class="form-input" type="text" id="name" name="name" required
                       placeholder="Enter goal name">
            </div>

            <div class="form-group">
                <label for="title">Description:</label>
                <input class="form-input" type="text" id="title" name="title" required
                       placeholder="Enter goal description">
            </div>

            <div class="form-group">
                <label for="total_amount">Target Amount:</label>
                <input class="form-input" type="number" id="total_amount" name="total_amount"
                       step="0.01" min="0.01" required
                       placeholder="Enter target amount">
            </div>

            <div>
                <button type="submit" class="create-btn">Create Goal</button>
                <a href="${pageContext.request.contextPath}/profile" class="cancel-btn">Cancel</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>