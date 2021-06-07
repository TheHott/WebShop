package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Category;
import classes.Product;
import database.dbFunc;

/**
 * Servlet implementation class GetPagesProducts
 */
@WebServlet("/catalog")
public class GetPagesProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPagesProducts() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = Integer.parseInt(request.getParameter("page"));
		ArrayList<Category> categories = dbFunc.getAllCategories();
		double amount = 10;
		int first=10*(page-1);
		double elements;
		double pages;
		String sort = "IDHigh2Low";
		if(request.getParameter("sort")!=null)
			sort = request.getParameter("sort");
		
		ArrayList<Product> products = dbFunc.getProductsFromXtoY(first, (int) amount, sort);
		
		elements=dbFunc.getAmountOfProducts();
		pages = elements / amount;
		System.out.println(pages); // debugging
		pages = Math.ceil(pages); 
		System.out.println(pages); // debugging
		ArrayList<Integer> pagesArr = new ArrayList<>();
		for(int i=1; i<=pages; i++)
			pagesArr.add(i);

		request.getSession().setAttribute("categories", categories);
		request.setAttribute("products", products);
		request.setAttribute("pagesArr", pagesArr);
		
		String path = "/catalog.jsp";
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

}
