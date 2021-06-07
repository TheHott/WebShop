package loginServlets;

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

@WebServlet("/CheckUser")
public class CheckUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CheckUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = "./index.jsp";

		response.sendRedirect(path);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String role = "guest";
		User obj_User = new User(login, password);
		Cart cart = new Cart();
		
		login = dbFunc.checkUser(obj_User);
		obj_User.setLogin(login);

		if(login!=null) {
			role = dbFunc.getUserRole(login);
			String[] usersInfo = new String[4];
			usersInfo = dbFunc.getUsersInfo(login);
			
			int cartID = dbFunc.getCartID(login);
			ArrayList<Product> products = new ArrayList<Product>();
			products = dbFunc.getCartProducts(login);
			float totalCost = dbFunc.getCartTotalCost(login);
			
			obj_User.setRole(role);
			obj_User.setFIO(usersInfo[0]);
			obj_User.setEmail(usersInfo[1]);
			obj_User.setPhoneNumber(usersInfo[2]);
			obj_User.setAddress(usersInfo[3]);
			
			cart.setId(cartID);
			cart.setOwnersLogin(login);
			cart.setProducts(products);
			cart.setTotalCost(totalCost);
			request.getSession().setAttribute("user", obj_User);
			request.getSession().setAttribute("cart", cart);
			request.getSession().setAttribute("msg", "Авторизация прошла успешно");
			doGet(request, response);
		}
		else { 
			request.getSession().setAttribute("msg", "Неправильный логин и/или пароль");
			String path = "./login.jsp";
			response.sendRedirect(path);
		}
			
	}

}
