package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dbFunc;

/**
 * Servlet implementation class SetProductsSpecs
 */
@WebServlet("/SetProductsSpecs")
public class SetProductsSpecs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetProductsSpecs() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = "/index.jsp";
		request.getRequestDispatcher(path).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		
		String productName=request.getParameter("prodName");
		String specName=request.getParameter("specName");
		String spec=request.getParameter("spec");
		
		boolean bool;
		if(productName!="" && specName!="" && spec!="") {
			bool=dbFunc.setProductsSpecs(productName, specName, spec);
			request.setAttribute("specsSet", bool);
		}
		doGet(request, response);
	}

}
