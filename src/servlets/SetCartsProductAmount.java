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
 * Servlet implementation class SetCartsProductAmount
 */
@WebServlet("/SetCartsProductAmount")
public class SetCartsProductAmount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SetCartsProductAmount() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getHeader("referer");
    	response.sendRedirect(path);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		String prodName = request.getParameter("prodName");
		int amount = Integer.parseInt(request.getParameter("amount"));
		String login = cart.getOwnersLogin();
		Product cartProduct = cart.getProduct(prodName);
		int prodID = cartProduct.getID();
		int prodsAmount = dbFunc.getProductAmount(prodID);
		int cartProdAmount = cartProduct.getAmount();
		float cartCost = cart.getTotalCost();
		float prodCost = cartProduct.getCostPerOne();
		int amountDelta = amount-cartProdAmount;
		String amountChange = request.getParameter("amountChange");
		request.setAttribute("deleteProduct", false);
		System.out.println("amountchange = "+amountChange);
		
		if(amountChange.equals("=")) {
			if(amount>0 && amount<=prodsAmount) {
				cartCost = cartCost + amountDelta*prodCost;
				if(login!="guest") {
					dbFunc.setCartsProductAmount(login, prodName, amount);
					dbFunc.setCartsTotalCost(login, cartCost);
				}
				cartProduct.setAmount(amount);
				cart.setTotalCost(cartCost);
				request.getSession().setAttribute("msg", "Количество товара изменено");
			}
			else if(amount==0) {
				if(login!="guest") {
					dbFunc.setCartsTotalCost(login, cart.getTotalCost() - cartProduct.getCostPerOne()*cartProdAmount);
					dbFunc.deleteCartProduct(login, prodName);
				}
				cart.setTotalCost(cart.getTotalCost() - cartProduct.getCostPerOne()*cartProdAmount);
				cart.removeProduct(cartProduct);
				request.getSession().setAttribute("msg", "Товар удален");
			}
			else if(amount>prodsAmount) {
				amountDelta=prodsAmount-cartProdAmount;
				cartCost = cartCost + amountDelta*prodCost;
				if(login!="guest") {
					dbFunc.setCartsProductAmount(login, prodName, prodsAmount);
					dbFunc.setCartsTotalCost(login, cartCost);
				}
				cartProduct.setAmount(prodsAmount);
				cart.setTotalCost(cartCost);
				request.getSession().setAttribute("msg", "Нельзя установить больше чем " + prodsAmount + " шт");
			}
			else
				request.getSession().setAttribute("msg", "Ошибка! Количество меньше 0!");
		}
		else if (amountChange.equals("-")) {
			if(amount>1) {
				amount --;
				cartCost -= prodCost;
				if(login!="guest") {
					dbFunc.setCartsProductAmount(login, prodName, amount);
					dbFunc.setCartsTotalCost(login, cartCost);
				}
				cartProduct.setAmount(amount);
				cart.setTotalCost(cartCost);
				request.getSession().setAttribute("msg", "Количество товара убавлено");
			}
			else if(amount==1) {
				if(login!="guest") {
					dbFunc.setCartsTotalCost(login, cart.getTotalCost() - cartProduct.getCostPerOne()*cartProdAmount);
					dbFunc.deleteCartProduct(login, prodName);
				}
				cart.setTotalCost(cart.getTotalCost() - cartProduct.getCostPerOne()*cartProdAmount);
				cart.removeProduct(cartProduct);
				request.getSession().setAttribute("msg", "Товар удален");
			}
			else
				request.getSession().setAttribute("msg", "Ошибка. Количество не больше 1 и не равно 1. Оно 0 или меньше?");
		}
		else if (amountChange.equals("+")) {
			if(amount<prodsAmount) {
				amount ++;
				cartCost += prodCost;
				if(login!="guest") {
					dbFunc.setCartsProductAmount(login, prodName, amount);
					dbFunc.setCartsTotalCost(login, cartCost);
				}
				cartProduct.setAmount(amount);
				cart.setTotalCost(cartCost);
				request.getSession().setAttribute("msg", "Количество товара прибавлено");
			}
			else
				request.getSession().setAttribute("msg", "Ошибка! Нельзя прибавить - на складе нет больше чем " + amount + " шт.");
		}
		else
			request.getSession().setAttribute("msg", "Неизвестная ошибка изменения");
			
		doGet(request, response);
	}

}
