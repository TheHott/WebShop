package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.dbFunc;

/**
 * Servlet implementation class SetOrder
 */
@WebServlet("/SetOrder")
public class SetOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String path = request.getHeader("referer");
    	request.getSession().setAttribute("msg", "Заказ успешно изменен");
    	response.sendRedirect(path);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		
		int orderID=0;
		float totalCost=-1;
		boolean isTaken=false;
		boolean isPaid=false;
		String address = request.getParameter("address");
		if(!request.getParameter("orderID").equals(""))
			orderID=Integer.parseInt(request.getParameter("orderID"));
		if(!request.getParameter("totalCost").equals(""))
			totalCost = Float.parseFloat(request.getParameter("totalCost"));
		if(!request.getParameter("isTaken").equals(""))
			isTaken = Boolean.parseBoolean(request.getParameter("isTaken"));
		if(!request.getParameter("isPaid").equals(""))
			isPaid = Boolean.parseBoolean(request.getParameter("isPaid"));
		
		if(orderID!=0) {
			if(totalCost!=-1) {
				dbFunc.setOrdersCost(orderID, totalCost);
			}
			if(address!="") {
				dbFunc.setOrdersAdrs(orderID, address);
			}
			dbFunc.setOrdersTaken(orderID, isTaken);
			dbFunc.setOrdersPaid(orderID, isPaid);
		}
		doGet(request, response);
	}

}
