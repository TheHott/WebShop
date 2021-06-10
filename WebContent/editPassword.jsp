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
		<td><a href="GetCatalog">Каталог</a></td>
		<td><a href="cart.jsp">Корзина</a></td>
		<td><b>Профиль</b></td>
		<td>Добро пожаловать, <b>${user.login }</b></td>
		<td><a href="SignOutUser">Выйти</a></td>
		<td><form action="search" method="get" autocomplete="off">
		<input type="search" name="req" placeholder="Найти товар" required>
		<input type="hidden" name="page" value="1">
		<input type="submit" value="Найти"></form></td>
	</tr>
</table>
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
<c:if test="${not empty msg }">
	<h3>${msg}</h3>
	<c:remove var="msg"/>
</c:if>
</body>
</html>