package net.yoedtos.blog.service;

import static net.yoedtos.blog.util.TestConstants.BADGE_ONE;
import static net.yoedtos.blog.util.TestConstants.BADGE_TWO;
import static net.yoedtos.blog.util.TestConstants.EMAIL_ONE;
import static net.yoedtos.blog.util.TestConstants.FULLNAME_ONE;
import static net.yoedtos.blog.util.TestConstants.HOST_ADDRESS;
import static net.yoedtos.blog.util.TestConstants.INDEX_CLR;
import static net.yoedtos.blog.util.TestConstants.INDEX_ONE;
import static net.yoedtos.blog.util.TestConstants.INDEX_TWO;
import static net.yoedtos.blog.util.TestConstants.REMEMBER_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.USERNAME_ONE;
import static net.yoedtos.blog.util.TestConstants.USER_ONE_ID;
import static net.yoedtos.blog.util.TestUtil.createRememberDataOne;
import static net.yoedtos.blog.util.TestUtil.createRememberOne;
import static net.yoedtos.blog.util.TestUtil.createUserOne;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.model.Role;
import net.yoedtos.blog.model.dto.RememberData;
import net.yoedtos.blog.model.dto.UserDTO;
import net.yoedtos.blog.model.entity.Remember;
import net.yoedtos.blog.model.entity.User;
import net.yoedtos.blog.repository.dao.RememberDao;
import net.yoedtos.blog.repository.dao.UserDao;
import net.yoedtos.blog.util.Constants;

@RunWith(MockitoJUnitRunner.class)
public class RememberServiceTest {
	
	@Mock
	private CryptoHelper cryptoHpMock;
	
	@Mock
	private UserDao userDaoMock;
	
	@Mock
	private RememberDao rememberDaoMock;
	
	@InjectMocks
	private RememberService rememberService;
	
	private RememberData dataOne;
	private Remember remember;
	private Date today;
	private User userOne;
	private String index;
	
	@Before
	public void init() {
		today = new Date();
		index = today.toInstant().getEpochSecond() + "|" + USERNAME_ONE;
		
		userOne = createUserOne(today);
		dataOne = createRememberDataOne();
		remember= createRememberOne(today, null);
	}
	
	@Test
	public void whenSetIsNewShouldReturnRememberOne() throws ServiceException, DaoException {
		when(rememberDaoMock.findByUsername(USERNAME_ONE)).thenReturn(null);
		when(userDaoMock.findByUsername(USERNAME_ONE)).thenReturn(userOne);
		when(cryptoHpMock.encode(anyString())).thenReturn(INDEX_ONE);
		when(cryptoHpMock.random()).thenReturn(BADGE_ONE);
		
		RememberData result = rememberService.set(USERNAME_ONE);
		dataOne.setUsername(null);
		
		assertThat(result, equalTo(dataOne));
		
		verify(cryptoHpMock, times(1)).encode(index);
		verify(rememberDaoMock, times(1)).persist(remember);
	}
	
	@Test
	public void whenSetIsUpdateShouldReturnRememberTwo() throws ServiceException, DaoException {
		RememberData dataTwo = new RememberData.Builder()
				.username(USERNAME_ONE)
				.index(INDEX_TWO)
				.badge(BADGE_TWO)
				.build();
		
		when(rememberDaoMock.findByUsername(USERNAME_ONE)).thenReturn(remember);
		when(cryptoHpMock.encode(anyString())).thenReturn(INDEX_TWO);
		when(cryptoHpMock.random()).thenReturn(BADGE_TWO);
		
		RememberData result = rememberService.set(USERNAME_ONE);
		dataTwo.setUsername(null);
		
		assertThat(result, equalTo(dataTwo));
		verify(cryptoHpMock, times(1)).encode(index);
		verify(rememberDaoMock, times(1)).merge(remember);
	}
	
	@Test
	public void whenUnsetShouldRemoveRemember() throws ServiceException, DaoException {
		remember.setId(REMEMBER_ONE_ID);
		when(rememberDaoMock.findByUsername(USERNAME_ONE)).thenReturn(remember);
		rememberService.unset(USERNAME_ONE);
		
		verify(rememberDaoMock, times(1)).remove(REMEMBER_ONE_ID);
	}
	
	@Test
	public void whenGetInShouldReturnUserDTO() throws ServiceException, DaoException {
		UserDTO expected = new UserDTO.Builder(USERNAME_ONE)
				.id(USER_ONE_ID)
				.fullname(FULLNAME_ONE)
				.email(EMAIL_ONE)
				.password(Constants.PASS_MASK)
				.hostAddress(HOST_ADDRESS)
				.role(Role.MEMBER)
				.active(true)
				.createdAt(today)
				.build();
		
		when(cryptoHpMock.decode(anyString())).thenReturn(INDEX_CLR);
		when(rememberDaoMock.findByUsername(USERNAME_ONE)).thenReturn(remember);
		
		UserDTO userDto = rememberService.getIn(dataOne);
		
		assertThat(userDto, equalTo(expected));
		verify(cryptoHpMock, times(1)).decode(INDEX_ONE);
	}
	
	@Test(expected = ServiceException.class)
	public void whenGetInFailedShouldThrowsException() throws ServiceException, DaoException {
		when(rememberDaoMock.findByUsername(USERNAME_ONE)).thenThrow(new DaoException());
		UserDTO userDto = rememberService.getIn(dataOne);
		
		assertNull(userDto);
		verify(cryptoHpMock, times(1)).decode(INDEX_ONE);
	}
	
	@Test(expected = ServiceException.class)
	public void whenGetInWithWrongBadgeInShouldThrowsException() throws ServiceException, DaoException {
		dataOne.setBadge(BADGE_TWO);
		
		when(cryptoHpMock.decode(anyString())).thenReturn(INDEX_CLR);
		when(rememberDaoMock.findByUsername(USERNAME_ONE)).thenReturn(remember);
		
		UserDTO userDto = rememberService.getIn(dataOne);
		
		assertNull(userDto);
		verify(cryptoHpMock, times(1)).decode(INDEX_ONE);
	}
}
