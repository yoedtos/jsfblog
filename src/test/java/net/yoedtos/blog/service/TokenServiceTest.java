package net.yoedtos.blog.service;

import static net.yoedtos.blog.util.TestConstants.ENC_UPDATE;
import static net.yoedtos.blog.util.TestConstants.TOKEN_NEW;
import static net.yoedtos.blog.util.TestConstants.TOKEN_ONE;
import static net.yoedtos.blog.util.TestConstants.TOKEN_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.TOKEN_UNKNOWN;
import static net.yoedtos.blog.util.TestConstants.TOKEN_INVALID;
import static net.yoedtos.blog.util.TestConstants.USERNAME_ONE;
import static net.yoedtos.blog.util.TestConstants.USER_UNKNOWN;
import static net.yoedtos.blog.util.TestUtil.createResetOneExpire;
import static net.yoedtos.blog.util.TestUtil.createResetOneMake;
import static net.yoedtos.blog.util.TestUtil.createTokenOne;
import static net.yoedtos.blog.util.TestUtil.createUserOne;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.model.dto.Reset;
import net.yoedtos.blog.model.entity.Token;
import net.yoedtos.blog.model.entity.User;
import net.yoedtos.blog.repository.dao.TokenDao;
import net.yoedtos.blog.repository.dao.UserDao;

@RunWith(MockitoJUnitRunner.class)
public class TokenServiceTest {
	
	@Mock
	private UserDao userDaoMock;
	
	@Mock
	private TokenHelper tokenHelperMock;
	
	@Mock
	private TokenDao tokenDaoMock;
	
	@InjectMocks
	private TokenService tokenService;
	
	@Captor
	private ArgumentCaptor<?> captor;
	private Date today;
	private User userOne;
	
	@Before
	public void init() {
		today = new Date();
		userOne = createUserOne(today);
	}
	
	@Test
	public void whenUserMakeTokenShouldCreate() throws DaoException, ServiceException {
		Token token = createTokenOne(today, null);
		
		when(userDaoMock.findByUsername(USERNAME_ONE)).thenReturn(userOne);
		when(tokenDaoMock.findByUsername(USERNAME_ONE)).thenReturn(null);
		when(tokenHelperMock.generate()).thenReturn(TOKEN_ONE);
		
		tokenService.make(createResetOneMake());
		
		verify(tokenDaoMock).persist((Token) captor.capture());
		Token captured = (Token) captor.getValue();
		assertThat(captured, equalTo(token));
		
		verify(tokenDaoMock, times(1)).persist(token);;
	}

	@Test
	public void whenUserMakeTokenTwiceShouldUpdate() throws DaoException, ServiceException {
		Token token = createTokenOne(today, TOKEN_ONE_ID);
		
		Token tokenNew = new Token.Builder()
				.id(TOKEN_ONE_ID)
				.createAt(today)
				.value(TOKEN_NEW)
				.creator(createUserOne(today))
				.build();
		
		when(userDaoMock.findByUsername(USERNAME_ONE)).thenReturn(userOne);
		when(tokenDaoMock.findByUsername(USERNAME_ONE)).thenReturn(token);
		when(tokenHelperMock.generate()).thenReturn(TOKEN_NEW);
		
		tokenService.make(createResetOneMake());
		
		verify(tokenDaoMock).merge((Token) captor.capture());
		Token captured = (Token) captor.getValue();
		assertThat(captured, equalTo(tokenNew));
		verify(tokenDaoMock, times(1)).merge(tokenNew);;
	}
	
	@Test(expected = ServiceException.class)
	public void whenWrongUserMakeTokenShouldThrowException() throws DaoException, ServiceException {
		Token token = createTokenOne(today, null);
		doThrow(new DaoException()).when(userDaoMock).findByUsername(USER_UNKNOWN);
		
		Reset invalid = new Reset.Builder().username(USER_UNKNOWN).build();
		tokenService.make(invalid);
		
		verify(tokenDaoMock, never()).persist(token);;
	}
	
	@Test
	public void whenExpireSuccessShouldRemoveAndUpdateUser() throws DaoException, ServiceException {
		Token token = createTokenOne(today, TOKEN_ONE_ID);
		
		when(tokenDaoMock.findByUsername(USERNAME_ONE)).thenReturn(token);
		tokenService.expire(createResetOneExpire());
		
		verify(tokenDaoMock).merge((Token) captor.capture());
		Token tokenCaptured = (Token) captor.getValue();
		assertEquals(ENC_UPDATE, tokenCaptured.getCreator().getPassword());
		
		verify(tokenDaoMock).remove((Long) captor.capture());
		assertEquals(TOKEN_ONE_ID, captor.getValue());
	}
	
	@Test(expected = ServiceException.class)
	public void whenExpireWithWrongTokenShouldThrowException() throws DaoException, ServiceException {
		Token token = createTokenOne(today, TOKEN_ONE_ID);
		Reset unknown = createResetOneExpire();
		unknown.setToken(TOKEN_UNKNOWN);
		
		when(tokenDaoMock.findByUsername(USERNAME_ONE)).thenReturn(token);
		tokenService.expire(unknown);
		
		verify(tokenDaoMock, never()).remove(TOKEN_ONE_ID);
	}
	
	@Test(expected = ServiceException.class)
	public void whenExpireWithInvalidTokenShouldThrowException() throws DaoException, ServiceException {
		Reset invalid = createResetOneExpire();
		invalid.setToken(TOKEN_INVALID);
		
		doThrow(new IllegalArgumentException()).when(tokenHelperMock).validate(TOKEN_INVALID);
		tokenService.expire(invalid);
		
		verify(tokenDaoMock, never()).remove(TOKEN_ONE_ID);
	}
}
