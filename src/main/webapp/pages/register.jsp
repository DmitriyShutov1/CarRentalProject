<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Регистрация</title>
    <style>
        body {
            background-color: #ADD8E6; /* Светло-голубой цвет */
        }
    </style>
</head>
<body>
    <h1>Регистрация</h1>
    <form action="<%= request.getContextPath() %>/auth" method="post">
	    <input type="hidden" name="action" value="reg">
	    <label for="login">Логин:</label>
	    <input type="text" id="login" name="login" required><br>
	    <label for="password">Пароль:</label>
	    <input type="password" id="password" name="password" required><br>
	    <label for="phone">Телефон:</label>
	    <input type="text" id="phone" name="phone" required><br>
	    <label for="card">Номер карты:</label>
	    <input type="text" id="card" name="card" required><br>
	    <input type="submit" value="Зарегистрироваться">
	</form>
</body>
</html>