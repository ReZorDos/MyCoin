<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>MyCoin</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/profile.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/footer.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/head.css">
</head>
<body>
    <header class="top-header">
        <div class="app-container">
            <div class="header-content">
                <a href="${pageContext.request.contextPath}/profile" class="site-title">
                    MyCoin
                </a>
                <nav class="header-nav">
                    <a href="${pageContext.request.contextPath}/all-transactions" class="nav-link">
                        Все транзакции
                    </a>
                    <a href="${pageContext.request.contextPath}/analyze/overview" class="nav-link">
                        Общий анализ
                    </a>
                    <a href="${pageContext.request.contextPath}/analyze-expense/day" class="nav-link">
                        Анализ расходов
                    </a>
                    <a href="${pageContext.request.contextPath}/analyze-income/day" class="nav-link">
                        Анализ доходов
                    </a>
                    <a href="${pageContext.request.contextPath}/logout" class="nav-link logout">
                        Выход
                    </a>
                </nav>
            </div>
        </div>
    </header>

    <div class="app-container">
        <div class="main-content">
            <div class="top-info-row">
                <section class="user-info-section">
                    <p class="user-email">Name: ${email}</p>
                </section>

                <section class="balance-section">
                    <div class="balance-card">
                        <div class="balance-info">
                            <span class="balance-label">Текущий баланс</span>
                            <span class="balance-amount">
                                <fmt:formatNumber value="${userBalance}" pattern="#,##0.00"/> ₽
                            </span>
                        </div>
                        <div class="balance-trend">
                        </div>
                    </div>
                </section>
            </div>

            <section class="actions-section">
                <div class="actions-grid">
                    <a href="${pageContext.request.contextPath}/create-expense" class="action-card">
                        <div class="action-icon expense">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                <path d="M12 6V18M6 12H18" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                            </svg>
                        </div>
                        <span>Add Expense Category</span>
                    </a>
                    <a href="${pageContext.request.contextPath}/create-income" class="action-card">
                        <div class="action-icon income">
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                <path d="M12 6V18M6 12H18" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                            </svg>
                        </div>
                        <span>Add Income Category</span>
                    </a>
                </div>
            </section>
            <div class="categories-sections">
                <section class="category-section">
                    <div class="section-header">
                        <h2 class="section-title">Expense Categories</h2>
                        <span class="category-count">${not empty expenseCategories ? expenseCategories.size() : 0}</span>
                    </div>

                    <c:choose>
                        <c:when test="${not empty expenseCategories}">
                            <div class="categories-grid">
                                <c:forEach var="category" items="${expenseCategories}">
                                    <div class="category-card" data-uuid="${category.id}" data-type="expense">
                                        <div class="category-header">
                                            <div class="category-icon-wrapper">
                                                <c:choose>
                                                    <c:when test="${not empty category.icon}">
                                                        <img src="${pageContext.request.contextPath}/static/icons/expense/${category.icon}?v=1.0"
                                                             alt="${category.name}"
                                                             class="category-icon"
                                                             onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">
                                                        <div class="icon-fallback" style="display: none;">
                                                                ${category.name.substring(0, 1).toUpperCase()}
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="icon-fallback">
                                                                ${category.name.substring(0, 1).toUpperCase()}
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="category-info">
                                                <h3 class="category-name">${category.name}</h3>
                                                <div class="category-amount">
                                                    <fmt:formatNumber value="${category.totalAmount}" pattern="#,##0.00"/> ₽
                                                </div>
                                                <div class="category-actions">
                                                    <button class="icon-btn" onclick="editCategory('${category.id}', 'expense')" title="Edit">
                                                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                                            <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7" stroke="currentColor" stroke-width="2"/>
                                                            <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z" stroke="currentColor" stroke-width="2"/>
                                                        </svg>
                                                    </button>
                                                    <button class="icon-btn" onclick="deleteCategory('${category.id}', 'expense')" title="Delete">
                                                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                                            <path d="M3 6h18M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2" stroke="currentColor" stroke-width="2"/>
                                                        </svg>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <button class="transaction-btn" onclick="createTransaction('${category.id}', 'expense')">
                                            + Add Transaction
                                        </button>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="empty-state">
                                <div class="empty-icon">💸</div>
                                <h3>No expense categories yet</h3>
                                <p>Start by creating your first expense category</p>
                                <a href="${pageContext.request.contextPath}/create-expense" class="btn-primary">
                                    Create Expense Category
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </section>

                <section class="category-section">
                    <div class="section-header">
                        <h2 class="section-title">Income Categories</h2>
                        <span class="category-count">${not empty incomeCategories ? incomeCategories.size() : 0}</span>
                    </div>

                    <c:choose>
                        <c:when test="${not empty incomeCategories}">
                            <div class="categories-grid">
                                <c:forEach var="category" items="${incomeCategories}">
                                    <div class="category-card" data-uuid="${category.id}" data-type="income">
                                        <div class="category-header">
                                            <div class="category-icon-wrapper">
                                                <c:choose>
                                                    <c:when test="${not empty category.icon}">
                                                        <img src="${pageContext.request.contextPath}/static/icons/income/${category.icon}?v=1.0"
                                                             alt="${category.name}"
                                                             class="category-icon"
                                                             onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">
                                                        <div class="icon-fallback" style="display: none;">
                                                                ${category.name.substring(0, 1).toUpperCase()}
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="icon-fallback">
                                                                ${category.name.substring(0, 1).toUpperCase()}
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="category-info">
                                                <h3 class="category-name">${category.name}</h3>
                                                <div class="category-amount">
                                                    <fmt:formatNumber value="${category.totalAmount}" pattern="#,##0.00"/> ₽
                                                </div>
                                                <div class="category-actions">
                                                    <button class="icon-btn" onclick="editCategory('${category.id}', 'income')" title="Edit">
                                                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                                            <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7" stroke="currentColor" stroke-width="2"/>
                                                            <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z" stroke="currentColor" stroke-width="2"/>
                                                        </svg>
                                                    </button>
                                                    <button class="icon-btn" onclick="deleteCategory('${category.id}', 'income')" title="Delete">
                                                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                                            <path d="M3 6h18M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2" stroke="currentColor" stroke-width="2"/>
                                                        </svg>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <button class="transaction-btn" onclick="createTransaction('${category.id}', 'income')">
                                            Add Transaction
                                            <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                                <path d="M5 12h14m-7-7l7 7-7 7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                            </svg>
                                        </button>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="empty-state">
                                <div class="empty-icon">💰</div>
                                <h3>No income categories yet</h3>
                                <p>Start by creating your first income category</p>
                                <a href="${pageContext.request.contextPath}/create-income" class="btn-primary">
                                    Create Income Category
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </section>
            </div>
        </div>
        <div class="footer">
            Сайт разработан в рамках учебного проекта. GitHub
            <a href="https://github.com/ReZorDos" target="_blank">тык</a>
        </div>
    </div>

<script>
    const contextPath = '${pageContext.request.contextPath}';
</script>
<script src="${pageContext.request.contextPath}/static/js/profile.js"></script>
</body>
</html>