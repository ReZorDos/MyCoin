<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Создать расходную транзакцию</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f5f5f5;
        }

        .container {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
        }

        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
            box-sizing: border-box;
        }

        input[type="text"]:focus,
        input[type="number"]:focus {
            outline: none;
            border-color: #4CAF50;
            box-shadow: 0 0 5px rgba(76, 175, 80, 0.3);
        }

        .btn {
            background-color: #4CAF50;
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            width: 100%;
            margin-top: 10px;
        }

        .btn:hover {
            background-color: #45a049;
        }

        .error-message {
            color: #d32f2f;
            background-color: #ffebee;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 15px;
            border-left: 4px solid #d32f2f;
        }

        .back-link {
            display: inline-block;
            margin-top: 15px;
            color: #666;
            text-decoration: none;
        }

        .back-link:hover {
            color: #333;
        }

        .category-info {
            background-color: #e8f5e8;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
            border-left: 4px solid #4CAF50;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Создать расходную транзакцию</h1>

        <c:if test="${not empty preselectedCategoryId}">
            <div class="category-info">
                <strong>💡 Транзакция будет создана для выбранной категории</strong>
                <input type="hidden" id="expenseId" name="expenseId" value="${preselectedCategoryId}">
            </div>
        </c:if>

        <c:if test="${not empty errors}">
            <div class="error-messages">
                <c:forEach var="error" items="${errors}">
                    <div class="error-message">
                        <strong>Ошибка:</strong>
                        <c:choose>
                            <c:when test="${not empty error.message}">
                                ${error.message}
                            </c:when>
                            <c:otherwise>
                                Неизвестная ошибка
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/create-transaction/expense" method="post">
            <div class="form-group">
                <label for="title">Название транзакции:</label>
                <input type="text" id="title" name="title" required
                       placeholder="Введите название транзакции (например: Покупка продуктов, Оплата коммунальных услуг)">
            </div>

            <div class="form-group">
                <label for="sum">Сумма:</label>
                <input type="number" id="sum" name="sum" required
                       min="0.01" step="0.01"
                       placeholder="Введите сумму в рублях">
            </div>

            <input type="hidden" name="expenseId" value="${preselectedCategoryId != null ? preselectedCategoryId : ''}">

            <button type="submit" class="btn">Создать транзакцию</button>
        </form>

        <a href="${pageContext.request.contextPath}/profile" class="back-link">← Вернуться в профиль</a>
    </div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const preselectedCategoryId = '${preselectedCategoryId != null ? preselectedCategoryId : ""}';

        if (!preselectedCategoryId) {
            console.warn('No category selected for transaction');
        }
    });
</script>
</body>
</html>