<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.kpfu.itis.dto.FieldErrorDto" %>
<%
    List<String> availableIcons = (List<String>) request.getAttribute("availableIcons");
    List<FieldErrorDto> errors = (List<FieldErrorDto>) request.getAttribute("errors");
%>
<html>
<head>
    <title>Создать категорию заработка</title>
    <style>
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
            border-color: #28a745;
            background-color: #f8fff9;
        }
        .icon-option.selected {
            border-color: #28a745;
            background-color: #e8f5e8;
            box-shadow: 0 0 5px rgba(40,167,69,0.5);
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
        .error {
            color: red;
            font-size: 14px;
            margin: 5px 0;
        }
    </style>
</head>
    <body>
    <h2>Создать категорию заработка</h2>

    <% if (errors != null && !errors.isEmpty()) { %>
    <div style="color: red; margin: 10px 0; padding: 10px; border: 1px solid red;">
        <% for (FieldErrorDto error : errors) { %>
        <div class="error"><%= error.getMessage() %></div>
        <% } %>
    </div>
    <% } %>

    <form action="create-income" method="post">
        <div>
            <label><strong>Название категории:</strong></label><br>
            <input type="text" name="name" required
                   style="width: 300px; padding: 8px; margin: 5px 0;"
                   placeholder="Введите название категории">
        </div>
        <br>

        <div>
            <label><strong>Выберите иконку:</strong></label><br>

            <div class="icons-container">
                <% if (availableIcons != null && !availableIcons.isEmpty()) { %>
                <% for (String icon : availableIcons) { %>
                <label class="icon-option">
                    <input type="radio" name="icon" value="<%= icon %>"
                           class="icon-radio" required>
                    <img src="${pageContext.request.contextPath}/static/icons/income/<%= icon %>?v=1.0"
                         alt="<%= icon %>"
                         onerror="this.style.display='none'">
                    <div class="icon-name"><%= icon %></div>
                </label>
                <% } %>
                <% } else { %>
                <div style="color: #666; font-style: italic;">
                    Иконки не найдены. Проверьте папку /static/icons/
                </div>
                <% } %>
            </div>

            <small style="color: #666;">* Выберите одну иконку из списка</small>
        </div>
        <br>

        <div>
            <button type="submit"
                    style="padding: 10px 20px; background: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer;">
                Создать категорию
            </button>
            <a href="profile"
               style="padding: 10px 20px; background: #6c757d; color: white; text-decoration: none; border-radius: 4px; margin-left: 10px;">
                Отмена
            </a>
        </div>
    </form>

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