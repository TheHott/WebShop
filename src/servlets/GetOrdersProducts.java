package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Product;
import database.dbFunc;

/**
 * Servlet implementation class GetOrdersProducts
 */
@WebServlet("/GetOrdersProducts")
public class GetOrdersProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetOrdersProducts() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int orderID = Integer.parseInt(request.getParameter("orderID"));
    	ArrayList <Product> products = new ArrayList<>();
		products = dbFunc.getOrdersProducts(orderID);
		request.setAttribute("orderProducts", products);
    	doGet(request, response);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = "/order.jsp";
		
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}
}
