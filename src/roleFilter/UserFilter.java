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

/* Фильтр пропускает только гостей */
public class UserFilter implements Filter {
	
    public UserFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String index = "/WebShop/index.jsp";
		
		User user = (User) httpRequest.getSession().getAttribute("user");
		if(user==null)
			chain.doFilter(request, response);
		else
			httpResponse.sendRedirect(index);
	}
	public void init(FilterConfig fConfig) throws ServletException {
 
	}

}
