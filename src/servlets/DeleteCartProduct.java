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

/**
 * Servlet implementation class DeleteCartProduct
 */
@WebServlet("/DeleteCartProduct")
public class DeleteCartProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DeleteCartProduct() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getHeader("referer");
		request.getSession().setAttribute("msg", "Товар удален из корзины!");
    	response.sendRedirect(path);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		String prodName = request.getParameter("prodName");
		String login = cart.getOwnersLogin();
		Product cartProduct = cart.getProduct(prodName);
		int amount = cartProduct.getAmount();
		float newCost = cart.getTotalCost() - cartProduct.getCostPerOne()*amount;
		
		if(login!="guest" && cartProduct!=null) { 
			dbFunc.setCartsTotalCost(login, newCost);
			dbFunc.deleteCartProduct(login, prodName);
		}
		
		cart.setTotalCost(newCost);
		cart.removeProduct(cartProduct);
		request.getSession().setAttribute("cart", cart);
		
    	doGet(request, response);
	}

}
