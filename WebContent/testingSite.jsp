<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="classes.Product"%>
<%@page import="classes.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/modal.css">
	<title>Проверка функций</title>
</head>
<body>

<div class="header">
	<h1>Интернет-магазин компьютерной техники</h1>
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
	<div><form action="CheckUser" method="post">
		<h1>Авторизация ВКЛЮЧЕНО</h1>
    	Логин: <input type="text" name="login" size="10"><br>
    	Пароль: <input type="password" name="password" size="10"><br>
    	<p>
			<small>
            	<input type="submit" name="save" value="Войти">
        	</small>
  	</form>
  	</div>
  	Пользователь существует? Это ${userExists}
  	<div><form action="SetUsersInfo" method="post" autocomplete="off">
  	<h1>Смена инфы о пользователе</h1>
      Логин: <input type="text" name="login" size="10"><br>
      ФИО: <input type="text" name="fio" size="10"><br>
      Емейл: <input type="text" name="email" size="10"><br>
      Номер телефона: <input type="text" name="phone" size="10"><br>
      Адрес: <input type="text" name="address" size="10"><br>
      <p>
		<small>
              <input type="submit" name="save" value="Сохранить">
        </small>
 	 </form>
 	 Пользователь найден? Это ${loginCorrect}
 	 Данные обновлены? ФИО - ${fioSet}, Емейл - ${emailSet}, 
 	 Телефон - ${phoneSet}, Адрес -${addressSet} 
  	</div>
  	<div><form action="CreateUser" method="post" autocomplete="off">
  	<h1>Добавить юзера</h1>
      Логин: <input type="text" name="login" size="10"><br>
      Пароль: <input type="password" name="password" size="10"><br>
      ФИО: <input type="text" name="fio" size="10"><br>
      Емейл: <input type="text" name="email" size="10"><br>
      Номер телефона: <input type="text" name="phone" size="10"><br>
      Адрес: <input type="text" name="address" size="10"><br>
      <p>
		<small>
              <input type="submit" name="save" value="Создать">
        </small>
 	 </form>
 	 Пользователь добавлен? Это ${userCreated}
 	 </div>
 	 <div><form action="CreateOrder" method="post" autocomplete="off">
  	<h1>Создать заказ НЕ ПРОВЕРЯТЬ - НЕ РАБОТАЕТ</h1>
  	<h3>Нужно подумать что нужно вводить для создания заказа</h3>
  	<h3>Скорее всего нужно начать с корзины, потом просто копировать</h3>
  	<h3>И вставлять в заказы</h3>
  	<h3>Можно даже запрос сделать который тупо копипастит из таблицы Basket</h3>
      Логин: <input type="text" name="login" size="10"><br>
      Адрес: <input type="text" name="address" size="10"><br>
      Сумма: <input type="text" name="totalCost" size="10"><br>
      <p>
		<small>
              <input type="submit" name="save" value="Создать">
        </small>
 	 </form>
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
  	<div><form action="AddSpec" method="post" autocomplete="off">
  	<h1>Добавить хар-ку товара ВКЛЮЧЕНО В АДМИНА</h1>
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
	<div>       <!-- основа сайта -->
        <div>    <!-- кнопки -->
			<button onClick='location.href="controller/Cookie_manager.jsp?name=kek&cost=2000"'>Cookies test (should be kek 2000)</button><br>
        </div>
        <div>
        <form action="getCatalog" method="post">
        <h1>Получить товары</h1>
        <table border="1">
			<tr>
				<td>ID</td>
				<td>Название</td>
				<td>Цена</td>
				<td>Кол-во на складе</td>
				<td>Описание</td>
				<td>Категория</td>
				<td>Имя хар-ки</td>
				<td>Хар-ка</td>
			</tr>
			<c:forEach var="product" items="${products}">
			<tr>
				<td>${product.ID }</td>
				<td>${product.name }</td>
				<td>${product.costPerOne}</td>
				<td>${product.amount}</td>
				<td>${product.shortDesc}</td>
				<td>${product.category}</td>
				<td>${product.specs[0][0]}</td>
				<td>${product.specs[0][1]}</td>
			</tr>
			</c:forEach>
 		</table>
 		<input class="rounded-input"  type="submit"  value="Получить продукт" /><br>
 		</form>
        </div>
        <div>
        <form action="GetCategoryProducts" method="post" autocomplete="off">
        <h1>Получить товары по категории</h1>
        Категория: <input type="text" name="category" size="20"><br>
        <table border="1">
			<tr>
				<td>ID</td>
				<td>Название</td>
				<td>Цена</td>
				<td>Кол-во на складе</td>
				<td>Описание</td>
				<td>Имя хар-ки</td>
				<td>Хар-ка</td>
			</tr>
			<c:forEach var="product" items="${categoryProducts}">
			<tr>
				<td>${product.ID }</td>
				<td>${product.name }</td>
				<td>${product.costPerOne}</td>
				<td>${product.amount}</td>
				<td>${product.shortDesc}</td>
				<td>${product.specs[0][0]}</td>
				<td>${product.specs[0][1]}</td>
			</tr>
			</c:forEach>
 		</table>
 		<input class="rounded-input"  type="submit"  value="Получить продукт" /><br>
 		</form>
        </div>
        <div>
        <form action="GetUsersInfo" method="post" autocomplete="off">
        <h1>Получить инфо о юзере</h1>
        <table border="1">
			<tr>
				<td>Логин</td>
				<td>ФИО</td>
				<td>Емейл</td>
				<td>Номер телефона</td>
				<td>Адрес</td>
			</tr>
			<tr>
				<td><input type="text" name="login" size="10"></td>
				<td>${fio}</td>
				<td>${email}</td>
				<td>${phone}</td>
				<td>${address}</td>
			</tr>
 		</table>
 		<input class="rounded-input"  type="submit"  value="Получить инфо юзера" /><br>
 		</form>
        </div>
         <div>
        <form action="GetOrder" method="post" autocomplete="off">
        <h1>Получить заказ</h1>
        <table border="1">
			<tr>
				<td>Логин</td>
				<td>ФИО</td>
				<td>Телефон</td>
				<td>Емейл</td>
				<td>Адрес</td>
				<td>Дата</td>
				<td>Сумма</td>
				<td>Доставлено?</td>
				<td>Оплачено?</td>
			</tr>
			<tr>
				<td><input type="text" name="login" size="10"></td>
				<td>${order.buyersFIO}</td>
				<td>${order.phoneNumber}</td>
				<td>${order.email}</td>
				<td>${order.deliveryAddress}</td>
				<td>${order.dateOfOrder}</td>
				<td>${order.totalCost}</td>
				<td>${order.deliveryState}</td>
				<td>${order.paymentState}</td>
			</tr>
 		</table>
 		<table border="1">
 		<tr>
 			<td>Имя товара</td>
 			<td>Категория</td>
 			<td>Стоимость за 1 шт</td>
 			<td>Количество</td>
 			<td>Сумма</td>
 		</tr>
 		<c:forEach var="product" items="${order.getProducts()}">
 		<tr>
 			<td>${product.name}</td>
 			<td>${product.category}</td>
 			<td>${product.costPerOne}</td>
 			<td>${product.amount}</td>
 			<td>${product.costPerOne*product.amount}</td>
 		</tr>
 		</c:forEach>
 		</table>
 		<input class="rounded-input"  type="submit"  value="Получить заказ" /><br>
 		</form>
        </div>
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