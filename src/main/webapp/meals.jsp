<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <style type="text/css">
        .excess {
            color: red;
        }

        .normal {
            color: green;
        }
    </style>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>User meals</h2>

<a href="?action=new">Add meal</a>
<br><br>

<table border="1" cellspacing="0" cellpadding="5">
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan="2"></th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
        <jsp:useBean id="dateFormatter" scope="request" type="java.time.format.DateTimeFormatter"/>
        <tr class="${meal.excess ? 'excess' : 'normal'}">
            <td><c:out value="${dateFormatter.format(meal.dateTime)}"/></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>

            <td><a href="?id=${meal.id}&action=update">Update</a></td>

            <td><a href="?id=${meal.id}&action=delete">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>