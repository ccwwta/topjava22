<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>User Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>

<h2><c:out value="${empty meal.id ? 'New' : 'Edit'}"/> meal</h2>

<form action="meals" method="post">
    <input type="hidden" name="id" value="${meal.id}">
    <table>
        <tbody>
        <tr>
            <td>Datetime</td>
            <td><label for="datetime">
                <input type="datetime-local" dataformatas="yyyy-MM-dd HH:mm" name="datetime" id="datetime"
                       value="${meal.dateTime}">
            </label></td>
        </tr>
        <tr>
            <td>Description</td>
            <td><label for="description">
                <input type="text" size="40" name="description" id="description" value="${meal.description}">
            </label></td>
        </tr>
        <tr>
            <td>Calories</td>
            <td><label for="calories">
                <input type="number" name="calories" id="calories" value="${meal.calories}">
            </label></td>
        </tr>
        </tbody>
    </table>
    <br>
    <input type="submit" value="Save">
    <button type="button" onclick="window.history.back();">Cancel</button>

</form>
</body>

</html>