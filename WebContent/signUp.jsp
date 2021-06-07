<%@page import="classes.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Регистрация</title>
</head>
<body>
<h1>Интернет-магазин компьютерной техники - регистрация</h1>
<table>
	<tr>
		<td><a href="index.jsp">Домой</a></td>
		<td><a href="GetCatalog">Каталог</a></td>
		<td><a href="cart.jsp">Корзина</a></td>
		<td><a href="login.jsp">Авторизация</a></td>
		<td><b>Регистрация</b></td>
		<td><form action="search" method="get" autocomplete="off">
		<input type="search" name="req" placeholder="Найти товар" required>
		<input type="hidden" name="page" value="1">
		<input type="submit" value="Найти"></form></td>
	</tr>
</table>
<div class="signup">
	<form action="CreateUser" method="post" autocomplete="off">
	<table>
	<tr>
		<td>Логин:</td>
		<td><input type="text" name="login" required></td>
	</tr>
	<tr>
		<td>Пароль:</td>
		<td><input type="password" name="password" required></td>
	</tr>
	<tr>
		<td>Подтвердите пароль:</td>
		<td><input type="password" name="passwordCnfrm" required></td>
	</tr>
	<tr>
		<td>ФИО:</td>
		<td><input type="text" name="fio"></td>
	</tr>
	<tr>
		<td>Емейл:</td>
		<td><input type="text" name="email" required></td>
	</tr>
	<tr>
		<td>Номер телефона:</td>
		<td><input type="text" name="phone"></td>
	</tr>
    <tr>
		<td>Адрес:</td>
		<td><input type="text" name="address"></td>
	</tr>
    </table>
    	<p>
		<small>
    		<input type="submit" name="save" value="Зарегистрироваться">
    	</small>
 	</form>
</div>
</body>
</html>