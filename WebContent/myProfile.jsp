<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="classes.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Мой профиль</title>
</head>
<body>
<div class="profile">
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
</div>
<div>
<form action="GetUntakenOrders" method="post">
<input type="submit" value="Мои заказы">
</form>
<c:if test="${not empty msg }">
	<h3>${msg}</h3>
	<c:remove var="msg"/>
</c:if>
</div>
</body>
</html>