<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Создать категорию</title>
</head>
<body>
<h2>Создать категорию</h2>

<form action="create-expense" method="post">
    <div>
        <label>Название:</label><br>
        <input type="text" name="name" required>
    </div>
    <br>
    <div>
        <label>Фото (URL):</label><br>
        <input type="text" name="icon">
    </div>
    <br>
    <button type="submit">Создать</button>
    <a href="profile">Отмена</a>
</form>
</body>
</html>