<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome Page</title>
    <style>
        .button-container {
            margin: 20px 0;
        }
        .button {
            display: inline-block;
            padding: 10px 20px;
            margin: 5px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        .button:hover {
            background-color: #45a049;
        }
        .sign-in {
            background-color: #008CBA;
        }
        .sign-in:hover {
            background-color: #007B9A;
        }
        .profile {
            background-color: #f44336;
        }
        .profile:hover {
            background-color: #da190b;
        }
    </style>
</head>
<body>
<h2>Hello from WelcomePage</h2>

<div class="button-container">
    <button class="button" onclick="location.href='${pageContext.request.contextPath}/sign-up'">Sign Up</button>
    <button class="button sign-in" onclick="location.href='${pageContext.request.contextPath}/sign-in'">Sign In</button>
    <button class="button profile" onclick="location.href='/profile'">Profile</button>
</div>

</body>
</html>