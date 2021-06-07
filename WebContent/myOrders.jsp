<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="classes.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Мои заказы</title>
</head>
<body>
<div>
<h1>Интернет-магазин компьютерной техники - Заказы</h1>
<table>
	<tr>
		<td><a href="index.jsp">Домой</a></td>
		<td><a href="GetCatalog">Каталог</a></td>
		<td><a href="cart.jsp">Корзина</a></td>
		<td><a href="myProfile.jsp">Профиль</a></td>
		<td>Добро пожаловать, <b>${user.login }</b></td>
		<td><a href="SignOutUser">Выйти</a></td>
		<td><form action="search" method="get" autocomplete="off">
		<input type="search" name="req" placeholder="Найти товар" required>
		<input type="hidden" name="page" value="1">
		<input type="submit" value="Найти"></form></td>
	</tr>
</table>
<table border="1">
	<tr>
		<td>ID</td>
		<td>Адрес доставки</td>
		<td>Дата заказа</td>
		<td>Получено?</td>
		<td>Оплачено?</td>
		<td>Суммарная стоимость</td>
	</tr>
	<c:forEach var="order" items="${orders}">
	<tr>
		<td>${order.ID}</td>
		<td>${order.deliveryAddress}</td>
		<td>${order.dateOfOrder}</td>
		<td>${order.deliveryState}</td>
		<td>${order.paymentState}</td>
		<td>${order.totalCost}</td>
		<td><form action="GetOrdersProducts" method="post">
		<input type="submit" value="Подробнее"><input type="hidden" name="orderID" value="${order.ID}"></form></td>
	</tr>
	</c:forEach>
</table>
<c:if test="${not empty msg }">
	<h3>${msg}</h3>
	<c:remove var="msg"/>
</c:if>
</div>
</body>
</html>