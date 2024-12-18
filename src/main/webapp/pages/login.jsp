<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Авторизация</title>
    <link rel="icon" href="<%= request.getContextPath() %>/pictures/shrek.png" type="image/png">
    <style>
        body {
            background: linear-gradient(135deg, #1e90ff, #87ceeb); /* Градиентный фон */
            font-family: 'Arial', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .login-container {
            background: #fff;
            padding: 20px 40px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            text-align: center;
            width: 300px;
        }
        .login-container h1 {
            color: #333;
            margin-bottom: 20px;
        }
        .login-container label {
            display: block;
            text-align: left;
            margin: 10px 0 5px;
            color: #555;
        }
        .login-container input[type="text"],
        .login-container input[type="password"],
        .login-container input[type="number"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }
        .login-container input[type="submit"] {
            background: #1e90ff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background 0.3s ease;
        }
        .login-container input[type="submit"]:hover {
            background: #0077cc;
        }
        .login-container a {
            color: #1e90ff;
            text-decoration: none;
            font-size: 14px;
        }
        .login-container a:hover {
            text-decoration: underline;
        }
        .login-container img {
            max-width: 100px;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h1>Авторизация</h1>
        <a href="register.jsp">Еще не с нами?</a>
        <form action="<%= request.getContextPath() %>/auth" method="post">
            <input type="hidden" name="action" value="auth">
            <label for="login">Логин:</label>
            <input type="text" id="login" name="login" required>
            <label for="password">Пароль:</label>
            <input type="password" id="password" name="password" required>
            <label for="role">Роль (1 - Админ, 0 - Клиент):</label>
            <input type="number" id="role" name="role" required>
            <input type="submit" value="Войти">
        </form>
        <img src="<%= request.getContextPath() %>/pictures/elka.png" alt="Example Image">
    </div>
</body>
</html>