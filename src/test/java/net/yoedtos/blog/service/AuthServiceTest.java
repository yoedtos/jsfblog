package net.yoedtos.blog.service;

import static net.yoedtos.blog.util.TestConstants.CREATE_ONE;
import static net.yoedtos.blog.util.TestConstants.EMAIL_ONE;
import static net.yoedtos.blog.util.TestConstants.ENCODED;
import static net.yoedtos.blog.util.TestConstants.FULLNAME_ONE;
import static net.yoedtos.blog.util.TestConstants.HOST_ADDRESS;
import static net.yoedtos.blog.util.TestConstants.PASSWORD;
import static net.yoedtos.blog.util.TestConstants.PASS_WRONG;
import static net.yoedtos.blog.util.TestConstants.USERNAME_ONE;
import static net.yoedtos.blog.util.TestConstants.USER_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.USER_UNKNOWN;
import static net.yoedtos.blog.util.TestUtil.createDate;
import static net.yoedtos.blog.util.TestUtil.createUserOne;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.yoedtos.blog.exception.AuthException;
import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.model.Role;
import net.yoedtos.blog.model.dto.Login;
import net.yoedtos.blog.model.dto.UserDTO;
import net.yoedtos.blog.model.entity.User;
import net.yoedtos.blog.repository.dao.UserDao;
import net.yoedtos.blog.util.Constants;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {
	
	private final static String USER = "User";
	private final static String ROLE = "Role";
	private final static String EMAIL = "Email";
	private final static String ACTIVE = "Active";
	
	@Mock
	private UserDao userDaoMock;
	
	@Mock
	private CryptoHelper cryptoHelper;

	@InjectMocks
	private AuthService authService;
	
	private User userOne;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Before
	public void init() {
		userOne = createUserOne(createDate(CREATE_ONE));
		userOne.setPassword(ENCODED);
	}
	
	@Test
	public void whenLoginValidShouldReturnUser() throws DaoException, ServiceException, AuthException {
		when(userDaoMock.findByUsername(USERNAME_ONE)).thenReturn(userOne);
		when(cryptoHelper.isValidPassword(PASSWORD, userOne.getPassword())).thenReturn(true);
		
		UserDTO user = authService.login(new Login(USERNAME_ONE, PASSWORD));
		assertEquals(USER, USERNAME_ONE, user.getUsername());
		assertEquals(ROLE, Role.MEMBER, user.getRole());
		assertEquals(EMAIL, EMAIL_ONE, user.getEmail());
		assertEquals(ACTIVE, true, user.getActive());
		
		verify(userDaoMock, times(1)).findByUsername(USERNAME_ONE);
		verify(cryptoHelper, times(1)).isValidPassword(PASSWORD, ENCODED);
	}
	
	@Test(expected = ServiceException.class)
	public void whenLoginUserIsInvalidShouldThrowException() throws DaoException, ServiceException, AuthException {	
		doThrow(new DaoException()).when(userDaoMock).findByUsername(USER_UNKNOWN);
		
		authService.login(new Login(USER_UNKNOWN, anyString()));
		
		verify(userDaoMock, times(1)).findByUsername(USERNAME_ONE);
		verify(cryptoHelper, never()).isValidPassword(USER_UNKNOWN, anyString());
	}
	
	@Test
	public void whenLoginPasswordIsInvalidShouldThrowException() throws DaoException, ServiceException, AuthException {
		expectedException.expect(AuthException.class);
		expectedException.expectMessage(Constants.USER_PASS_NG);
		
		when(userDaoMock.findByUsername(USERNAME_ONE)).thenReturn(userOne);
		when(cryptoHelper.isValidPassword(PASS_WRONG, ENCODED)).thenReturn(false);
		
		authService.login(new Login(USERNAME_ONE, PASS_WRONG));
		
		verify(userDaoMock, times(1)).findByUsername(USERNAME_ONE);
		verify(cryptoHelper, times(1)).isValidPassword(PASS_WRONG, ENCODED);
	}
	
	@Test
	public void whenLoginValidAndUserDisactiveShouldThrowException() throws DaoException, ServiceException, AuthException {
		expectedException.expect(AuthException.class);
		expectedException.expectMessage(Constants.USER_INACTIVE);
		
		userOne = new User.Builder(USERNAME_ONE)
				.userId(USER_ONE_ID)
				.fullname(FULLNAME_ONE)
				.email(EMAIL_ONE)
				.hostAddress(HOST_ADDRESS)
				.role(Role.MEMBER)
				.active(false)
				.createAt(createDate(CREATE_ONE))
				.build();
				
		when(userDaoMock.findByUsername(USERNAME_ONE)).thenReturn(userOne);
		when(cryptoHelper.isValidPassword(PASSWORD, ENCODED)).thenReturn(true);
		
		authService.login(new Login(USERNAME_ONE, PASSWORD));
		
		verify(userDaoMock, times(1)).findByUsername(USERNAME_ONE);
		verify(cryptoHelper, times(1)).isValidPassword(PASSWORD, ENCODED);
	}
}
