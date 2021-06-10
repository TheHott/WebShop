<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="classes.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Страница администратора</title>
<link rel="stylesheet" type="text/css" href="css/modal.css">
</head>
<body>

<div class="header">
<h1>Интернет-магазин компьютерной техники - Страница администратора</h1>
<table>
	<tr>
		<td><a href="index.jsp">Домой</a></td>
		<td><a href="GetCatalog">Каталог</a></td>
		<td><a href="cart.jsp">Корзина</a></td>
		<td><a href="myProfile.jsp">Профиль</a></td>
		<td>Добро пожаловать, <b>${user.login }</b></td>
		<td><a href="SignOutUser">Выйти</a></td>
		<td><form action="search" method="get" autocomplete="off">
		<input type="search" name="req" placeholder="Найти товар" required>
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
<div><a href="admin/adminOrder.jsp">Редактировать заказы</a>
<a href="admin/adminSpecs.jsp">Редактировать характеристики товаров</a>
<form action="AddSpec" method="post" autocomplete="off">
  	<h1>Добавить хар-ку товара</h1>
      Название товара: <input type="text" name="productName" size="10"><br>
      Название хар-ки: <input type="text" name="specName" size="10"><br>
      Хар-ка: <input type="text" name="spec" size="10"><br>
      <p>
		<small>
              <input type="submit" name="save" value="Создать">
        </small>
 	 </form>
 	 Хар-ка добавлена? Это ${specAdded}
</div>
<div><form action="CreateCategory" method="post" autocomplete="off">
  	<h1>Добавить категорию</h1>
      Наименование: <input type="text" name="category" size="10"><br>
      <p>
		<small>
              <input type="submit" name="save" value="Создать">
        </small>
 	 </form>
  	</div>
  	<div><form action="DeleteUser" method="post" autocomplete="off">
  	<h1>Удалить юзера</h1>
      Логин: <input type="text" name="login" size="10"><br>
      <p>
		<small>
              <input type="submit" name="save" value="Удалить">
        </small>
 	 </form>
 	 Пользователь удален? Это ${userDeleted}
  	</div>
  	<div><form action="SetProduct" method="post" autocomplete="off">
		<h1>Обновить данные о товаре</h1>
    	Название товара для изменения: <input type="text" name="oldName" size="10"><br>
    	Стоимость: <input type="text" name="cost" size="10"><br>
    	Количество: <input type="text" name="amount" size="10"><br>
    	Короткое описание: <input type="text" name="desc" size="10"><br>
    	Категория: <input type="text" name="category" size="10"><br>
    	<p>
			<small>
            	<input type="submit" name="save" value="Обновить">
        	</small>
  	</form>
  	 Товар найден? Это ${nameCorrect}
 	 Данные обновлены? Цена - ${costSet}, Количество - ${amountSet}, 
 	 Описание - ${descSet}, Категория - ${categorySet}
  	</div>
  	<div><form action="SetProductsSpecs" method="post" autocomplete="off">
		<h1>Обновить характеристики товара</h1>
    	Название товара: <input type="text" name="prodName" size="10"><br>
    	Хар-ка: <input type="text" name="specName" size="10"><br>
    	Значение хар-ки: <input type="text" name="spec" size="10"><br>
    	<p>
			<small>
            	<input type="submit" name="save" value="Обновить">
        	</small>
  	</form>
  	 Хар-ка обновлена? Это ${specsSet} <br>
  	</div>
        <div><form action="CreateProduct" method="post" autocomplete="off">
  	<h1>Добавить товар</h1>
      Название: <input type="text" name="newProdName" size="10"><br>
      Цена: <input type="text" name="newCost" size="10"><br>
      Количество на складе: <input type="text" name="newAmount" size="10"><br>
      Краткое описание: <input type="text" name="newShortDesc" size="10"><br>
      Категория: <input type="text" name="newCategory" size="10"><br>
      <p>
		<small>
              <input type="submit" name="save" value="Создать">
        </small>
 	 </form>
 	 Товар добавлен? Это ${prodCreated}
  	</div>
</div>

<div class="errors">
	<c:if test="${not empty msg }">
		<h3>${msg}</h3>
		<c:remove var="msg"/>
	</c:if>		
</div>
</body>
</html>