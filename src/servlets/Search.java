package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.Product;
import database.dbFunc;

/**
 * Servlet implementation class Search
 */
@WebServlet("/search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Search() {
        super();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = "/search.jsp";
		String req = request.getParameter("req");
    	int page = Integer.parseInt(request.getParameter("page"));
    	String[] categoryID = null;
    	int[] categories = null;
    	if(request.getParameterValues("c[]")!=null) {
    		categoryID = request.getParameterValues("c[]");
    		categories = new int[categoryID.length];
    		for(int i=0; i<categoryID.length; i++)
    			categories[i] = Integer.parseInt(categoryID[i]);
    		Arrays.sort(categories);
    	}
    	double elements;
    	int first=10*(page-1);
		double amount = 10;
		double pages;
		float fromCost=0;
		float toCost=999999;
		// TODO
		/*
		if(request.getParameter("fromCost")!=null)
			fromCost=Float.parseFloat(request.getParameter("fromCost"));
		
		if(request.getParameter("toCost")!=null)
			toCost=Float.parseFloat(request.getParameter("toCost"));*/
		
		if(fromCost<0 || toCost<0)
			request.getSession().setAttribute("msg", "Нельзя ставить отрицательные значения стоимости");
		else if (toCost<fromCost) {
			request.getSession().setAttribute("msg", "'После' не может быть меньше 'До'");
			toCost=fromCost;
			request.setAttribute("toCost", fromCost);
			getServletContext().getRequestDispatcher(path).forward(request, response);
		}
		
		if(request.getSession().getAttribute("category")==null)
			request.getSession().setAttribute("categories", dbFunc.getAllCategories());
		ArrayList<Product> products = new ArrayList<>();
		products = dbFunc.searchProduct(req, first, (int) amount, categories, fromCost, toCost);
		elements = dbFunc.getAmountOfProductsSearch(req, categories, fromCost, toCost);
		pages = elements / amount;
		pages = Math.ceil(pages); 
		ArrayList<Integer> pagesArr = new ArrayList<>();
		for(int i=1; i<=pages; i++)
			pagesArr.add(i);
		request.setAttribute("pagesArr", pagesArr);
		request.setAttribute("products", products);
		
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

}
