package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dbFunc;

/**
 * Servlet implementation class SetProduct
 */
@WebServlet("/SetProduct")
public class SetProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetProduct() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = "/index.jsp";
		request.getRequestDispatcher(path).forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		
		String oldName=request.getParameter("oldName");
		// String newName=request.getParameter("newName");
		String costPerOne = request.getParameter("cost");
		int amount=Integer.parseInt(request.getParameter("amount"));
		String shortDesc=request.getParameter("desc");
		String category=request.getParameter("category");
		// String specName=request.getParameter("specName");
		// String spec=request.getParameter("spec");
		
		boolean bool = false;
		request.setAttribute("nameCorrect", bool);
		if(oldName!="") {
			bool = true;
			request.setAttribute("nameCorrect", bool);
			if(costPerOne!="") {
				boolean bool1 = dbFunc.setProductsCostPerOne(costPerOne, oldName);
				request.setAttribute("costSet", bool1);
			}
			if(amount!=0) {
				boolean bool2 = dbFunc.setProductsAmount(oldName, amount);
				request.setAttribute("amountSet", bool2);
			}
			if(shortDesc!="") {
				boolean bool3 = dbFunc.setProductsShortDesc(oldName, shortDesc);
				request.setAttribute("descSet", bool3);
			}
			if(category!="") {
				boolean bool4 = dbFunc.setProductsCategory(oldName, category);
				request.setAttribute("categorySet", bool4);
			}
			/* if(spec!="" && specName!="") {
				boolean bool5 = dbFunc.setProductsSpecs(oldName, specName, spec);
				request.setAttribute("specsSet", bool5);
			} */
		}
		doGet(request, response);
	}

}
