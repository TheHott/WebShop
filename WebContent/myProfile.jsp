<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="classes.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Мой профиль</title>
<link rel="stylesheet" type="text/css" href="css/modal.css">
</head>
<body>
<div class="header">
<h1>Интернет-магазин компьютерной техники - Информация профиля</h1>
<table>
	<tr>
		<td><a href="index.jsp">Домой</a></td>
		<td><a href="GetCatalog">Каталог</a></td>
		<td><a href="cart.jsp">Корзина</a></td>
		<td><b>Профиль</b></td>
		<c:if test="${user.role=='admin' }"><td><a href="adminPage.jsp">Администрирование</a></td></c:if>
		<td>Добро пожаловать, <b>${user.login }</b></td>
		<td><a href="SignOutUser">Выйти</a></td>
		<td><form action="search" method="get" autocomplete="off">
		<input type="search" name="req" placeholder="Найти товар" required>
		<input type="hidden" name="page" value="1">
		<input type="submit" value="Найти"></form></td>
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
	<table>
		<tr>
			<td>Логин</td>
			<td>${user.login }</td>
		</tr>
		<tr>
			<td>ФИО</td>
			<td>${user.FIO }</td>
		</tr>
		<tr>
			<td>Email</td>
			<td>${user.email }</td>
		</tr>
		<tr>
			<td>Телефон</td>
			<td>${user.phoneNumber }</td>
		</tr>
		<tr>
			<td>Адрес</td>
			<td>${user.address }</td>
		</tr>
 	</table>
 	<input type="button" name="edit" value="Редактировать" onClick="window.location.href='./editProfile.jsp'">
 	<input type="button" name="edit" value="Изменить пароль" onClick="window.location.href='./editPassword.jsp'">
<form action="GetUntakenOrders" method="post">
<input type="submit" value="Мои заказы">
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