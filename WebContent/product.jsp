<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="classes.Product"%>
<%@page import="classes.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
<title>${product.name }</title>
<link rel="stylesheet" type="text/css" href="css/modal.css">
</head>
<body>
<div>
<h1>Интернет-магазин компьютерной техники - ${product.name}</h1>
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
<!-- Корзина -->
<button id="myBtn">Открыть корзину</button>
<div id="myModal" class="modal">

  <!-- Modal content -->
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
<br>Изображение<br>
<a href="${product.picLink}"><img src="${product.picLink}" width="100" height="100" border=2></a>
<table border="1">
		<tr>
			<td>Название</td>
			<td>Цена</td>
			<td>Кол-во на складе</td>
			<td>Категория</td>
			<td></td>
		</tr>
		<tr>
			<td>${product.name }</td>
			<td>${product.costPerOne} руб.</td>
			<td>${product.amount} шт.</td>
			<td><a href="GetCategoryProducts?id=${product.category.ID}&page=1">${product.category.name}</a></td>
			<td><c:if test="${cart.containsProduct(product)==false || cart==null}">
			<form action="AddProductToCart" method="post">
			<input type="hidden" name="prodID" value="${product.ID }">
			<input type="submit" value="Добавить в корзину">
			</form></c:if>
			<c:if test="${cart.containsProduct(product) }">
			<button onclick='location.href="cart.jsp"'>В корзине</button></c:if></td>
		</tr>
 	</table>
 	<h3>Описание</h3>
 	${product.shortDesc}
 	<h3>Характеристики</h3>
<table border="1">
	<tr>
		<td>Характеристика</td>
		<td>Значение хар-ки</td>
	</tr>
	<c:if test="${product.rows > 0 }">
	<c:forEach begin="0" end="${product.rows-1}" var="i">
	<tr>
		<td>${product.specs[i][0] }</td>
		<td>${product.specs[i][1] }</td>
	</tr>
	</c:forEach>
	</c:if>
</table>
<c:if test="${not empty msg }">
	<h3>${msg}</h3>
	<c:remove var="msg"/>
</c:if>
</div>
</body>
</html>