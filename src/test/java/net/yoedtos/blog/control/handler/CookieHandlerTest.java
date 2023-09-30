package net.yoedtos.blog.control.handler;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CookieHandlerTest {
	
	private final static String KEY = "IfBWgh4N";
	private final static String VALUE = "rbrsbkW9doWL9C6RbIfBWgh4NPyNywxqzAX3";
	private final static int MAX_AGE = 5;
	private final static String CTX_PATH = "/";
	private final static String SRV_NAME = "localhost";
	
	@Mock 
	private HttpServletRequest mockRequest;
	@Mock 
	private HttpServletResponse mockResponse;
	
	@Captor
	private ArgumentCaptor<Cookie> captor;
	
	@InjectMocks
	private CookieHandler cookieHandler;
	
	private Cookie cookie; 
	
	@Before
	public void init() {
		cookie = new Cookie(KEY, VALUE);
		cookie.setMaxAge(MAX_AGE);
		
		when(mockRequest.getContextPath()).thenReturn(CTX_PATH);
		when(mockRequest.getServerName()).thenReturn(SRV_NAME);
	}
	
	@Test
	public void whenCreateCookieShouldCreateOne() {
		cookieHandler.createCookie(KEY, VALUE, MAX_AGE);
		
		verify(mockResponse).addCookie(captor.capture());
		
		Cookie atual = captor.getValue();
		assertEquals(MAX_AGE, atual.getMaxAge());
		assertEquals(KEY, atual.getName());
		assertEquals(VALUE, atual.getValue());
		assertEquals(CTX_PATH, atual.getPath());
		assertEquals(SRV_NAME, atual.getDomain());
		
		verify(mockResponse, times(1)).addCookie(any());
	}
	
	@Test
	public void whenRemoveCookieShouldCreateEmptyOne() {		
		cookieHandler.removeCookie(KEY);
		
		verify(mockResponse).addCookie(captor.capture());
		
		Cookie atual = captor.getValue();
		assertEquals(0, atual.getMaxAge());
		assertEquals(KEY, atual.getName());
		assertEquals("", atual.getValue());
		assertEquals(CTX_PATH, atual.getPath());
		assertEquals(SRV_NAME, atual.getDomain());
		
		verify(mockResponse, times(1)).addCookie(any());
	}
	
	@Test
	public void whenGetCookieShoulReturnCookieValue() {
		when(mockRequest.getCookies()).thenReturn(new Cookie[] {cookie});
		
		String cookieValue = cookieHandler.getCookieValue(KEY);
		assertThat(cookieValue, equalTo(VALUE));
	}
}
