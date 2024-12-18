<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.Review" %>
<!DOCTYPE html>
<html>
<head>
    <title>Информация о заказах</title>
    <style>
        body {
            background-color: #FFB6C1; /* Светло-голубой цвет */
        }
    </style>
</head>
<body>
    <h1>Информация о заказах</h1>
    <form action="<%= request.getContextPath() %>/auth" method="post">
        <input type="hidden" name="action" value="showrevs">
        <input type="submit" value="Обновить">
    </form>
    <table border="1">
        <tr>
            <th>Client ID</th>
            <th>Car Model</th>
            <th>Review</th>
        </tr>
        <%
            List<Review> reviews = (List<Review>) request.getAttribute("reviews");
            if (reviews != null) {
                for (Review review : reviews) {
        %>
        <tr>
            <td><%= review.getClientId() %></td>
            <td><%= review.getCarModel() %></td>
            <td><%= review.getReviewText() %></td>
        </tr>
        <%
                }
            }
        %>
    </table>
    
    <h2>Забанить:</h2>
    <form action="<%= request.getContextPath() %>/auth" method="post">
        <input type="hidden" name="action" value="ban">
        <label for="clientId">ID клиента:</label>
        <input type="text" id="clientId" name="clientId" required>
        <input type="submit" value="Забанить">
    </form>

    <%
        String message = (String) request.getAttribute("message");
        if (message != null) {
    %>
    <p><%= message %></p>
    <%
        }
    %>
    <form action="<%= request.getContextPath() %>/auth" method="post" style="display:inline;">
	    <input type="hidden" name="action" value="toMenu">
	    <button type="submit">Вернуться в меню</button>
	</form>
</body>
</html>