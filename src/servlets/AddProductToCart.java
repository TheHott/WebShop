package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Cart;
import classes.Product;
import database.dbFunc;

@WebServlet("/AddProductToCart")
public class AddProductToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddProductToCart() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String path = request.getHeader("referer");
    	request.getSession().setAttribute("msg", "Товар добавлен в корзину!");
    	System.out.println(path);
    	response.sendRedirect(path);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		int amount = 1;
		int productID = Integer.parseInt(request.getParameter("prodID"));
		Product product = dbFunc.getProduct(productID);
		
		String prodName = product.getName();
		
		if(cart==null) {
			cart = new Cart();
		}
		else {
			String login = cart.getOwnersLogin();
			if(login!="guest") {
				dbFunc.addProductToCart(login, prodName, amount);
				dbFunc.setCartsTotalCost(login, cart.getTotalCost()+product.getCostPerOne()*amount);
			}
		}

		cart.setTotalCost(cart.getTotalCost()+product.getCostPerOne()*amount);
		cart.addProduct(product, amount);
		
		request.getSession().setAttribute("cart", cart);
		doGet(request, response);
	}
}
