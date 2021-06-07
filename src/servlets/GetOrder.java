package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Order;
import database.dbFunc;

/**
 * Servlet implementation class GetOrder
 */
@WebServlet("/GetOrder")
public class GetOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetOrder() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String login = request.getParameter("login");
		ArrayList<Order> orders = new ArrayList<>();
		orders = dbFunc.getUntakenOrders(login);
		request.setAttribute("usersOrders", orders);
		if (orders.isEmpty())
			request.getSession().setAttribute("msg", "Заказы не найдены");
    	doGet(request, response);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String path = request.getHeader("referer");
		String path = "/admin/adminOrder.jsp";
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}
}
