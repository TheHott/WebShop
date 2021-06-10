<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="classes.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- WIP не сделал -->
<html>
<head>
<meta charset="UTF-8">
<title>Страница администратора</title>
<link rel="stylesheet" type="text/css" href="css/modal.css">
</head>
<body>

<div class="header">
<h1>Страница администратора</h1>
<table>
	<tr>
		<td><a href="/WebShop/index.jsp">Домой</a></td>
		<td><a href="/WebShop/catalog?page=1">Каталог</a></td>
		<td><a href="/WebShop/cart.jsp">Корзина</a></td>
		<td><a href="/WebShop/myProfile.jsp">Профиль</a></td>
		<td>Добро пожаловать, <b>${user.login }</b></td>
		<td><a href="/WebShop/SignOutUser">Выйти</a></td>
	</tr>
</table>
</div> <!-- header ender -->

<!-- Корзина -->
<button id="myBtn">Открыть корзину</button>
<div id="myModal" class="modal">
<div class="modal-content">
    <span class="close">&times;</span>
    <c:if test="${cart.getProducts().isEmpty()==false }">
    <table border="1">
    	<tr>
    		<td>ID корзины</td>
    		<td>Логин владельца</td>
    		<td>Сумма</td>
    	</tr>
    	<tr>
    		<td>${cart.id }</td>
    		<td>${cart.ownersLogin }</td>
    		<td>${cart.totalCost } руб.</td>
    	</tr>
    </table>
    <table border="1">
    	<tr>
    		<td>ID товара</td>
    		<td>Наименование</td>
    		<td>Цена</td>
    		<td>Количество</td>
    		<td></td>
    	</tr>
    <c:forEach var="product" items="${cart.getProducts()}">
		<tr>
			<td>${product.ID }</td>
			<td><a href="GetProduct?id=${product.ID}">${product.name }</a></td>
			<td>${product.costPerOne} руб.</td>
			<td>${product.amount} шт.</td>
			<td><form action="DeleteCartProduct" method="post">
			<input type="hidden" name="prodName" value="${product.name }">
			<input type="submit" value="Удалить">
			</form></td>
		</tr>
		</c:forEach>
		</table>
		<button onclick='location.href="cart.jsp"'>Перейти в корзину</button>
		</c:if>
		<c:if test="${cart.getProducts().isEmpty()!=false }">
			Корзина пустая!
		</c:if>
</div>
	<script type="text/javascript" src="scripts/miniCart.js"></script>
</div> <!-- корзина ender -->

<div class="contents">
<h1>Получить информацию о заказе</h1>
<form action="GetOrder" method="post" autocomplete="off">
<input type="text" name="login" size="10" placeholder="Логин" value="${param.login }" required>
<input class="rounded-input"  type="submit"  value="Получить заказ" /><br>
</form>
<c:if test="${usersOrders.isEmpty()==false }">
	<table border="1">
	<tr>
		<td>ID</td>
		<td>Адрес доставки</td>
		<td>Дата заказа</td>
		<td>Получено?</td>
		<td>Оплачено?</td>
		<td>Суммарная стоимость</td>
	</tr>
	<c:forEach var="order" items="${usersOrders}">
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
</c:if>
</div>
<div><form action="/WebShop/SetOrder" method="post" autocomplete="off">
<h1>Обновить данные о заказе</h1>
<table>
<tr>
	<td>ID заказа для изменения:</td>
	<td><input type="text" name="orderID" required></td>
</tr>
<tr>
	<td>Стоимость:</td>
	<td><input type="text" name="totalCost"></td>
</tr>
<tr>
	<td>Адрес:</td>
	<td><input type="text" name="address"></td>
</tr>
<tr>
	<td>Забран?</td>
	<td><input type="radio" name="isTaken" value="true">Да<input type="radio" name="isTaken" value="false" checked>Нет</td>
</tr>
<tr>
	<td>Оплачен?</td>
	<td><input type="radio" name="isPaid" value="true">Да<input type="radio" name="isPaid" value="false" checked>Нет</td>
</tr>
</table>
<p>
<small>
	<input type="submit" name="save" value="Обновить">
</small>
</form>
</div>

<div class="errors">
	<c:if test="${not empty msg }">
		<h3>${msg}</h3>
		<c:remove var="msg"/>
	</c:if>		
</div>
</body>
</html>