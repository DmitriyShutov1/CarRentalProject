<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Авторизация</title>
    <link rel="icon" href="<%= request.getContextPath() %>/pictures/shrek.png" type="image/png">
    <style>
        body {
            background-color: #ADD8E6; /* Светло-голубой цвет */
        }
    </style>
</head>
<body>
    <h1>Авторизация</h1>
    <a href="register.jsp">Еще не с нами?</a>
    <form action="<%= request.getContextPath() %>/auth" method="post">
	    <input type="hidden" name="action" value="auth">
	    <label for="login">Логин:</label>
	    <input type="text" id="login" name="login" required><br>
	    <label for="password">Пароль:</label>
	    <input type="password" id="password" name="password" required><br>
	    <label for="role">Роль (1 - Админ, 0 - Клиент):</label>
	    <input type="number" id="role" name="role" required><br>
	    <input type="submit" value="Войти">
	</form>
	
	<img src="<%= request.getContextPath() %>/pictures/elka.png" alt="Example Image" style="max-width: 100%; height: auto; margin-top: 20px;">
</body>
</html>
