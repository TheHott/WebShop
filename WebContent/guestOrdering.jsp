<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="classes.Product"%>
<%@page import="classes.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
<title>Заказ</title>
</head>
<body>
<div>
<h1>Интернет-магазин компьютерной техники</h1>
<table>
	<tr>
		<td><a href="index.jsp">Домой</a></td>
		<td><a href="GetCatalog">Каталог</a></td>
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
<form action="CreateOrder" method="post" autocomplete="off">
Пожалуйста, введите адрес для доставки
<input type="text" name="address" name="Адрес" required>
<input type="submit" value="Купить">
</form>
<button type="button" onclick='location.href="cart.jsp"'>Назад</button>
<c:if test="${not empty msg }">
	<h3>${msg}</h3>
	<c:remove var="msg"/>
</c:if>
</div>
</body>
</html>