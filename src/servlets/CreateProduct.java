package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dbFunc;

/**
 * Servlet implementation class CreateProduct
 */
@WebServlet("/CreateProduct")
public class CreateProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateProduct() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = "/index.jsp";
		request.getRequestDispatcher(path).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		String name = request.getParameter("newProdName");
		String costPerOne = request.getParameter("newCost");
		String amount = request.getParameter("newAmount");
		String shortDesc = request.getParameter("newShortDesc");
		String category = request.getParameter("newCategory");
		
		boolean bool = dbFunc.createProduct(name, costPerOne, amount, shortDesc, category);
		request.setAttribute("prodCreated", bool);
		doGet(request, response);
	}

}
