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
        <div class="category-card" data-uuid="<%= category.getId() %>" data-type="expense">
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

            <div class="actions-menu">
                <button class="action-btn edit-btn" onclick="editCategory('<%= category.getId() %>', 'expense')">
                    Изменить
                </button>
                <button class="action-btn delete-btn" onclick="deleteCategory('<%= category.getId() %>', 'expense')">
                    Удалить
                </button>
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
        <div class="category-card" data-uuid="<%= category.getId() %>" data-type="income">
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

            <div class="actions-menu">
                <button class="action-btn edit-btn" onclick="editCategory('<%= category.getId() %>', 'income')">
                    Изменить
                </button>
                <button class="action-btn delete-btn" onclick="deleteCategory('<%= category.getId() %>', 'income')">
                    Удалить
                </button>
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
                    ? '<%= request.getContextPath() %>/expense-category/delete'
                    : '<%= request.getContextPath() %>/income-category/delete';

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
                ? '<%= request.getContextPath() %>/expense-category/update?uuid=' + uuid
                : '<%= request.getContextPath() %>/edit-income?uuid=' + uuid;
        }
    </script>
</body>
</html>