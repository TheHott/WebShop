<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="classes.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Авторизация</title>
</head>
<body>
<h1>Интернет-магазин компьютерной техники - Авторизация</h1>
<table>
	<tr>
		<td><a href="index.jsp">Домой</a></td>
		<td><a href="GetCatalog">Каталог</a></td>
		<td><a href="cart.jsp">Корзина</a></td>
		<td><b>Авторизация</b></td>
		<td><a href="signUp.jsp">Регистрация</a></td>
		<td><form action="search" method="get" autocomplete="off">
		<input type="search" name="req" placeholder="Найти товар" required>
		<input type="hidden" name="page" value="1">
		<input type="submit" value="Найти"></form></td>
	</tr>
</table>
<div class="login">
	<form action="CheckUser" method="post" autocomplete="off">
	<table>
	<tr>
		<td>Логин:</td>
		<td><input type="text" name="login" placeholder="Логин, e-mail или телефон" required></td>
	</tr>
	<tr>
		<td>Пароль:</td>
		<td><input type="password" name="password" placeholder="Пароль" required></td>
	</tr>
	</table>
	<small>
    	<input type="submit" name="OK" value="Войти">
    </small>
  	</form>
  	<c:if test="${not empty msg }">
  		<h3 style='color:red'>${msg}</h3>
  		<c:remove var="msg"/>
  	</c:if>
</div>
</body>
</html>