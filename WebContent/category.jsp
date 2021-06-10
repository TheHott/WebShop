<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="classes.Product"%>
<%@page import="classes.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${categoryName}</title>
<link rel="stylesheet" type="text/css" href="css/modal.css">
</head>
<body>

<div class="header">
<h1>Интернет-магазин компьютерной техники</h1>
<table>
	<tr>
		<td><a href="index.jsp">Домой</a></td>
		<td><b>Каталог</b></td>
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
</div> <!-- header ender -->

<!-- Корзина -->
<button id="myBtn">Открыть корзину</button>
<!-- The Modal -->
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
		</c:if>
		<c:if test="${cart.getProducts().isEmpty()!=false }">
			Корзина пустая!
		</c:if>
  </div>
  <script type="text/javascript" src="scripts/miniCart.js"></script>
</div> <!-- корзина ender -->

<div class="contents">
<br><a href="catalog?page=1">Каталог</a> -> <b>${categoryName }</b>
<h1>Категории</h1>
<table>
<tr><c:forEach var="category" items="${categories}">
<c:if test="${!category.name.equals(categoryName) }"><td><a href="GetCategoryProducts?id=${category.ID}&page=1">${category.name}</a></td></c:if>
<c:if test="${category.name.equals(categoryName) }"><td><b>${category.name}</b></td></c:if>
</c:forEach></tr>
</table>
Сортировать по
<form action="GetCategoryProducts" method="get">
<input type="hidden" name="page" value="1">
<input type="hidden" name="id" value="${param.id }">
<input type="radio" name="sort" value="IDHigh2Low" checked>От новых к старым (по умолчанию)
<input type="radio" name="sort" value="IDLow2High"
<c:if test="${param.sort eq 'IDLow2High' }">
	checked
</c:if>>От старых к новым
<input type="radio" name="sort" value="CostLow2High"
<c:if test="${param.sort eq 'CostLow2High' }">
	checked
</c:if>>Цена по возрастанию
<input type="radio" name="sort" value="CostHigh2Low"
<c:if test="${param.sort eq 'CostHigh2Low' }">
	checked
</c:if>>Цена по убыванию
<input type="submit" value="Обновить">
</form>
<table border="1">
	<tr>
		<td>ID</td>
		<td>Название</td>
		<td>Цена</td>
		<td>Кол-во на складе</td>
		<td></td>
	</tr>
	<c:forEach var="product" items="${categoryProducts}">
	<tr>
		<td>${product.ID }</td>
		<td><a href="GetProduct?id=${product.ID}">${product.name }</a></td>
		<td>${product.costPerOne} руб.</td>
		<td>${product.amount} шт.</td>
		<td><c:if test="${cart.containsProduct(product)==false || cart==null}">
		<form action="AddProductToCart" method="post">
		<input type="hidden" name="prodID" value="${product.ID }">
		<input type="submit" value="Добавить в корзину">
		</form></c:if>
		<c:if test="${cart.containsProduct(product) }">
		<button onclick='location.href="cart.jsp"'>В корзине</button></c:if></td>
	</tr>
	</c:forEach>
 </table>
 <c:if test="${param.page>1 }"><a href="GetCategoryProducts?id=${param.id}&page=${param.page-1}&sort=${param.sort}">◀</a></c:if>
<c:forEach var="Integer" items="${pagesArr}">
	<c:if test="${Integer==param.page}"><b>${Integer}</b></c:if>
	<c:if test="${Integer!=param.page}"><a href="GetCategoryProducts?id=${param.id}&page=${Integer}&sort=${param.sort}">${Integer}</a></c:if>
</c:forEach>
<c:if test="${param.page!=pagesArr.get(pagesArr.size()-1)}"><a href="GetCategoryProducts?id=${param.id}&page=${param.page+1}&sort=${param.sort}">▶</a></c:if>
</div>

<div class="errors">
<c:if test="${not empty msg }">
	<h3>${msg}</h3>
	<c:remove var="msg"/>
</c:if>
</div>
</body>
</html>