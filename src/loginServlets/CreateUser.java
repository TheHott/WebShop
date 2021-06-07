package loginServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dbFunc;

/**
 * Servlet implementation class CreateUser
 */
@WebServlet("/CreateUser")
public class CreateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = "./index.jsp";
		response.sendRedirect(path);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String passwordCnfrm = request.getParameter("passwordCnfrm");
		String[] usersInfo = new String[4];
		String errPath = "./signUp.jsp";
		usersInfo[0]=request.getParameter("fio");
		usersInfo[1]=request.getParameter("email");
		usersInfo[2]=request.getParameter("phone");
		usersInfo[3]=request.getParameter("address");
		
		boolean bool = false;
		if(password.equals(passwordCnfrm)) {
			bool = dbFunc.createUser(login, password, usersInfo);
			if(bool) {
				request.getSession().setAttribute("msg", "Регистрация прошла успешно");
				doGet(request, response);
			}
			else {
				request.getSession().setAttribute("msg", "Ошибка регистрации");
				response.sendRedirect(errPath);
			}
		}
		else {
			request.getSession().setAttribute("msg", "Пароли не совпадают");
			response.sendRedirect(errPath);
		}
	}
}
