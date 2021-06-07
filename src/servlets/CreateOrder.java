package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Cart;
import classes.Product;
import classes.User;
import database.dbFunc;

/**
 * Servlet implementation class CreateOrder
 */
@WebServlet("/CreateOrder")
public class CreateOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String path = "./cart.jsp";
		request.getSession().setAttribute("msg", "Заказ успешно оформлен");
		// request.getRequestDispatcher(path).forward(request, response);
		response.sendRedirect(path);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		User user = (User) request.getSession().getAttribute("user");
		String login = cart.getOwnersLogin();
		String address = "Не указано";
		if(login=="guest")
			address = request.getParameter("address");
		else
			address = user.getAddress();
		
		float totalCost = cart.getTotalCost();
		ArrayList<Product> cartProducts = cart.getProducts();
		Product cartProduct = new Product();
		
		String prodName = null;
		int newAmount = 0;
		float newCost = 0;
		int prodID=0;
		int amount=0;
		
		dbFunc.createOrder(login, totalCost, address);
		dbFunc.addOrdersProduct(login, cartProducts, totalCost, address);
	
		// удалить из корзины все товары и уменьшить их количество в каталоге
		for(int i=0; i<cartProducts.size(); i++) {
			cartProduct=cartProducts.get(i);
			prodName=cartProduct.getName();
			prodID=cartProduct.getID();
			amount = dbFunc.getProductAmount(prodID);
			newAmount = amount - cartProduct.getAmount();
			if(newAmount>=0) {
				dbFunc.setProductsAmount(prodName, newAmount);
				
				if(!login.equals("guest"))
					dbFunc.deleteCartProduct(login, prodName);
			}
			else {
				System.err.println("Ошибка! Количество товара в корзине больше чем на складе. Как такое произошло?");
			}
		}
		
		// установить новую сумму корзины = 0
		if(!login.equals("guest"))
			dbFunc.setCartsTotalCost(login, newCost);
		cart.setTotalCost(newCost);
		
		cart.emptyTheProductList();
		request.getSession().setAttribute("cart", cart);
		doGet(request, response);
	}

}
