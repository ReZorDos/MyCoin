<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
  <title>Ошибка</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/forms.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/error.css">
</head>
<body>
<div class="error-page">
  <div class="error-card">
    <div class="error-code">
      Ошибка ${not empty statusCode ? statusCode : '500'}
    </div>

    <h2 class="error-title">Что-то пошло не так...</h2>

    <div class="error-message">
      <strong>Сообщение:</strong><br>
      ${not empty errorMessage ? errorMessage : 'Неизвестная ошибка'}
    </div>

    <div class="error-details">
      <div class="detail-item">
        <span class="detail-label">Код ошибки:</span>
        <span class="detail-value">${not empty statusCode ? statusCode : 'Неизвестно'}</span>
      </div>
      <c:if test="${not empty requestUri}">
        <div class="detail-item">
          <span class="detail-label">Запрошенный адрес:</span>
          <span class="detail-value">${requestUri}</span>
        </div>
      </c:if>
    </div>

    <div class="action-buttons">
      <a href="${pageContext.request.contextPath}/" >
        На главную
      </a>
    </div>
  </div>
</div>
</body>
</html>