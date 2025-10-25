<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.kpfu.itis.model.ExpenseCategoryEntity" %>
<%@ page import="ru.kpfu.itis.model.IncomeCategoryEntity" %>
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
        }
        .category-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.15);
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
            transition: background-color 0.3s;
        }
        .add-btn:hover {
            background-color: #45a049;
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
            transition: background-color 0.3s;
        }
        .add-income-btn:hover {
            background-color: #1976D2;
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
    </style>
</head>
<body>
    <h2>Profile</h2>
    <p>Your email: <%= request.getAttribute("email") %></p>

    <div>
        <a href="<%= request.getContextPath() %>/create-expense" class="add-btn">
            + Add New Expense Category
        </a>
        <a href="<%= request.getContextPath() %>/create-income" class="add-income-btn">
            + Add New Income Category
        </a>
    </div>

    <%
        List<ExpenseCategoryEntity> expenseCategories = (List<ExpenseCategoryEntity>) request.getAttribute("expenseCategories");
        if (expenseCategories != null && !expenseCategories.isEmpty()) {
    %>
    <h3 class="section-title">Your Expense Categories:</h3>

    <div class="categories-container">
        <%
            for (ExpenseCategoryEntity category : expenseCategories) {
                String iconName = category.getIcon();
                Double totalAmount = category.getTotalAmount();
        %>
        <div class="category-card">
            <% if (iconName != null && !iconName.isEmpty()) { %>
            <img src="${pageContext.request.contextPath}/static/icons/expense/<%= iconName %>?v=1.0"
                 alt="<%= category.getName() %> icon"
                 class="category-icon"
                 onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">
            <div class="no-icon" style="display: none;">No Icon</div>
            <% } else { %>
            <div class="no-icon">No Icon</div>
            <% } %>
            <div class="category-name"><%= category.getName() %></div>
            <div class="category-amount">
                Total: <span class="amount-value"><%= String.format("%.2f", totalAmount) %></span>
            </div>
        </div>
        <%
            }
        %>
    </div>
    <%
    } else {
    %>
    <p style="color: #666; font-style: italic; margin: 20px 0;">
        No expense categories yet. <a href="<%= request.getContextPath() %>/create-expense">Create your first one!</a>
    </p>
    <%
        }
    %>

    <%
        List<IncomeCategoryEntity> incomeCategories = (List<IncomeCategoryEntity>) request.getAttribute("incomeCategories");
        if (incomeCategories != null && !incomeCategories.isEmpty()) {
    %>
    <h3 class="section-title">Your Income Categories:</h3>

    <div class="categories-container">
        <%
            for (IncomeCategoryEntity category : incomeCategories) {
                String iconName = category.getIcon();
                Double totalAmount = category.getTotalAmount();
        %>
        <div class="category-card">
            <% if (iconName != null && !iconName.isEmpty()) { %>
            <img src="${pageContext.request.contextPath}/static/icons/income/<%= iconName %>?v=1.0"
                 alt="<%= category.getName() %> icon"
                 class="category-icon"
                 onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">
            <div class="no-icon" style="display: none;">No Icon</div>
            <% } else { %>
            <div class="no-icon">No Icon</div>
            <% } %>
            <div class="category-name"><%= category.getName() %></div>
            <div class="category-amount">
                Total: <span class="amount-value"><%= String.format("%.2f", totalAmount) %></span>
            </div>
        </div>
        <%
            }
        %>
    </div>
    <%
    } else {
    %>
    <p style="color: #666; font-style: italic; margin: 20px 0;">
        No income categories yet. <a href="<%= request.getContextPath() %>/create-income">Create your first one!</a>
    </p>
    <%
        }
    %>

    <br>
    <a href="<%= request.getContextPath() %>/sign-in">SIGN-IN</a><br>
    <a href="<%= request.getContextPath() %>/logout">LOGOUT</a><br>

    <script>
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
        });
    </script>
</body>
</html>