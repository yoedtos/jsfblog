package net.yoedtos.blog.control.filter;

import static net.yoedtos.blog.control.Session.SESSION_KEY_ROLE;
import static net.yoedtos.blog.control.Session.SESSION_KEY_USER;
import static net.yoedtos.blog.control.filter.HomeFilter.LOGIN;
import static net.yoedtos.blog.control.filter.LoginFilter.USER_HOME;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.yoedtos.blog.model.Role;

@WebFilter(filterName="adminFilter", urlPatterns={"/admin/*"})
public class AdminFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		
		HttpSession session = ((HttpServletRequest)request).getSession();
		
		if(session.getAttribute(SESSION_KEY_USER) == null) {
			((HttpServletResponse)response).sendRedirect(LOGIN);
		} else if(session.getAttribute(SESSION_KEY_ROLE).equals(Role.ADMINISTRATOR.getValue())) {
			chain.doFilter(request, response);
		} else {
			((HttpServletResponse)response).sendRedirect(USER_HOME);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {}
	
	@Override
	public void destroy() {}

}
