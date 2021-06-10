<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="classes.Product"%>
<%@page import="classes.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Моя корзина</title>
<link rel="stylesheet" type="text/css" href="css/modal.css">
</head>
<body>

<div class="header">
<h1>Интернет-магазин компьютерной техники - Корзина</h1>
<table>
	<tr>
		<td><a href="index.jsp">Домой</a></td>
		<td><a href="GetCatalog">Каталог</a></td>
		<td><b>Корзина</b></td>
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
</div> <!-- header ender -->

<div class="contents">
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
    		<td>Количество</td>
    		<td>Цена</td>
    		<td></td>
    	</tr>
    <c:forEach var="product" items="${cart.getProducts()}" varStatus="loop">
		<tr>
			<td>${product.ID }</td>
			<td><a href="GetProduct?id=${product.ID}">${product.name }</a></td>
			<td><form action="SetCartsProductAmount" method="post" autocomplete="off">
			<input type="submit" name="amountChange" value="=" id="eqBtn"> ← Эта кнопка не должна быть видна
			<input type="submit" name="amountChange" value="-">
			<input type="hidden" name="prodName" value="${product.name }"> 
			<input type="text" name="amount" value="${product.amount}" size="1" required>
			<input type="submit" name="amountChange" value="+"></form></td>
			<td>${product.costPerOne} руб.</td>
			<td><form action="DeleteCartProduct" method="post" autocomplete="off">
			<input type="hidden" name="prodName" value="${product.name }">
			<input type="submit" value="Удалить">
			</form></td>
		</tr>
		</c:forEach>
		</table>
		<c:if test="${user==null }"><button type="button" onclick='location.href="guestOrdering.jsp"'>Купить</button></c:if>
		<c:if test="${user!=null }"><form action="CreateOrder" method="post">
			<input type="submit" value="Купить">
		</form></c:if>
		TODO Написать скрипт чтоб не вводились символы<br>
		TODO При нажатии на + - или = кнопки должны не работать до перезагрузки страницы<br>
		JavaScript?<br>
		TODO Сделать подтверждение "уверены ли вы?" при нажатии на Купить<br>
</c:if>
<c:if test="${cart.getProducts().isEmpty()!=false }">
Корзина пустая!
</c:if>
</div>

<div class="errors">
	<c:if test="${not empty msg }">
		<h3>${msg}</h3>
		<c:remove var="msg"/>
	</c:if>		
</div>
</body>
</html>