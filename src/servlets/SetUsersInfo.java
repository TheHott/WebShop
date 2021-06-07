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
 * Servlet implementation class SetUsersInfo
 */
@WebServlet("/SetUsersInfo")
public class SetUsersInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SetUsersInfo() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = "./myProfile.jsp";
		response.sendRedirect(path);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		User user = (User) request.getSession().getAttribute("user");
		
		String login = user.getLogin();
		String FIO = request.getParameter("fio");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String newLogin = request.getParameter("newLogin");
		
		dbFunc.setUsersFIO(login, FIO);
		user.setFIO(FIO);
		dbFunc.setUsersEmail(login, email);
		user.setEmail(email);
		dbFunc.setUsersPhone(login, phone);
		user.setPhoneNumber(phone);
		dbFunc.setUsersAddress(login, address);
		user.setAddress(address);
		dbFunc.setUsersLogin(login, newLogin);
		user.setLogin(newLogin);
		
		request.getSession().setAttribute("user", user);
		request.getSession().setAttribute("msg", "Данные успешно обновлены");
		doGet(request, response);
	}

}