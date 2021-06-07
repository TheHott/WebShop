package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.User;
import database.dbFunc;

/**
 * Servlet implementation class SetUsersPassword
 */
@WebServlet("/SetUsersPassword")
public class SetUsersPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetUsersPassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = "./myProfile.jsp";
		response.sendRedirect(path);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		User user = (User) request.getSession().getAttribute("user");
		
		String login = user.getLogin();
		String oldPass = request.getParameter("oldPass");
		String newPass = request.getParameter("newPass");
		String newPassCnfrm = request.getParameter("newPassCnfrm");
		String errPath = "./editPassword.jsp";
		
		if(oldPass.equals(user.getPassword()))
			if(newPass.equals(newPassCnfrm)) {
				dbFunc.setUsersPassword(login, newPass);
				user.setPassword(newPass);
				request.getSession().setAttribute("msg", "Пароль обновлен");
				doGet(request, response);
			}
			else {
				request.getSession().setAttribute("msg", "Пароли не совпадают");
				response.sendRedirect(errPath);
			}
		else {
			request.getSession().setAttribute("msg", "Неправильный пароль");
			response.sendRedirect(errPath);
		}
	}

}
