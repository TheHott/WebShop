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
		<td>Добро пожаловать, <b>${user.login }</b></td>
		<td><a href="SignOutUser">Выйти</a></td>
		<td><form action="search" method="get" autocomplete="off">
		<input type="search" name="req" placeholder="Найти товар" required>
		<input type="hidden" name="page" value="1">
		<input type="submit" value="Найти"></form></td>
	</tr>
</table>
<form action="SetUsersInfo" method="post" autocomplete="off">
	<table>
		<tr>
			<td>Логин</td>
			<td><input type="text" name="newLogin" value='${user.login }' required></td>		
		</tr>
		<tr>
			<td>ФИО</td>
			<td><input type="text" name="fio" value='${user.FIO}'></td>
		</tr>
		<tr>
			<td>Email</td>
			<td><input type="text" name="email" value='${user.email}' required></td>
		</tr>
		<tr>
			<td>Телефон</td>
			<td><input type="text" name="phone" value='${user.phoneNumber}'></td>
		</tr>
		<tr>
			<td>Адрес</td>
			<td><input type="text" name="address" size="75" value='${user.address}'></td>
		</tr>
 	</table>
	<small>
    	<input type="submit" name="save" value="Сохранить">
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