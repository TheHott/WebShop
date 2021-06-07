package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Order;
import classes.User;
import database.dbFunc;

/**
 * Servlet implementation class GetUntakenOrders
 */
@WebServlet("/GetUntakenOrders")
public class GetUntakenOrders extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUntakenOrders() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = (User) request.getSession().getAttribute("user");
    	String login = user.getLogin();
		ArrayList<Order> orders = new ArrayList<>();
		orders = dbFunc.getUntakenOrders(login);
		request.setAttribute("orders", orders);
    	doGet(request, response);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = "/myOrders.jsp";
		
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

}
