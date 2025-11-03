<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit Income Category</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/forms.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/icons.css">
</head>
<body>
<div class="container">
    <h2>Edit Income Category</h2>

    <c:if test="${not empty errors}">
        <div class="error-container">
            <c:forEach var="error" items="${errors}">
                <div class="error-item">${error.message}</div>
            </c:forEach>
        </div>
    </c:if>
    <div class="form-container">
        <form method="post" action="${pageContext.request.contextPath}/income-category/update">
            <input type="hidden" name="uuid" value="${category.id}">

            <div class="form-group">
                <label for="name">Category Name:</label>
                <input class="form-input" type="text" id="name" name="name" value="${category.name}" required
                       placeholder="Enter category name">
            </div>

            <div class="form-group">
                <label>Icon:</label>
                <div class="icons-container">
                    <c:choose>
                        <c:when test="${not empty availableIcons}">
                            <c:forEach var="icon" items="${availableIcons}">
                                <c:set var="isSelected" value="${icon eq category.icon}" />
                                <label class="icon-option <c:if test='${isSelected}'>selected</c:if>">
                                    <input type="radio" name="icon" value="${icon}"
                                           class="icon-radio" <c:if test='${isSelected}'>checked</c:if> required>
                                    <img src="${pageContext.request.contextPath}/static/icons/income/${icon}?v=1.0"
                                         alt="${icon}"
                                         onerror="this.style.display='none'">
                                </label>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="no-icons-message">
                                Иконки не найдены
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div>
                <button type="submit" class="create-btn">Update Category</button>
                <a href="${pageContext.request.contextPath}/profile" class="cancel-btn">Cancel</a>
            </div>
        </form>
    </div>
</div>

<script src="${pageContext.request.contextPath}/static/js/icon-selection.js"></script>
</body>
</html>