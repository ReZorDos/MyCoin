<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Profile</title>
    <style>
        .categories-container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            margin: 20px 0;
        }
        .category-card {
            border: 1px solid #ddd;
            padding: 15px;
            border-radius: 12px;
            text-align: center;
            width: 140px;
            background: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            transition: all 0.3s ease;
            cursor: pointer;
            position: relative;
        }
        .category-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.15);
        }
        .category-card.active {
            border-color: #4CAF50;
            background-color: #f8fff8;
        }
        .actions-menu {
            position: absolute;
            top: 100%;
            left: 0;
            right: 0;
            background: white;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
            display: none;
            flex-direction: column;
            z-index: 10;
            margin-top: 5px;
        }
        .category-card.active .actions-menu {
            display: flex;
        }
        .action-btn {
            background: none;
            border: none;
            padding: 10px;
            cursor: pointer;
            text-align: center;
            font-size: 13px;
            transition: background-color 0.2s;
        }
        .action-btn:hover {
            background-color: #f5f5f5;
        }
        .delete-btn {
            color: #ff4444;
            border-bottom: 1px solid #eee;
        }
        .edit-btn {
            color: #2196F3;
        }
        .category-icon {
            width: 64px;
            height: 64px;
            object-fit: contain;
            margin-bottom: 10px;
            display: block;
            margin-left: auto;
            margin-right: auto;
        }
        .category-name {
            font-weight: bold;
            color: #333;
            font-size: 14px;
            word-wrap: break-word;
            margin-bottom: 5px;
        }
        .category-amount {
            font-size: 12px;
            color: #666;
            font-weight: normal;
        }
        .amount-value {
            font-weight: bold;
            color: #2c3e50;
        }
        .add-btn {
            background-color: #4CAF50;
            color: white;
            padding: 12px 20px;
            text-decoration: none;
            border-radius: 6px;
            display: inline-block;
            margin: 10px 0;
            font-weight: bold;
        }
        .add-income-btn {
            background-color: #2196F3;
            color: white;
            padding: 12px 20px;
            text-decoration: none;
            border-radius: 6px;
            display: inline-block;
            margin: 10px 10px 10px 0;
            font-weight: bold;
        }
        .no-icon {
            width: 64px;
            height: 64px;
            background-color: #f5f5f5;
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 10px;
            color: #999;
            font-size: 12px;
        }
        .section-title {
            color: #333;
            margin-top: 30px;
            margin-bottom: 15px;
            padding-bottom: 5px;
            border-bottom: 2px solid #eee;
        }
        .transaction-btn {
            color: #4CAF50;
            border-bottom: 1px solid #eee;
        }

        .balance-container {
            margin: 20px 0;
        }

        .balance-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
            max-width: 300px;
        }

        .balance-label {
            font-size: 14px;
            opacity: 0.9;
            margin-bottom: 5px;
        }

        .balance-amount {
            font-size: 24px;
            font-weight: bold;
        }
    </style>
</head>
<body>
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
            <p style="color: #666; font-style: italic; margin: 20px 0;">
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
            <p style="color: #666; font-style: italic; margin: 20px 0;">
                No income categories yet. <a href="${pageContext.request.contextPath}/create-income">Create your first one!</a>
            </p>
        </c:otherwise>
    </c:choose>

    <br>
    <a href="${pageContext.request.contextPath}/sign-in">SIGN-IN</a><br>
    <a href="${pageContext.request.contextPath}/logout">LOGOUT</a><br>

<script>
    let activeCard = null;

    document.addEventListener('DOMContentLoaded', function() {
        const images = document.querySelectorAll('.category-icon');
        images.forEach(img => {
            img.addEventListener('error', function() {
                this.style.display = 'none';
                const noIcon = this.nextElementSibling;
                if (noIcon && noIcon.classList.contains('no-icon')) {
                    noIcon.style.display = 'flex';
                }
            });
        });

        document.querySelectorAll('.category-card').forEach(card => {
            card.addEventListener('click', function(e) {
                if (e.target.closest('.actions-menu')) {
                    return;
                }

                if (activeCard && activeCard !== this) {
                    activeCard.classList.remove('active');
                }

                this.classList.toggle('active');

                if (this.classList.contains('active')) {
                    activeCard = this;
                } else {
                    activeCard = null;
                }
            });
        });

        document.addEventListener('click', function(e) {
            if (activeCard && !activeCard.contains(e.target)) {
                activeCard.classList.remove('active');
                activeCard = null;
            }
        });
    });

    function deleteCategory(uuid, type) {
        if (confirm('Вы уверены, что хотите удалить эту категорию?')) {
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = type === 'expense'
                ? '${pageContext.request.contextPath}/expense-category/delete'
                : '${pageContext.request.contextPath}/income-category/delete';

            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'uuid';
            input.value = uuid;

            form.appendChild(input);
            document.body.appendChild(form);
            form.submit();
        }
    }

    function editCategory(uuid, type) {
        window.location.href = type === 'expense'
            ? '${pageContext.request.contextPath}/expense-category/update?uuid=' + uuid
            : '${pageContext.request.contextPath}/income-category/update?uuid=' + uuid;
    }

    function createTransaction(categoryId, type) {
        if (type === 'expense') {
            window.location.href = '${pageContext.request.contextPath}/create-transaction/expense?categoryId=' + categoryId;
        } else {
            window.location.href = '${pageContext.request.contextPath}/create-transaction/income?categoryId=' + categoryId;
        }
    }
</script>
</body>
</html>