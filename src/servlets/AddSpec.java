package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dbFunc;

/**
 * Servlet implementation class AddSpec
 */
@WebServlet("/AddSpec")
public class AddSpec extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddSpec() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getHeader("referer");
    	response.sendRedirect(path);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		String prodName = request.getParameter("productName");
		String specName = request.getParameter("specName");
		String spec = request.getParameter("spec");
		
		boolean bool = dbFunc.addSpec(prodName, specName, spec);
		if(bool)
			request.getSession().setAttribute("msg", "Характеристика успешно добавлена");
		else
			request.getSession().setAttribute("msg", "Характеристика не была добавлена (неизвестная ошибка)");
		doGet(request, response);
	}

}
