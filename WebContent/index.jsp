<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="classes.Product"%>
<%@page import="classes.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Интернет-магазин компьютерной техники</title>
	<link rel="stylesheet" type="text/css" href="css/modal.css">
</head>
<body>
<div>
	<h1>Интернет-магазин компьютерной техники</h1>
	<table>
		<tr>
			<td><b>Домой</b></td>
			<td><a href="./catalog?page=1">Каталог</a></td>
			<td><a href="cart.jsp">Корзина</a></td>
			<c:if test="${user==null }"><td><a href="login.jsp">Авторизация</a></td>
			<td><a href="signUp.jsp">Регистрация</a></td></c:if>
			<c:if test="${user!=null }"><td><a href="myProfile.jsp">Профиль</a></td>
			<td>Добро пожаловать, <b>${user.login }</b></td>
			<td><a href="SignOutUser">Выйти</a></td></c:if>
			<td><form action="search" method="get" autocomplete="off">
			<input type="search" name="req" placeholder="Найти товар" required>
			<input type="hidden" name="page" value="1">
			<input type="submit" value="Найти"></form></td>
		</tr>
	</table>
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
</div>
	<br><a href="testingSite.jsp">Тест функций не для юзеров</a><br>
<c:if test="${not empty msg }">
	<h3>${msg}</h3>
	<c:remove var="msg"/>
</c:if>		
</div>
</body>
</html>