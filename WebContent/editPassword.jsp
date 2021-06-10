<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="classes.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Изменить пароль</title>
<link rel="stylesheet" type="text/css" href="css/modal.css">
</head>
<body>

<div class="header">
<h1>Интернет-магазин компьютерной техники - Изменить пароль</h1>
<table>
	<tr>
		<td><a href="index.jsp">Домой</a></td>
		<td><a href="./catalog?page=1&sort=IDHigh2Low">Каталог</a></td>
		<td><a href="cart.jsp">Корзина</a></td>
		<td><b>Профиль</b></td>
		<td>Добро пожаловать, <b>${user.login }</b></td>
		<td><a href="SignOutUser">Выйти</a></td>
		<td><form action="search" method="get" autocomplete="off">
		<input type="search" name="req" placeholder="Найти товар" required>
		<input type="hidden" name="page" value="1">
		<input type="submit" value="Найти"></form></td>
		<td><button class="cart-btn" id="myBtn"></button></td>
	</tr>
</table>
</div> <!-- header ender -->

<!-- Корзина -->
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
    </table><br>
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
<form action="SetUsersPassword" method="post" autocomplete="off">
	<table>
	<tr>
		<td>Ваш текущий пароль</td>
		<td><input type="password" name="oldPass" placeholder="Текущий пароль" required></td>
	</tr>
	<tr>
		<td>Новый пароль</td>
		<td><input type="password" name="newPass" placeholder="Новый пароль" required></td>
	</tr>
	<tr>
		<td>Повторите новый пароль</td>
		<td><input type="password" name="newPassCnfrm" placeholder="Повторите новый пароль" required></td>
	</tr>
	</table>
	<small>
    	<input type="submit" name="save" value="Изменить">
    	<input type="button" name="cancel" value="Отменить" onClick="window.location.href='./myProfile.jsp'">
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