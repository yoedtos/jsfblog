package net.yoedtos.blog.control.filter;

import static net.yoedtos.blog.control.Session.BADGE;
import static net.yoedtos.blog.control.Session.INDEX;
import static net.yoedtos.blog.control.Session.SESSION_GUEST;
import static net.yoedtos.blog.control.Session.SESSION_KEY_ROLE;
import static net.yoedtos.blog.control.Session.SESSION_KEY_USER;
import static net.yoedtos.blog.control.Session.SESSION_USER;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.yoedtos.blog.control.handler.CookieHandler;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.factory.ServiceFactory;
import net.yoedtos.blog.model.dto.RememberData;
import net.yoedtos.blog.model.dto.UserDTO;
import net.yoedtos.blog.service.RememberService;

@WebFilter(filterName = "rememberMe", urlPatterns = {"/*"})
public class RememberFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(RememberFilter.class);

	private RememberService rememberService;

	public RememberFilter() {
		rememberService = ServiceFactory.create(RememberService.class);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession();
	
		if (session.getAttribute(SESSION_KEY_USER) == null 
							&& session.getAttribute(SESSION_GUEST) == null) {
			
			CookieHandler cookieHandler = new CookieHandler((HttpServletRequest)request,
															(HttpServletResponse)response);
			
			String index = cookieHandler.getCookieValue(INDEX);
			String badge = cookieHandler.getCookieValue(BADGE);

			if (index != null && badge != null) {
				RememberData remember = new RememberData.Builder().index(index).badge(badge).build();
				try {
					UserDTO userDto = rememberService.getIn(remember);
					session.setAttribute(SESSION_KEY_USER, userDto.getUsername());
					session.setAttribute(SESSION_KEY_ROLE, userDto.getRole().getValue());
					session.setAttribute(SESSION_USER, userDto);
				} catch (ServiceException e) {
					LOGGER.error(e.getMessage());
				}
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void destroy() {
	}
}
