package net.yoedtos.blog.control.filter;

import static net.yoedtos.blog.control.Session.SESSION_KEY_ROLE;
import static net.yoedtos.blog.control.Session.SESSION_KEY_USER;
import static net.yoedtos.blog.util.TestConstants.LOGIN_URI;
import static net.yoedtos.blog.util.TestConstants.USERNAME_ONE;
import static net.yoedtos.blog.util.TestConstants.USER_HOME_URI;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.yoedtos.blog.model.Role;

@RunWith(MockitoJUnitRunner.class)
public class FiltersTest {

	private final static String DOTS = "..";
	private final static String ADMIN_URI = "/admin/";
	private final static String HOME_URI = "/home/";
	
	@Mock 
	private HttpServletRequest mockRequest;
	@Mock 
	private HttpServletResponse mockResponse;
	@Mock 
	private HttpSession mockSession;
	@Mock 
	private FilterChain mockFilterChain;
	@Mock 
	private FilterConfig mockFilterConfig;
 
	@Test
	public void loggedUserGetLoginShouldRedirectToUserHome() throws IOException, ServletException {
		when(mockRequest.getRequestURI()).thenReturn(LOGIN_URI);
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute(SESSION_KEY_USER)).thenReturn(USERNAME_ONE);
		
		LoginFilter loginFilter = new LoginFilter();
		loginFilter.init(mockFilterConfig);
		loginFilter.doFilter(mockRequest, mockResponse, mockFilterChain);
		loginFilter.destroy();
	
		verify(mockResponse, times(1)).sendRedirect(DOTS + USER_HOME_URI);
	}
	
	@Test
	public void notLoggedUserGetLoginShouldGoThrough() throws IOException, ServletException {
		when(mockRequest.getRequestURI()).thenReturn(LOGIN_URI);
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute(SESSION_KEY_USER)).thenReturn(null);
		
		LoginFilter loginFilter = new LoginFilter();
		loginFilter.init(mockFilterConfig);
		loginFilter.doFilter(mockRequest, mockResponse, mockFilterChain);
		loginFilter.destroy();
	
		verify(mockResponse, never()).sendRedirect(DOTS + USER_HOME_URI);
	}
	
	@Test
	public void notLoggedUserGetHomeShouldRedirectToLogin() throws IOException, ServletException {
		when(mockRequest.getRequestURI()).thenReturn(HOME_URI);
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute(SESSION_KEY_USER)).thenReturn(null);
		
		HomeFilter homeFilter = new HomeFilter();
		homeFilter.init(mockFilterConfig);
		homeFilter.doFilter(mockRequest, mockResponse, mockFilterChain);
		homeFilter.destroy();
	
		verify(mockResponse, times(1)).sendRedirect(DOTS + LOGIN_URI);
	}
	
	@Test
	public void loggedUserGetHomeShouldGoThrough() throws IOException, ServletException {
		when(mockRequest.getRequestURI()).thenReturn(HOME_URI);
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute(SESSION_KEY_USER)).thenReturn(USERNAME_ONE);
		
		HomeFilter homeFilter = new HomeFilter();
		homeFilter.init(mockFilterConfig);
		homeFilter.doFilter(mockRequest, mockResponse, mockFilterChain);
		homeFilter.destroy();
	
		verify(mockResponse, never()).sendRedirect(DOTS + USER_HOME_URI);
	}
	
	@Test
	public void loggedAdminUserGetAdminShouldGoThrough() throws IOException, ServletException {
		when(mockRequest.getRequestURI()).thenReturn(ADMIN_URI);
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute(SESSION_KEY_USER)).thenReturn(USERNAME_ONE);
		when(mockSession.getAttribute(SESSION_KEY_ROLE)).thenReturn(Role.ADMINISTRATOR.getValue());
		
		AdminFilter adminFilter = new AdminFilter();
		adminFilter.init(mockFilterConfig);
		adminFilter.doFilter(mockRequest, mockResponse, mockFilterChain);
		adminFilter.destroy();
	
		verify(mockResponse, never()).sendRedirect(DOTS + USER_HOME_URI);
		verify(mockResponse, never()).sendRedirect(DOTS + LOGIN_URI);
	}
	
	@Test
	public void notLoggedAdminUserGetAdminShouldRedirectToUserHome() throws IOException, ServletException {
		when(mockRequest.getRequestURI()).thenReturn(ADMIN_URI);
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute(SESSION_KEY_USER)).thenReturn(USERNAME_ONE);
		when(mockSession.getAttribute(SESSION_KEY_ROLE)).thenReturn(Role.MEMBER.getValue());
		
		AdminFilter adminFilter = new AdminFilter();
		adminFilter.init(mockFilterConfig);
		adminFilter.doFilter(mockRequest, mockResponse, mockFilterChain);
		adminFilter.destroy();
	
		verify(mockResponse, never()).sendRedirect(DOTS + LOGIN_URI);
		verify(mockResponse, times(1)).sendRedirect(DOTS + USER_HOME_URI);
	}
	
	@Test
	public void notLoggedUserGetAdminShouldRedirectLogin() throws IOException, ServletException {
		when(mockRequest.getRequestURI()).thenReturn(ADMIN_URI);
		when(mockRequest.getSession()).thenReturn(mockSession);
		when(mockSession.getAttribute(SESSION_KEY_USER)).thenReturn(null);
		
		AdminFilter adminFilter = new AdminFilter();
		adminFilter.init(mockFilterConfig);
		adminFilter.doFilter(mockRequest, mockResponse, mockFilterChain);
		adminFilter.destroy();
	
		verify(mockResponse, times(1)).sendRedirect(DOTS + LOGIN_URI);
	}
}
