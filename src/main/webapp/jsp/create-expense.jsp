<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Создать категорию</title>
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
        .error {
            color: red;
            font-size: 14px;
            margin: 5px 0;
        }
    </style>
</head>
<body>
    <h2>Создать категорию расходов</h2>

    <c:if test="${not empty errors}">
        <div style="color: red; margin: 10px 0; padding: 10px; border: 1px solid red;">
            <c:forEach var="error" items="${errors}">
                <div class="error">${error.message}</div>
            </c:forEach>
        </div>
    </c:if>

    <form action="create-expense" method="post">
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
                <c:choose>
                    <c:when test="${not empty availableIcons}">
                        <c:forEach var="icon" items="${availableIcons}">
                            <label class="icon-option">
                                <input type="radio" name="icon" value="${icon}"
                                       class="icon-radio" required>
                                <img src="${pageContext.request.contextPath}/static/icons/expense/${icon}?v=1.0"
                                     alt="${icon}"
                                     onerror="this.style.display='none'">
                                <div class="icon-name">${icon}</div>
                            </label>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div style="color: #666; font-style: italic;">
                            Иконки не найдены. Проверьте папку /static/icons/expense
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <small style="color: #666;">* Выберите одну иконку из списка</small>
        </div>
        <br>

        <div>
            <button type="submit"
                    style="padding: 10px 20px; background: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer;">
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