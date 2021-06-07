package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import classes.Category;
import classes.Order;
import classes.Product;
import classes.User;

public class dbFunc {
	/* Добавить товар в заказ по логину */
	public static void addOrdersProduct(String login, ArrayList<Product> products, float totalCost, 
			String address) {
		Connection con = ConnectionPool.getInstance().getConnection();
		int userID=getUserID(login);
		int orderID=getOrdersID(userID, totalCost, address);
		int prodID;
		int amount;
		
		PreparedStatement pr = null;
		try {
			for(int i=0; i<products.size(); i++) {
				prodID=products.get(i).getID();
				amount=products.get(i).getAmount();
				String query = "INSERT INTO Order_element (ID_order, ID_product, Amount)\r\n"
						+ "VALUES (?, ?, ?);";
				pr = con.prepareStatement(query);
				pr.setInt(1, orderID);
				pr.setInt(2, prodID);
				pr.setInt(3, amount);
				pr.executeUpdate();
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void addProductToCart(String login, String prodName, int amount) {
		Connection con = ConnectionPool.getInstance().getConnection();
		int cartID = getCartID(login);
		int prodID = getProductID(prodName);
		
		PreparedStatement pr = null;
		String query="INSERT INTO Cart_element (ID_cart, ID_product, Amount) VALUES (?, ?, ?)";
		
		try {
			pr = con.prepareStatement(query);
			pr.setInt(1, cartID);
			pr.setInt(2, prodID);
			pr.setInt(3, amount);
			pr.executeUpdate();
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/* Добавить хар-ку к товару */
	public static boolean addSpec(String prodName, String specName, String spec) {
		boolean isAdded = false;
		int prodID=getProductID(prodName);
		int specID=getSpecID(specName);
		Connection con = ConnectionPool.getInstance().getConnection();
		PreparedStatement pr = null;
		
		if(specID != 0) {
			try {
				String query = "INSERT INTO Products_description (ID_product, ID_description, Text) "
						+ "VALUES (?, ?, ?);";
				pr = con.prepareStatement(query);
				pr.setInt(1, prodID);
				pr.setInt(2, specID);
				pr.setString(3, spec);
				pr.executeUpdate();
				isAdded = true;
		
			} catch (Exception e) {
				System.err.println(e);
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return isAdded;
	}
	/* проверить существует ли пользователь и возвращает его логин */
	public static String checkUser(User obj_User) {
		String login=null;
		
		Connection con = ConnectionPool.getInstance().getConnection();
		PreparedStatement pr = null;
		String query="SELECT User.Login FROM User LEFT JOIN Users_info \r\n"
				+ "ON User.ID=Users_info.ID_user WHERE User.Login=? AND User.Password=? OR "
				+ "Users_info.Email=? AND User.Password=? OR Users_info.Phone=? AND User.Password=?;";
		
		try {
			pr=con.prepareStatement(query);
			pr.setString(1, obj_User.getLogin());
			pr.setString(2, obj_User.getPassword());
			pr.setString(3, obj_User.getLogin());
			pr.setString(4, obj_User.getPassword());
			pr.setString(5, obj_User.getLogin());
			pr.setString(6, obj_User.getPassword());
			ResultSet rs = pr.executeQuery();
			// System.out.println(pr); // debugging
		
			if(rs.next()) {
				login=rs.getString(1);
			}
			// System.out.println(login); // debugging
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return login;
	}
	/* создать категорию */
	public static boolean createCategory(String name) {
		boolean isCreated = false; // создалась ли категория? 
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		try {
			String query = "INSERT INTO Category (Name) VALUES (?);";
			pr = con.prepareStatement(query);
			pr.setString(1, name);
			pr.executeUpdate();
			isCreated = true;
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isCreated;
	}
	/* создать заказ */
	public static void createOrder(String login, float totalCost, String address) { 
		Connection con = ConnectionPool.getInstance().getConnection();
		int userID=getUserID(login);
		
		PreparedStatement pr = null;
		try {
			String query = "INSERT INTO Buy_order (ID_user, Total_cost, Delivery_address) \r\n"
					+ "VALUES (?, ?, ?);";
			pr = con.prepareStatement(query);
			pr.setInt(1, userID);
			pr.setFloat(2, totalCost);
			pr.setString(3, address);
			pr.executeUpdate();
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/* создать товар */
	public static boolean createProduct(String name, String cost, String amount, String shortDesc,
			String category) {
		boolean isCreated = false; // создался ли продукт
		Connection con = ConnectionPool.getInstance().getConnection();
		int categoryID=getCategoryID(category);
		
		PreparedStatement pr = null;
		try {
			String query = "INSERT INTO Product (Name, Cost, Short_desc, "
					+ "Amount, ID_category) VALUES (?, ?, ?, ?, ?);";
			pr = con.prepareStatement(query);
			pr.setString(1, name);
			pr.setString(2, cost);
			pr.setString(3, shortDesc);
			pr.setString(4, amount);
			pr.setInt(5, categoryID);
			pr.executeUpdate();
			isCreated = true;
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isCreated;
	}
	/* создать пользователя (логин, пароль, фио, емейл, телефон) */
	public static boolean createUser(String login, String password, String[] usersInfo) {
		boolean isCreated = false; // создался ли пользователь?
		Connection con = ConnectionPool.getInstance().getConnection();
		int userID;
		
		PreparedStatement pr = null;
		try {
			/* Добавление данных для авторизации (логин и пароль) */
			String query = "INSERT INTO User (Login, Password) VALUES (?, ?);";
			pr = con.prepareStatement(query);
			pr.setString(1, login);
			pr.setString(2, password);
			pr.executeUpdate();
			
			/* Получение айди юзера по логину */
			userID=getUserID(login);
			
			query="INSERT INTO Users_info (ID_user, FIO, Email, Phone, Address) "
					+ "VALUES (?, ?, ?, ?, ?);";
			pr = con.prepareStatement(query);
			pr.setInt(1, userID);
			pr.setString(2, usersInfo[0]);
			pr.setString(3, usersInfo[1]);
			pr.setString(4, usersInfo[2]);
			pr.setString(5, usersInfo[3]);
			pr.executeUpdate();
			
			query="INSERT INTO Cart (ID_user) SELECT ID FROM User WHERE Login=?;";
			pr = con.prepareStatement(query);
			pr.setString(1, login);
			pr.executeUpdate();
			isCreated = true;
			
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isCreated;
	}
	public static void deleteCartProduct(String login, String prodName) {
		Connection con = ConnectionPool.getInstance().getConnection();
		PreparedStatement pr = null;
		int cartID = getCartID(login);
		int prodID = getProductID(prodName);
		String query="DELETE FROM Cart_element WHERE ID_cart=? AND ID_product=?";
		
		try {
			pr=con.prepareStatement(query);
			pr.setInt(1, cartID);
			pr.setInt(2, prodID);
			pr.executeUpdate();
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/* удалить пользователя */
	public static boolean deleteUser(String login) {
		boolean userDeleted = false; // удалилось ли? 
		Connection con = ConnectionPool.getInstance().getConnection();
		PreparedStatement pr = null;
		String query="DELETE FROM User WHERE Login=?";
		
		try {
			pr=con.prepareStatement(query);
			pr.setString(1, login);
			pr.executeUpdate();
			userDeleted=true;
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userDeleted;
	}
	/* получить категории */
	public static ArrayList<Category> getAllCategories() {
		ArrayList<Category> categories = new ArrayList<Category>();
		Category category = null;
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		String query="SELECT * FROM Category";
		try {
			pr=con.prepareStatement(query);
			ResultSet rs = pr.executeQuery();
			
			while (rs.next()) {
				category = new Category();
				category.setID(rs.getInt(1));
				category.setName(rs.getString(2));
				categories.add(category);
			}
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}
	/* получить количество товаров */
	public static int getAmountOfProducts() {
		int amount = 0;
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		String query="SELECT count(1) FROM Product;";
		try {
			pr=con.prepareStatement(query);
			ResultSet rs = pr.executeQuery();
		
			while (rs.next()) {
				amount=rs.getInt(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return amount;
	}
	/* получить количество товаров по запросу поиска, используется для Search.java */
	public static int getAmountOfProductsSearch(String req, int categoryID[], float fromCost, float toCost) {
		req = "%" + req + "%";
		int amount = 0;
		Connection con = ConnectionPool.getInstance().getConnection();
	
		PreparedStatement pr = null;
		String query="SELECT count(1) FROM Product WHERE (Name LIKE ? OR Short_desc LIKE ?) ";
		if(categoryID!=null) {
			query = query + "AND (ID_category=? ";
			if(categoryID.length!=1)
				for(int i=1; i<categoryID.length; i++)
					query = query + "OR ID_category=? ";
			query = query + ") ";
		}
		query = query + "AND (Cost BETWEEN ? AND ?);";
		try {
			pr=con.prepareStatement(query);
			pr.setString(1, req);
			pr.setString(2, req);
			if(categoryID!=null) {
				int i;
				for(i=0; i<categoryID.length; i++)
					pr.setInt(i+3, categoryID[i]);
				pr.setFloat(i+3, fromCost);
				pr.setFloat(i+4, toCost);
			}
			else {
				pr.setFloat(3, fromCost);
				pr.setFloat(4, toCost);
			}
			ResultSet rs = pr.executeQuery();
		
			while (rs.next()) {
				amount=rs.getInt(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return amount;
	}
	/* получить количество товаров категории по ее айди */
	public static int getAmountOfCategoryProducts(int ID) {
		int amount = 0;
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		String query="SELECT count(1) FROM Product WHERE id_category=?;";
		try {
			pr=con.prepareStatement(query);
			pr.setInt(1, ID);
			ResultSet rs = pr.executeQuery();
		
			while (rs.next()) {
				amount=rs.getInt(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return amount;
	}
	/* получить количество характеристик товара */
	public static int getAmountOfSpecs(int ID_product) {
		int amountOfSpecs = 0;
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		String query="SELECT count(1) FROM products_description WHERE ID_product=?;";
		try {
			pr=con.prepareStatement(query);
			pr.setInt(1, ID_product);
			ResultSet rs = pr.executeQuery();
		
			while (rs.next()) {
				amountOfSpecs=rs.getInt(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return amountOfSpecs;
	}
	// Получить айди корзины юзера по его логину, для использования в getCart и SetCartsProductAmount
	public static int getCartID(String login){
		Connection con = ConnectionPool.getInstance().getConnection();
		int cartID = 0;
		
		PreparedStatement pr = null;
		String query="SELECT Cart.ID from Cart\r\n"
				+ "LEFT JOIN User ON Cart.ID_user=User.ID \r\n"
				+ "WHERE User.Login=?;";
		try {
			pr=con.prepareStatement(query);
			pr.setString(1, login);
			ResultSet rs = pr.executeQuery();
		
			while(rs.next())
				cartID=rs.getInt(1);
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cartID;
	}
	// Получить товары корзины юзера по его логину
	public static ArrayList<Product> getCartProducts(String login){
		Connection con = ConnectionPool.getInstance().getConnection();
		int cartID = getCartID(login);
		ArrayList<Product> cartProducts = new ArrayList<Product>();
		
		PreparedStatement pr = null;
		String query="SELECT Product.ID, Product.Name, Product.Cost, Cart_element.Amount\r\n"
				+ "FROM Product \r\n"
				+ "LEFT JOIN Cart_element ON Product.ID=Cart_element.ID_product \r\n"
				+ "WHERE Cart_element.ID_cart=?;";
		try {
			pr=con.prepareStatement(query);
			pr.setInt(1, cartID);
			ResultSet rs = pr.executeQuery();
		
			while(rs.next()) {
				Product product = new Product();
				product.setID(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setCostPerOne(rs.getInt(3));
				product.setAmount(rs.getInt(4));
				cartProducts.add(product);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cartProducts;
	}
	// Получить стоимость корзины по логину
	public static float getCartTotalCost(String login){
		Connection con = ConnectionPool.getInstance().getConnection();
		int cartID = getCartID(login);
		float totalCost = 0;
		
		PreparedStatement pr = null;
		String query="SELECT Total_cost FROM Cart WHERE Cart.ID=?;";
		try {
			pr=con.prepareStatement(query);
			pr.setInt(1, cartID);
			ResultSet rs = pr.executeQuery();
		
			while(rs.next()) {
				totalCost=rs.getFloat(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalCost;
	}
	/* получить каталог товаров */
	public static ArrayList<Product> getCatalog() {
		ArrayList<Product> products = new ArrayList<Product>();
		Product product = null;
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		String query="SELECT Product.ID, Product.Name, Product.Cost, Product.Short_desc, \r\n"
				+ "Product.Amount, Category.ID, Category.Name FROM Product LEFT JOIN Category ON \r\n"
				+ "Product.ID_category = Category.ID;";
		try {
			pr=con.prepareStatement(query);
			ResultSet rs = pr.executeQuery();
			
			while (rs.next()) {
				product = new Product();
				product.setID(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setCostPerOne(rs.getFloat(3));
				product.setShortDesc(rs.getString(4));
				product.setAmount(rs.getInt(5));
				product.setCategoryID(rs.getInt(6));
				product.setCategoryName(rs.getString(7));
				product.setPicLink(rs.getString(8));
				product.setRows(getAmountOfSpecs(product.getID()));
				product.setSpecs(getSpecs(product.getRows(), product.getID()));
				products.add(product);
			}
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}
	/* получить айди категории */
	public static int getCategoryID(String name) {
		int ID = 0;
		
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		String query="SELECT ID FROM Category WHERE Name=?";
		try {
			pr=con.prepareStatement(query);
			pr.setString(1, name);
			ResultSet rs = pr.executeQuery();
		
			while (rs.next()) {
				ID=rs.getInt(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ID;
	}
	/* получить имя категории */
	public static String getCategoryName(int ID) {
		String name = null;
		
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		String query="SELECT Name FROM Category WHERE ID=?";
		try {
			pr=con.prepareStatement(query);
			pr.setInt(1, ID);
			ResultSet rs = pr.executeQuery();
		
			while (rs.next()) {
				name=rs.getString(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return name;
	}
	// получить товары категории
	public static ArrayList<Product> getCategoryProducts(int ID) {
		ArrayList<Product> products = new ArrayList<Product>();
		Product product = null;
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		String query="SELECT Product.ID, Product.Name, Product.Cost, Product.Short_desc, \r\n"
				+ "Product.Amount, Category.ID, Category.Name FROM Product LEFT JOIN Category ON \r\n"
				+ "Product.ID_category = Category.ID WHERE Category.ID=?;";
		
		try {
			pr=con.prepareStatement(query);
			pr.setInt(1, ID);
			ResultSet rs = pr.executeQuery();
			
			while (rs.next()) {
				product = new Product();
				product.setID(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setCostPerOne(rs.getFloat(3));
				product.setShortDesc(rs.getString(4));
				product.setAmount(rs.getInt(5));
				product.setCategoryID(rs.getInt(6));
				product.setCategoryName(rs.getString(7));
				product.setRows(getAmountOfSpecs(product.getID()));
				product.setSpecs(getSpecs(product.getRows(), product.getID()));
				products.add(product);
			}
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}
	// получить товары категории от X до X+M
		public static ArrayList<Product> getCategoryProductsXtoY(int ID, int first, int x, String sort) {
			ArrayList<Product> products = new ArrayList<Product>();
			Product product = null;
			Connection con = ConnectionPool.getInstance().getConnection();
			
			PreparedStatement pr = null;
			String query="SELECT Product.ID, Product.Name, Product.Cost, Product.Short_desc, \r\n"
					+ "Product.Amount, Category.ID, Category.Name FROM Product LEFT JOIN Category ON \r\n"
					+ "Product.ID_category = Category.ID WHERE Category.ID=? LIMIT ?, ?;";
			switch (sort) {
			case ("CostHigh2Low"):
				query="SELECT Product.ID, Product.Name, Product.Cost, Product.Short_desc, \r\n"
						+ "Product.Amount, Category.ID, Category.Name FROM Product LEFT JOIN Category ON \r\n"
						+ "Product.ID_category = Category.ID WHERE Category.ID=? ORDER BY Product.Cost DESC LIMIT ?, ?;";
				break;
			case ("CostLow2High"):
				query="SELECT Product.ID, Product.Name, Product.Cost, Product.Short_desc, \r\n"
						+ "Product.Amount, Category.ID, Category.Name FROM Product LEFT JOIN Category ON \r\n"
						+ "Product.ID_category = Category.ID WHERE Category.ID=? ORDER BY Product.Cost LIMIT ?, ?;";
				break;
			case ("IDHigh2Low"):
				query="SELECT Product.ID, Product.Name, Product.Cost, Product.Short_desc, \r\n"
						+ "Product.Amount, Category.ID, Category.Name FROM Product LEFT JOIN Category ON \r\n"
						+ "Product.ID_category = Category.ID WHERE Category.ID=? ORDER BY Product.ID DESC LIMIT ?, ?;";
				break;
			case ("IDLow2High"):
				query="SELECT Product.ID, Product.Name, Product.Cost, Product.Short_desc, \r\n"
						+ "Product.Amount, Category.ID, Category.Name FROM Product LEFT JOIN Category ON \r\n"
						+ "Product.ID_category = Category.ID WHERE Category.ID=? ORDER BY Product.ID LIMIT ?, ?;";
				break;
			}
			
			try {
				pr=con.prepareStatement(query);
				pr.setInt(1, ID);
				pr.setInt(2, first);
				pr.setInt(3, x);
				ResultSet rs = pr.executeQuery();
				
				while (rs.next()) {
					product = new Product();
					product.setID(rs.getInt(1));
					product.setName(rs.getString(2));
					product.setCostPerOne(rs.getFloat(3));
					product.setShortDesc(rs.getString(4));
					product.setAmount(rs.getInt(5));
					product.setCategoryID(rs.getInt(6));
					product.setCategoryName(rs.getString(7));
					product.setRows(getAmountOfSpecs(product.getID()));
					product.setSpecs(getSpecs(product.getRows(), product.getID()));
					products.add(product);
				}
			
			} catch (Exception e) {
				System.err.println(e);
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return products;
		}
	/* получить дату заказа по айди */
	public static String getOrdersDate(int userID, String totalCost, String address) {
		String orderDate = null;
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		String query="SELECT DateOfOrder FROM Buy_order WHERE ID_user=? "
				+ "AND Total_cost=? AND Delivery_Address=?;";
		try {
			pr=con.prepareStatement(query);
			pr.setInt(1, userID);
			pr.setString(2, totalCost);
			pr.setString(3, address);
			ResultSet rs = pr.executeQuery();
		
			while (rs.next()) {
				orderDate=rs.getString(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orderDate;
	}
	/* получить айди заказа, для использования в Interface.createOrder. Получает айди только неполученного */
	public static int getOrdersID(int userID, float totalCost, String address) {
		int orderID = 0;
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		String query="SELECT ID FROM buy_order WHERE ID_user=? "
				+ "AND Total_cost=? AND Delivery_Address=? AND Is_taken=0;";
		try {
			pr=con.prepareStatement(query);
			pr.setInt(1, userID);
			pr.setFloat(2, totalCost);
			pr.setString(3, address);
			ResultSet rs = pr.executeQuery();
		
			while (rs.next()) {
				orderID=rs.getInt(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orderID;
	}
	/* Получить товары заказа по айди заказа */
	public static ArrayList<Product> getOrdersProducts(int orderID) {
		ArrayList<Product> ordersProducts = new ArrayList<>();
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		String query="SELECT Product.Name, Category.ID, Category.Name, Product.Cost, Order_element.Amount \r\n"
				+ "FROM Buy_order\r\n"
				+ "LEFT JOIN User ON Buy_order.ID_user=User.ID\r\n"
				+ "LEFT JOIN Order_element ON Buy_order.ID=Order_element.ID_order\r\n"
				+ "LEFT JOIN Product ON Order_element.ID_product=Product.ID\r\n"
				+ "LEFT JOIN Category ON Product.ID_category=Category.ID\r\n"
				+ "LEFT JOIN Users_info ON Users_info.ID_user=User.ID\r\n"
				+ "WHERE Buy_order.ID_user=User.ID AND Buy_order.Is_taken=0\r\n"
				+ "AND Buy_order.ID=?;";
		try {
			pr=con.prepareStatement(query);
			pr.setInt(1, orderID);
			ResultSet rs = pr.executeQuery();
		
			while (rs.next()) {
				Product product = new Product();
				product.setName(rs.getString(1));
				product.setCategoryID(rs.getInt(2));
				product.setCategoryName(rs.getString(3));
				product.setCostPerOne(rs.getFloat(4));
				product.setAmount(rs.getInt(5));
				ordersProducts.add(product);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ordersProducts;
	}
	/* получить каталог товаров */
	public static Product getProduct(int ID) {
		Product product = null;
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		String query="SELECT Product.ID, Product.Name, Product.Cost, Product.Short_desc, \r\n"
				+ "Product.Amount, Category.ID, Category.Name, Product.PicLink FROM Product LEFT JOIN Category ON \r\n"
				+ "Product.ID_category = Category.ID WHERE Product.ID=?;";
		try {
			pr=con.prepareStatement(query);
			pr.setInt(1, ID);
			ResultSet rs = pr.executeQuery();
			
			while (rs.next()) {
				product = new Product();
				product.setID(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setCostPerOne(rs.getFloat(3));
				product.setShortDesc(rs.getString(4));
				product.setAmount(rs.getInt(5));
				product.setCategoryID(rs.getInt(6));
				product.setCategoryName(rs.getString(7));
				product.setPicLink(rs.getString(8));
				product.setRows(getAmountOfSpecs(product.getID()));
				product.setSpecs(getSpecs(product.getRows(), product.getID()));
			}
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}
	/* получить количество товара по айди (для SetCartsProductAmount) */
	public static int getProductAmount(int ID) {
		Connection con = ConnectionPool.getInstance().getConnection();
		int amount = 0;
		
		PreparedStatement pr = null;
		String query="SELECT Amount FROM Product WHERE ID=?;";
		try {
			pr=con.prepareStatement(query);
			pr.setInt(1, ID);
			ResultSet rs = pr.executeQuery();
		
			while (rs.next()) {
				amount=rs.getInt(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return amount;
	}
	/* получить айди товара по названию */
	public static int getProductID(String name) {
		Connection con = ConnectionPool.getInstance().getConnection();
		int ID=0;
		
		PreparedStatement pr = null;
		String query="SELECT ID FROM Product WHERE Name=?;";
		try {
			pr=con.prepareStatement(query);
			pr.setString(1, name);
			ResultSet rs = pr.executeQuery();
		
			while (rs.next()) {
				ID=rs.getInt(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ID;
	}
	/* получить название товара по айди */
	public static String getProductName(int ID) {
		Connection con = ConnectionPool.getInstance().getConnection();
		String name = null;
		
		PreparedStatement pr = null;
		String query="SELECT Name FROM Product WHERE ID=?;";
		try {
			pr=con.prepareStatement(query);
			pr.setInt(1, ID);
			ResultSet rs = pr.executeQuery();
		
			while (rs.next()) {
				name=rs.getString(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return name;
	}
	/* получить айди хар-ки по названию */
	public static int getSpecID(String name){
		Connection con = ConnectionPool.getInstance().getConnection();
		int specID = 0;
		
		PreparedStatement pr = null;
		String query="SELECT ID FROM description WHERE Name=?;";
		try {
			pr=con.prepareStatement(query);
			pr.setString(1, name);
			ResultSet rs = pr.executeQuery();
		
			while (rs.next()) {
				specID=rs.getInt(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return specID;
	}
	/* получить данные о названии характеристики по айди */
	public static String getSpecName(int ID){
		Connection con = ConnectionPool.getInstance().getConnection();
		String specName = null;
		
		PreparedStatement pr = null;
		String query="SELECT Name FROM description WHERE ID=?;";
		try {
			pr=con.prepareStatement(query);
			pr.setInt(1, ID);
			ResultSet rs = pr.executeQuery();
		
			while (rs.next()) {
				specName=rs.getString(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return specName;
	}
	/* получить данные о характеристиках товара */
	public static String[][] getSpecs(int amountOfSpecs, int ID_product){
		Connection con = ConnectionPool.getInstance().getConnection();
		String[][] specs = new String[amountOfSpecs][2];
		
		PreparedStatement pr = null;
		String query="SELECT Description.Name, Products_description.Text \r\n"
				+ "FROM Products_description LEFT JOIN Description ON Products_description.ID_description = Description.ID \r\n"
				+ "WHERE Products_description.ID_product = ?;";
		try {
			pr=con.prepareStatement(query);
			pr.setInt(1, ID_product);
			ResultSet rs = pr.executeQuery();
		
			for(int i=0; rs.next(); i++) {
				specs[i][0]=rs.getString(1);
				specs[i][1]=rs.getString(2);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return specs;
	}
	/* получить неполученный заказ по логину */
	public static Order getUntakenOrder(String login) {
		Order order = new Order();
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		String query="SELECT Users_info.FIO, Users_info.Phone, Users_info.Email, \r\n"
				+ "Buy_order.Delivery_Address, Buy_order.DateOfOrder, \r\n"
				+ "Buy_order.Total_cost, Buy_order.Is_taken, Buy_order.Is_paid, "
				+ "Buy_order.ID FROM Buy_order\r\n"
				+ "LEFT JOIN User ON Buy_order.ID_user=User.ID\r\n"
				+ "LEFT JOIN Users_info ON User.ID=Users_info.ID_user\r\n"
				+ "WHERE Buy_order.ID_user=User.ID AND Buy_order.Is_taken=0 AND User.Login=?;";
		try {
			pr=con.prepareStatement(query);
			pr.setString(1, login);
			ResultSet rs = pr.executeQuery();
		
			while (rs.next()) {
				order.setBuyersFIO(rs.getString(1));
				order.setPhoneNumber(rs.getString(2));
				order.setEmail(rs.getString(3));
				order.setDeliveryAddress(rs.getString(4));
				order.setDateOfOrder(rs.getString(5));
				order.setTotalCost(rs.getFloat(6));
				order.setDeliveryState(rs.getBoolean(7));
				order.setPaymentState(rs.getBoolean(8));
				order.setID(rs.getInt(9));
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}
	/* получить неполученные заказы по логину */
	public static ArrayList<Order> getUntakenOrders(String login) {
		Order order;
		ArrayList<Order> orders = new ArrayList<>();
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		String query="SELECT Users_info.FIO, Users_info.Phone, Users_info.Email, \r\n"
				+ "Buy_order.Delivery_Address, Buy_order.DateOfOrder, \r\n"
				+ "Buy_order.Total_cost, Buy_order.Is_taken, Buy_order.Is_paid, "
				+ "Buy_order.ID FROM Buy_order\r\n"
				+ "LEFT JOIN User ON Buy_order.ID_user=User.ID\r\n"
				+ "LEFT JOIN Users_info ON User.ID=Users_info.ID_user\r\n"
				+ "WHERE Buy_order.ID_user=User.ID AND Buy_order.Is_taken=0 AND User.Login=?;";
		try {
			pr=con.prepareStatement(query);
			pr.setString(1, login);
			ResultSet rs = pr.executeQuery();
		
			while (rs.next()) {
				order = new Order();
				order.setBuyersFIO(rs.getString(1));
				order.setPhoneNumber(rs.getString(2));
				order.setEmail(rs.getString(3));
				order.setDeliveryAddress(rs.getString(4));
				order.setDateOfOrder(rs.getString(5));
				order.setTotalCost(rs.getFloat(6));
				order.setDeliveryState(rs.getBoolean(7));
				order.setPaymentState(rs.getBoolean(8));
				order.setID(rs.getInt(9));
				orders.add(order);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}
	/* получить ID юзера по его логину (для createUser) */
	public static int getUserID(String login) {
		Connection con = ConnectionPool.getInstance().getConnection();
		int userID = 0;
		
		PreparedStatement pr = null;
		try {
			String query="SELECT ID from User WHERE Login=?;";
			pr=con.prepareStatement(query);
			pr.setString(1, login);
			ResultSet rs = pr.executeQuery();
			while(rs.next()) {
				userID=rs.getInt(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userID;
	}
	public static String getUserRole(String login) {
		Connection con = ConnectionPool.getInstance().getConnection();
		String role = "guest";
		
		PreparedStatement pr = null;
		try {
			String query="SELECT Role.Name FROM Role LEFT JOIN User ON Role.ID=User.ID_Role WHERE User.Login=?;";
			pr=con.prepareStatement(query);
			pr.setString(1, login);
			ResultSet rs = pr.executeQuery();
			while(rs.next()) {
				role=rs.getString(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return role;
	}
	/* получить инфо о пользователе (фио, емейл, телефон), зная его логин */
	public static String[] getUsersInfo(String login) {
		String[] usersInfo = new String[4];
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		String query="SELECT FIO, Email, Phone, Address FROM Users_info "
				+ "LEFT JOIN User ON Users_info.ID_user=User.ID \r\n"
				+ "WHERE User.Login=?;";
		try {
			pr=con.prepareStatement(query);
			pr.setString(1, login);
			ResultSet rs = pr.executeQuery();
		
			while (rs.next()) {
				usersInfo[0]=rs.getString(1); // фио
				usersInfo[1]=rs.getString(2); // емейл
				usersInfo[2]=rs.getString(3); // телефон
				usersInfo[3]=rs.getString(4); // адрес
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return usersInfo;
	}
	public static int getUsersProfileID(String login) {
		int profileID = 0;
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		String query="SELECT users_info.ID \r\n"
				+ "FROM Users_info LEFT JOIN User ON Users_info.ID_user=User.ID \r\n"
				+ "WHERE User.Login=?";
		try {
			pr=con.prepareStatement(query);
			pr.setString(1, login);
			ResultSet rs = pr.executeQuery();
		
			while (rs.next()) {
				profileID=rs.getInt(1);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return profileID;
	}
	/* Получить X количество товаров */
	public static ArrayList<Product> getProductsFromXtoY(int first, int amount, String sort) {
		ArrayList<Product> products = new ArrayList<Product>();
		Product product = null;
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		String query="SELECT Product.ID, Product.Name, Product.Cost, Product.Short_desc, \r\n"
				+ "Product.Amount, Category.ID, Category.Name FROM Product LEFT JOIN Category ON \r\n"
				+ "Product.ID_category = Category.ID LIMIT ?, ?;";;
		switch (sort) {
		case ("CostHigh2Low"):
			query="SELECT Product.ID, Product.Name, Product.Cost, Product.Short_desc, \r\n"
					+ "Product.Amount, Category.ID, Category.Name FROM Product LEFT JOIN Category ON \r\n"
					+ "Product.ID_category = Category.ID ORDER BY Product.Cost DESC LIMIT ?, ?;";
			break;
		case ("CostLow2High"):
			query="SELECT Product.ID, Product.Name, Product.Cost, Product.Short_desc, \r\n"
					+ "Product.Amount, Category.ID, Category.Name FROM Product LEFT JOIN Category ON \r\n"
					+ "Product.ID_category = Category.ID ORDER BY Product.Cost LIMIT ?, ?;";
			break;
		case ("IDHigh2Low"):
			query="SELECT Product.ID, Product.Name, Product.Cost, Product.Short_desc, \r\n"
					+ "Product.Amount, Category.ID, Category.Name FROM Product LEFT JOIN Category ON \r\n"
					+ "Product.ID_category = Category.ID ORDER BY Product.ID DESC LIMIT ?, ?;";
			break;
		case ("IDLow2High"):
			query="SELECT Product.ID, Product.Name, Product.Cost, Product.Short_desc, \r\n"
					+ "Product.Amount, Category.ID, Category.Name FROM Product LEFT JOIN Category ON \r\n"
					+ "Product.ID_category = Category.ID ORDER BY Product.ID LIMIT ?, ?;";
			break;
		}
		try {
			pr=con.prepareStatement(query);
			pr.setInt(1, first);
			pr.setInt(2, amount);
			System.out.println(pr);
			ResultSet rs = pr.executeQuery();
			
			while (rs.next()) {
				product = new Product();
				product.setID(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setCostPerOne(rs.getFloat(3));
				product.setShortDesc(rs.getString(4));
				product.setAmount(rs.getInt(5));
				product.setCategoryID(rs.getInt(6));
				product.setCategoryName(rs.getString(7));
				product.setRows(getAmountOfSpecs(product.getID()));
				product.setSpecs(getSpecs(product.getRows(), product.getID()));
				products.add(product);
			}
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}
	/* поиск товара по запросу (название, описание) */
	// TODO возможно по характеристике
	public static ArrayList<Product> searchProduct(String req, int first, int amount, int[] categoryID,
			float fromCost, float toCost) {
		req = "%" + req + "%";
		ArrayList<Product> products = new ArrayList<Product>();
		Product product = null;
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		String query="SELECT Product.ID, Product.Name, Product.Cost, Product.Short_desc, \r\n"
				+ "Product.Amount, Category.ID, Category.Name FROM Product LEFT JOIN Category ON \r\n"
				+ "Product.ID_category = Category.ID WHERE (Product.Name LIKE ? OR Product.Short_desc LIKE ?) ";
		if(categoryID!=null) {
			query = query + "AND (Category.ID=? ";
			if(categoryID.length!=1)
				for(int i=1; i<categoryID.length; i++)
					query = query + "OR Category.ID=? ";
			query = query + ") ";
		}
		query = query + "AND (Cost BETWEEN ? AND ?) LIMIT ?, ?;";
		try {
			pr=con.prepareStatement(query);
			pr.setString(1, req);
			pr.setString(2, req);
			if(categoryID!=null) { // если ввели категорию
				int i;
				for(i=0; i<categoryID.length; i++)
					pr.setInt(i+3, categoryID[i]);
				pr.setFloat(i+3, fromCost);
				pr.setFloat(i+4, toCost);
				pr.setInt(i+5, first);
				pr.setInt(i+6, amount);
			}
			else { // если ввели ТОЛЬКО цену
				pr.setFloat(3, fromCost);
				pr.setFloat(4, toCost);
				pr.setInt(5, first);
				pr.setInt(6, amount);
			}
			System.out.println("pr= "+pr); // debugging
			ResultSet rs = pr.executeQuery();
			
			while (rs.next()) {
				product = new Product();
				product.setID(rs.getInt(1));
				product.setName(rs.getString(2));
				product.setCostPerOne(rs.getFloat(3));
				product.setShortDesc(rs.getString(4));
				product.setAmount(rs.getInt(5));
				product.setCategoryID(rs.getInt(6));
				product.setCategoryName(rs.getString(7));
				product.setRows(getAmountOfSpecs(product.getID()));
				product.setSpecs(getSpecs(product.getRows(), product.getID()));
				products.add(product);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}
	/* Установить количество товаров в корзине */
	public static boolean setCartsProductAmount(String login, String prodName, int amount) {
		boolean bool = false;
		Connection con = ConnectionPool.getInstance().getConnection();
		int cartID = getCartID(login);
		int prodID = getProductID(prodName);
		// System.out.println(cartID); // debugging
		// System.out.println(prodID); // debugging
		
		PreparedStatement pr = null;
		String query="UPDATE Cart_element\r\n"
				+ "SET Amount = ? \r\n"
				+ "WHERE ID_product = ? AND ID_Cart = ?";
		try {
			pr = con.prepareStatement(query);
			pr.setInt(1, amount);
			pr.setInt(2, prodID);
			pr.setInt(3, cartID);
			pr.executeUpdate();
			bool = true;
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bool;
	}
	public static void setCartsTotalCost(String login, float cost) {
		Connection con = ConnectionPool.getInstance().getConnection();
		int cartID = getCartID(login);
		
		PreparedStatement pr = null;
		String query="UPDATE Cart "
				+ "SET Total_cost = ? "
				+ "WHERE ID=?";
		try {
			pr = con.prepareStatement(query);
			pr.setFloat(1, cost);
			pr.setInt(2, cartID);
			pr.executeUpdate();
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/* установка заказа */
	public static boolean setOrder() {
		boolean isSet = false; // изменился ли заказ
		return isSet;
	}
	public static boolean setOrdersAdrs(int ID, String address) {
		boolean bool = false;
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		try {
			String query="UPDATE Buy_order SET Address=? \r\n"
					+ "WHERE ID=?;";
			pr = con.prepareStatement(query);
			pr.setString(1, address);
			pr.setInt(2, ID);
			pr.executeUpdate();
			bool = true;
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bool;
	}
	public static boolean setOrdersCost(int ID, float totalCost) {
		boolean bool = false;
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		try {
			String query="UPDATE Buy_order SET Total_cost=? \r\n"
					+ "WHERE ID=?;";
			pr = con.prepareStatement(query);
			pr.setFloat(1, totalCost);
			pr.setInt(2, ID);
			pr.executeUpdate();
			bool = true;
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bool;
	}
	public static boolean setOrdersPaid(int ID, boolean isPaid) {
		boolean bool = false;
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		try {
			String query="UPDATE Buy_order SET Is_Paid=? \r\n"
					+ "WHERE ID=?;";
			pr = con.prepareStatement(query);
			pr.setBoolean(1, isPaid);
			pr.setInt(2, ID);
			pr.executeUpdate();
			bool = true;
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bool;
	}
	public static boolean setOrdersTaken(int ID, boolean isTaken) {
		boolean bool = false;
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		try {
			String query="UPDATE Buy_order SET Is_Taken=? \r\n"
					+ "WHERE ID=?;";
			pr = con.prepareStatement(query);
			pr.setBoolean(1, isTaken);
			pr.setInt(2, ID);
			pr.executeUpdate();
			bool = true;
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bool;
	}
	public static boolean setProductsAmount(String name, int amount) {
		boolean isSet = false; // изменилось ли количество?
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		try {
			String query="UPDATE Product SET Amount=? \r\n"
					+ "WHERE Name=?;";
			pr = con.prepareStatement(query);
			pr.setInt(1, amount);
			pr.setString(2, name);
			pr.executeUpdate();
			isSet = true;
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSet;
	}
	public static boolean setProductsCategory(String productName, String newCategoryName) {
		boolean isSet = false; // изменилась ли категория? 
		Connection con = ConnectionPool.getInstance().getConnection();
		int categoryID=dbFunc.getCategoryID(newCategoryName);
		
		PreparedStatement pr = null;
		try {
			String query="UPDATE Product SET ID_category=? \r\n"
					+ "WHERE Name=?;";
			pr = con.prepareStatement(query);
			pr.setInt(1, categoryID);
			pr.setString(2, productName);
			pr.executeUpdate();
			isSet = true;
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSet;
	}
	public static boolean setProductsCostPerOne(String cost, String name) {
		boolean isSet = false; // изменилась ли цена?
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		try {
			String query="UPDATE Product SET Cost=? \r\n"
					+ "WHERE Name=?;";
			pr = con.prepareStatement(query);
			pr.setString(1, cost);
			pr.setString(2, name);
			pr.executeUpdate();
			isSet = true;
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSet;
	}
	public static boolean setProductsName(String oldName, String newName) {
		boolean isSet = false; // изменилось ли наименование? 
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		try {
			String query="UPDATE Product SET Name=? \r\n"
					+ "WHERE Name=?;";
			pr = con.prepareStatement(query);
			pr.setString(1, newName);
			pr.setString(2, oldName);
			pr.executeUpdate();
			isSet = true;
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSet;
	}
	public static boolean setProductsShortDesc(String name, String shortDesc) {
		boolean isSet = false; // изменилось ли описание?
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		try {
			String query="UPDATE Product SET Short_desc=? \r\n"
					+ "WHERE Name=?;";
			pr = con.prepareStatement(query);
			pr.setString(1, shortDesc);
			pr.setString(2, name);
			pr.executeUpdate();
			isSet = true;
			
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSet;
	}
	// TODO не понимаю как это сделать, установка характеристики товара. Наверное нужно
	// сделать функции для изменения каждого столбца каждой таблицы (ору пиздец)
	public static boolean setProductsSpecs(String productName, String specName, String spec) {
		boolean isSet = false; // установлены ли хар-ки
		Connection con = ConnectionPool.getInstance().getConnection();
		int specID=getSpecID(specName); // получить айди хар-ки
		int productID=getProductID(productName);
		PreparedStatement pr = null;
		try {
			String query="UPDATE Products_description SET Text=? \r\n"
					+ "WHERE ID_product=? AND ID_description=?;";
			pr = con.prepareStatement(query);
			pr.setString(1, spec);
			pr.setInt(2, productID);
			pr.setInt(3, specID);
			pr.executeUpdate();
			isSet = true;
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSet;
	}
	/* установить номер телефона юзера */
	public static boolean setUsersAddress(String login, String address) {
		boolean isSet = false; // изменился ли адрес
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		try {
			String query="UPDATE Users_info, User SET Users_info.Address=? \r\n"
					+ "WHERE Users_info.ID_user=user.id and user.login=?;";
			pr = con.prepareStatement(query);
			pr.setString(1, address); // адрес
			pr.setString(2, login);
			pr.executeUpdate();
			isSet = true;
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSet;
	}
	/* установить емейл юзера */
	public static boolean setUsersEmail(String login, String email) {
		boolean isSet = false; // изменился ли емейл
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		try {
			String query="UPDATE Users_info, User SET Users_info.Email=? \r\n"
					+ "WHERE Users_info.ID_user=user.id and user.login=?;";
			pr = con.prepareStatement(query);
			pr.setString(1, email); // емейл
			pr.setString(2, login);
			pr.executeUpdate();
			isSet = true;
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSet;
	}
	/* установить ФИО юзера */
	public static boolean setUsersFIO(String login, String FIO) {
		boolean isSet = false; // изменилось ли ФИО
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		try {
			String query="UPDATE Users_info, User SET Users_info.FIO=? \r\n"
					+ "WHERE Users_info.ID_user=user.id and user.login=?;";
			pr = con.prepareStatement(query);
			pr.setString(1, FIO); // фио
			pr.setString(2, login);
			pr.executeUpdate();
			isSet = true;
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSet;
	}
	/* установить логин */
	public static boolean setUsersLogin(String oldLogin, String newLogin) {
		boolean isSet = false; // изменился логин
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		try {
			String query="UPDATE User SET Login=? WHERE Login=?";
			pr = con.prepareStatement(query);
			pr.setString(1, newLogin);
			pr.setString(2, oldLogin);
			pr.executeUpdate();
			isSet = true;
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSet;
	}
	/* установить пароль */
	public static boolean setUsersPassword(String login, String newPassword) {
		boolean isSet = false; // изменился ли пароль
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		try {
			String query="UPDATE User SET Password=? WHERE Login=?";
			pr = con.prepareStatement(query);
			pr.setString(1, newPassword);
			pr.setString(2, login);
			pr.executeUpdate();
			isSet = true;
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSet;
	}
	/* установить номер телефона юзера */
	public static boolean setUsersPhone(String login, String phone) {
		boolean isSet = false; // изменился ли номер
		Connection con = ConnectionPool.getInstance().getConnection();
		
		PreparedStatement pr = null;
		try {
			String query="UPDATE Users_info, User SET Users_info.Phone=? \r\n"
					+ "WHERE Users_info.ID_user=user.id and user.login=?;";
			pr = con.prepareStatement(query);
			pr.setString(1, phone); // телефон 
			pr.setString(2, login);
			pr.executeUpdate();
			isSet = true;
		
		} catch (Exception e) {
			System.err.println(e);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isSet;
	}
}
