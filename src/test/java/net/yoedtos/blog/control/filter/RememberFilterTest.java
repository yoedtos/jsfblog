package net.yoedtos.blog.control.filter;

import static net.yoedtos.blog.control.Session.BADGE;
import static net.yoedtos.blog.control.Session.INDEX;
import static net.yoedtos.blog.control.Session.SESSION_GUEST;
import static net.yoedtos.blog.control.Session.SESSION_KEY_USER;
import static net.yoedtos.blog.util.TestConstants.BADGE_ONE;
import static net.yoedtos.blog.util.TestConstants.BADGE_TWO;
import static net.yoedtos.blog.util.TestConstants.INDEX_ONE;
import static net.yoedtos.blog.util.TestConstants.USERNAME_ONE;
import static net.yoedtos.blog.util.TestUtil.createUserDTO;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.model.Role;
import net.yoedtos.blog.model.dto.RememberData;
import net.yoedtos.blog.model.dto.UserDTO;
import net.yoedtos.blog.service.RememberService;

@RunWith(MockitoJUnitRunner.class)
public class RememberFilterTest {

	@Mock
	private HttpServletRequest requestMock;
	@Mock
	private HttpServletResponse responseMock;
	@Mock
	private HttpSession sessionMock;
	@Mock
	private FilterChain filterChainMock;
	@Mock
	private FilterConfig filterConfigMock;
	@Mock
	private RememberService rememberServiceMock;

	@Captor
	private ArgumentCaptor<?> captor;

	@InjectMocks
	private RememberFilter rememberFilter;

	@Test
	public void whenRememberIsValidShouldSetUserInSession() throws Exception {
		Cookie[] cookies = new Cookie[] { new Cookie(INDEX, INDEX_ONE),
											new Cookie(BADGE, BADGE_ONE) };
		RememberData data = new RememberData.Builder()
								.index(INDEX_ONE)
								.badge(BADGE_ONE)
								.build();
		
		when(sessionMock.getAttribute(SESSION_KEY_USER)).thenReturn(null);
		when(sessionMock.getAttribute(SESSION_GUEST)).thenReturn(null);
		when(requestMock.getRequestURI()).thenReturn("/*");
		when(requestMock.getCookies()).thenReturn(cookies);
		when(requestMock.getSession()).thenReturn(sessionMock);

		UserDTO userOne = createUserDTO(new Date());
		when(rememberServiceMock.getIn(any())).thenReturn(userOne);

		rememberFilter.init(filterConfigMock);
		rememberFilter.doFilter(requestMock, responseMock, filterChainMock);
		rememberFilter.destroy();

		verify(rememberServiceMock, times(1)).getIn(data);
		verify(sessionMock, times(3)).setAttribute(anyString(), captor.capture());

		assertEquals(USERNAME_ONE, captor.getAllValues().get(0));
		assertEquals(Role.MEMBER.getValue(), captor.getAllValues().get(1));
		assertEquals(userOne, captor.getAllValues().get(2));
	}

	@Test
	public void whenRememberIsInvalidShouldGoThrough() throws Exception {
		Cookie[] cookies = new Cookie[] { new Cookie(INDEX, INDEX_ONE),
											new Cookie(BADGE, BADGE_TWO) };

		when(sessionMock.getAttribute(SESSION_KEY_USER)).thenReturn(null);
		when(sessionMock.getAttribute(SESSION_GUEST)).thenReturn(null);
		when(requestMock.getRequestURI()).thenReturn("/*");
		when(requestMock.getCookies()).thenReturn(cookies);
		when(requestMock.getSession()).thenReturn(sessionMock);

		when(rememberServiceMock.getIn(any())).thenThrow(new ServiceException());

		rememberFilter.init(filterConfigMock);
		rememberFilter.doFilter(requestMock, responseMock, filterChainMock);
		rememberFilter.destroy();

		verify(rememberServiceMock, times(1)).getIn(any());
		verify(sessionMock, never()).setAttribute(anyString(), captor.capture());
	}

	@Test
	public void whenRememberIsNotSetShouldGoThrough() throws Exception {
		Cookie[] cookies = new Cookie[] {};

		when(sessionMock.getAttribute(SESSION_KEY_USER)).thenReturn(null);
		when(sessionMock.getAttribute(SESSION_GUEST)).thenReturn(null);
		when(requestMock.getRequestURI()).thenReturn("/*");
		when(requestMock.getCookies()).thenReturn(cookies);
		when(requestMock.getSession()).thenReturn(sessionMock);

		rememberFilter.init(filterConfigMock);
		rememberFilter.doFilter(requestMock, responseMock, filterChainMock);
		rememberFilter.destroy();

		verify(rememberServiceMock, never()).getIn(any());
	}
	
	@Test
	public void whenUserLoggedShouldGoThrough() throws Exception {
		when(sessionMock.getAttribute(SESSION_KEY_USER)).thenReturn(USERNAME_ONE);
		when(sessionMock.getAttribute(SESSION_GUEST)).thenReturn("guest");
		when(requestMock.getRequestURI()).thenReturn("/*");
		when(requestMock.getSession()).thenReturn(sessionMock);

		rememberFilter.init(filterConfigMock);
		rememberFilter.doFilter(requestMock, responseMock, filterChainMock);
		rememberFilter.destroy();

		verify(rememberServiceMock, never()).getIn(any());
	}
}
