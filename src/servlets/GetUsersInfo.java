package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dbFunc;

/**
 * Servlet implementation class GetUsersInfo
 */
@WebServlet("/GetUsersInfo")
public class GetUsersInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUsersInfo() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String login = request.getParameter("login");
		String[] usersInfo = new String[4];
		usersInfo = dbFunc.getUsersInfo(login);
		request.setAttribute("fio", usersInfo[0]);
		request.setAttribute("email", usersInfo[1]);
		request.setAttribute("phone", usersInfo[2]);
		request.setAttribute("address", usersInfo[3]);
		doGet(request, response);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = "/index.jsp";
		
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}

}
