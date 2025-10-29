<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/proflie.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/categories.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/action.css">
</head>
<body>
<div class="profile-container">
    <h2>Profile</h2>
    <p>Your email: ${email}</p>

    <div class="balance-container">
        <div class="balance-card">
            <div class="balance-label">Current Balance</div>
            <div class="balance-amount">
                <fmt:formatNumber value="${userBalance}" pattern="#,##0.00"/> ₽
            </div>
        </div>
    </div>

    <div>
        <a href="${pageContext.request.contextPath}/create-expense" class="add-btn">
            + Add New Expense Category
        </a>
        <a href="${pageContext.request.contextPath}/create-income" class="add-income-btn">
            + Add New Income Category
        </a>
    </div>

    <c:choose>
        <c:when test="${not empty expenseCategories}">
            <h3 class="section-title">Your Expense Categories:</h3>
            <div class="categories-container">
                <c:forEach var="category" items="${expenseCategories}">
                    <div class="category-card" data-uuid="${category.id}" data-type="expense">
                        <c:choose>
                            <c:when test="${not empty category.icon}">
                                <img src="${pageContext.request.contextPath}/static/icons/expense/${category.icon}?v=1.0"
                                     alt="${category.name} icon"
                                     class="category-icon"
                                     onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">
                                <div class="no-icon" style="display: none;">No Icon</div>
                            </c:when>
                            <c:otherwise>
                                <div class="no-icon">No Icon</div>
                            </c:otherwise>
                        </c:choose>
                        <div class="category-name">${category.name}</div>
                        <div class="category-amount">
                            Total: <span class="amount-value"><fmt:formatNumber value="${category.totalAmount}" pattern="#.##"/></span>
                        </div>

                        <div class="actions-menu">
                            <button class="action-btn edit-btn" onclick="editCategory('${category.id}', 'expense')">
                                Изменить
                            </button>
                            <button class="action-btn transaction-btn" onclick="createTransaction('${category.id}', 'expense')">
                                Создать транзакцию
                            </button>
                            <button class="action-btn delete-btn" onclick="deleteCategory('${category.id}', 'expense')">
                                Удалить
                            </button>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <p class="empty-message">
                No expense categories yet. <a href="${pageContext.request.contextPath}/create-expense">Create your first one!</a>
            </p>
        </c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${not empty incomeCategories}">
            <h3 class="section-title">Your Income Categories:</h3>
            <div class="categories-container">
                <c:forEach var="category" items="${incomeCategories}">
                    <div class="category-card" data-uuid="${category.id}" data-type="income">
                        <c:choose>
                            <c:when test="${not empty category.icon}">
                                <img src="${pageContext.request.contextPath}/static/icons/income/${category.icon}?v=1.0"
                                     alt="${category.name} icon"
                                     class="category-icon"
                                     onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">
                                <div class="no-icon" style="display: none;">No Icon</div>
                            </c:when>
                            <c:otherwise>
                                <div class="no-icon">No Icon</div>
                            </c:otherwise>
                        </c:choose>
                        <div class="category-name">${category.name}</div>
                        <div class="category-amount">
                            Total: <span class="amount-value"><fmt:formatNumber value="${category.totalAmount}" pattern="#.##"/></span>
                        </div>

                        <div class="actions-menu">
                            <button class="action-btn edit-btn" onclick="editCategory('${category.id}', 'income')">
                                Изменить
                            </button>
                            <button class="action-btn transaction-btn" onclick="createTransaction('${category.id}', 'income')">
                                Создать транзакцию
                            </button>
                            <button class="action-btn delete-btn" onclick="deleteCategory('${category.id}', 'income')">
                                Удалить
                            </button>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <p class="empty-message">
                No income categories yet. <a href="${pageContext.request.contextPath}/create-income">Create your first one!</a>
            </p>
        </c:otherwise>
    </c:choose>

    <br>
    <a href="${pageContext.request.contextPath}/sign-in">SIGN-IN</a><br>
    <a href="${pageContext.request.contextPath}/logout">LOGOUT</a><br>
</div>

<script>
    const contextPath = '${pageContext.request.contextPath}';
</script>
<script src="${pageContext.request.contextPath}/static/js/profile.js"></script>
</body>
</html>