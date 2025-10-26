<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.kpfu.itis.model.ExpenseCategoryEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.kpfu.itis.model.IncomeCategoryEntity" %>
<%
    IncomeCategoryEntity category = (IncomeCategoryEntity) request.getAttribute("category");
    List<String> availableIcons = (List<String>) request.getAttribute("availableIcons");
%>
<html>
<head>
    <title>Edit Expense Category</title>
    <style>
        .form-container {
            max-width: 500px;
            margin: 20px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .form-group input, .form-group select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .submit-btn {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .cancel-btn {
            background-color: #6c757d;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            margin-left: 10px;
        }
        .icons-container {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            margin: 10px 0;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            max-height: 300px;
            overflow-y: auto;
        }
        .icon-option {
            text-align: center;
            cursor: pointer;
            padding: 10px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            transition: all 0.3s ease;
            background: white;
        }
        .icon-option:hover {
            border-color: #007bff;
            background-color: #f8f9fa;
        }
        .icon-option.selected {
            border-color: #007bff;
            background-color: #e3f2fd;
            box-shadow: 0 0 5px rgba(0,123,255,0.5);
        }
        .icon-option img {
            width: 32px;
            height: 32px;
            display: block;
            margin: 0 auto 5px;
            object-fit: contain;
        }
        .icon-radio {
            display: none;
        }
        .icon-name {
            font-size: 11px;
            color: #666;
            max-width: 80px;
            word-break: break-all;
        }
    </style>
</head>
<body>
<h2>Edit Expense Category</h2>

<div class="form-container">
    <form method="post" action="<%= request.getContextPath() %>/income-category/update">
        <input type="hidden" name="uuid" value="<%= category.getId() %>">

        <div class="form-group">
            <label for="name">Category Name:</label>
            <input type="text" id="name" name="name" value="<%= category.getName() %>" required>
        </div>

        <div class="form-group">
            <label>Icon:</label>
            <div class="icons-container">
                <% if (availableIcons != null && !availableIcons.isEmpty()) { %>
                <% for (String icon : availableIcons) {
                    boolean isSelected = icon.equals(category.getIcon());
                %>
                <label class="icon-option <%= isSelected ? "selected" : "" %>">
                    <input type="radio" name="icon" value="<%= icon %>"
                           class="icon-radio" <%= isSelected ? "checked" : "" %>>
                    <img src="${pageContext.request.contextPath}/static/icons/income/<%= icon %>?v=1.0"
                         alt="<%= icon %>"
                         onerror="this.style.display='none'">
                    <div class="icon-name"><%= icon %></div>
                </label>
                <% } %>
                <% } else { %>
                <div style="color: #666; font-style: italic;">
                    No icons found
                </div>
                <% } %>
            </div>
        </div>

        <button type="submit" class="submit-btn">Update Category</button>
        <a href="<%= request.getContextPath() %>/profile" class="cancel-btn">Cancel</a>
    </form>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const radioButtons = document.querySelectorAll('.icon-radio');

        radioButtons.forEach(radio => {
            radio.addEventListener('change', function() {
                document.querySelectorAll('.icon-option').forEach(opt => {
                    opt.classList.remove('selected');
                });

                if (this.checked) {
                    this.parentElement.classList.add('selected');
                }
            });
        });

        document.querySelectorAll('.icon-option').forEach(option => {
            option.addEventListener('click', function(e) {
                const radio = this.querySelector('.icon-radio');
                if (radio) {
                    radio.checked = true;
                    radio.dispatchEvent(new Event('change'));
                }
            });
        });
    });
</script>
</body>
</html>