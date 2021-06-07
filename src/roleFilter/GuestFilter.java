package roleFilter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classes.User;

/* Фильтр не пропускает гостей */
public class GuestFilter implements Filter {
	public void destroy() {
		// TODO Auto-generated method stub
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String login = "/WebShop/login.jsp";
		
		User user = (User) httpRequest.getSession().getAttribute("user");
		if(user != null)
			chain.doFilter(request, response);
		else
			httpResponse.sendRedirect(login);
	}

	public void init(FilterConfig fConfig) throws ServletException {
 
	}

}
