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

<div class="nexturl">
<c:url var="nextUrl" value="/search">
	<c:forEach items="${param}" var="entry">
		<c:if test="${entry.key!='page' }">
			<c:param name="${entry.key }" value="${entry.value }"/>
		</c:if>
	</c:forEach>
	<c:forEach items="${paramValues}" var="entry">
		<c:if test="${entry.key=='c[]' }">
			<c:forEach items="${entry.value }" var="entryVal" varStatus="loop">
				<c:param name="${entry.key}" value="${entry.value[loop.index]}"/>
			</c:forEach>
		</c:if>
	</c:forEach>
</c:url>
</div>

<div class="header">
	<h1>Интернет-магазин компьютерной техники - Поиск</h1>
	<table>
		<tr>
			<td><a href="index.jsp">Домой</a></td>
			<td><a href="./catalog?page=1">Каталог</a></td>
			<td><a href="cart.jsp">Корзина</a></td>
			<c:if test="${user==null }"><td><a href="login.jsp">Авторизация</a></td>
			<td><a href="signUp.jsp">Регистрация</a></td></c:if>
			<c:if test="${user!=null }"><td><a href="myProfile.jsp">Профиль</a></td>
			<td>Добро пожаловать, <b>${user.login }</b></td>
			<td><a href="SignOutUser">Выйти</a></td></c:if>
			<td><form id="searchProd" action="search" method="get" autocomplete="off">
			<input type="search" name="req" placeholder="Найти товар" value="${param.req }" required>
			<input type="hidden" name="page" value="1">
			<input type="submit" value="Найти"></form></td>
		</tr>
	</table>
</div> <!-- header ender -->

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
</div> <!-- корзина ender -->

<div class="contents">
<table>
	<tr>
		<c:forEach var="category" items="${categories}" varStatus="loop">
		<td><input type="checkbox" name="c[]" value="${category.ID}" form="searchProd"
		<c:forEach var="item" items="${paramValues.get('c[]') }">
			<c:if test="${item eq category.ID }">
				checked
			</c:if>
		</c:forEach> 
		>${category.name}</td>
		</c:forEach>
	</tr>
</table>
<table>
<tr>
	<td>От <input type="text" name="fromCost" placeholder="0"
	<c:if test='${param.fromCost==null}'> value="0" </c:if>
	<c:if test='${param.fromCost!=null}'> value="${param.fromCost }" </c:if>
	 form="searchProd"></td>
	<td>До <input type="text" name="toCost" placeholder="999999"
	<c:if test='${param.toCost==null}'> value="999999" </c:if>
	<c:if test='${param.toCost!=null}'> value="${param.toCost }" </c:if>
	form="searchProd"></td>
</tr>
</table>
<c:url var="nextUrl" value="">
	<c:forEach items="${param}" var="entry">
		<c:if test="${entry.key!='page' && entry.key!='c[]' }">
			<c:param name="${entry.key }" value="${entry.value }"/>
		</c:if>
	</c:forEach>
	<c:forEach items="${paramValues}" var="entry">
		<c:if test="${entry.key=='c[]' }">
			<c:forEach items="${entry.value }" var="entryVal" varStatus="loop">
				<c:param name="${entry.key}" value="${entry.value[loop.index]}"/>
			</c:forEach>
		</c:if>
	</c:forEach>
</c:url>
<c:if test="${products.isEmpty()!=true}">
	<table border=1>
	<tr>
		<td>ID</td>
		<td>Название</td>
		<td>Цена</td>
		<td>Кол-во на складе</td>
		<td>Категория</td>
		<td></td>
	</tr>
	<c:forEach var="product" items="${products}">
	<tr>
		<td>${product.ID }</td>
		<td><a href="GetProduct?id=${product.ID}">${product.name }</a></td>
		<td>${product.costPerOne} руб.</td>
		<td><c:if test="${product.amount==0 }">Нет на складе!</c:if>
		<c:if test="${product.amount!=0 }">${product.amount} шт.</c:if></td>
		<td><a href="GetCategoryProducts?id=${product.category.ID}&page=1">${product.category.name}</a></td>
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
	<c:if test="${param.page>1 }"><a href="${nextUrl}&page=${param.page-1}">◀</a></c:if>
	<c:forEach var="Integer" items="${pagesArr}">
		<c:if test="${Integer==param.page}"><b>${Integer}</b></c:if>
		<c:if test="${Integer!=param.page}">
		<a href="${nextUrl}&page=${Integer}">${Integer}</a></c:if>
	</c:forEach>
	<c:if test="${param.page!=pagesArr.get(pagesArr.size()-1)}"><a href="${nextUrl}&page=${param.page+1}">▶</a></c:if>
</c:if>
<c:if test="${products.isEmpty()!=false}">
<br>По вашему запросу ничего не найдено.
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