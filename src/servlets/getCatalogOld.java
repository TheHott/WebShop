package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;

import database.dbFunc;
import classes.Category;
import classes.Product;

/**
 * Servlet implementation class getCatalog
 */
@WebServlet("/catalogOld")
public class getCatalogOld extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Category> categories = dbFunc.getAllCategories();
		ArrayList<Product> products = dbFunc.getCatalog();
		request.getSession().setAttribute("products", products);
		request.getSession().setAttribute("categories", categories);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		String path = "./catalog.jsp";
		response.sendRedirect(path);
	}
}
