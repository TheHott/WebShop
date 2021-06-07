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
 * Servlet implementation class GetCatalogTest
 */
@WebServlet("/GetCatalog")
public class GetCatalog extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCatalog() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Category> categories = dbFunc.getAllCategories();
		double elements;
		double amount = 10;
		double pages;
		String sort = "Product.ID DESC";
		if(request.getParameter("sort")!=null)
			sort = request.getParameter("sort");
		
		ArrayList<Product> products = dbFunc.getProductsFromXtoY(0, (int) amount, sort);
		
		request.setAttribute("products", products);
		request.getSession().setAttribute("categories", categories);
		
		elements=dbFunc.getAmountOfProducts();
		pages = elements / amount;
		// System.out.println(pages); // debugging
		pages = Math.ceil(pages); 
		// System.out.println(pages); // debugging
		ArrayList<Integer> pagesArr = new ArrayList<>();
		for(int i=1; i<=pages; i++)
			pagesArr.add(i);
		request.setAttribute("pagesArr", pagesArr);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		String path = "./catalog?page=1";
		response.sendRedirect(path);
	}
}
