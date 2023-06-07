package net.yoedtos.blog.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import net.yoedtos.blog.model.Role;
import net.yoedtos.blog.model.dto.UserDTO;
import net.yoedtos.blog.model.entity.User;
import net.yoedtos.blog.repository.dao.UserDao;
import net.yoedtos.blog.util.Constants;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserDao userDaoMock;

	@Mock
	private CryptoHelper encryptorMock;
	
	@InjectMocks
	private UserService userService;

	@Captor
	private ArgumentCaptor<?> captor;
	
	final static private long USER_ID = 1;
	final static private String USERNAME = "userone";
	final static private String FULLNAME = "User One";
	final static private String EMAIL = "userone@domain.com";
	final static private String PASSWORD = "password";
	final static private String ENCODED = "drowssap";
	final static private String HOST_ADDRESS= "127.0.0.1";
	
	private User userOne;
	private UserDTO userDTO;
	private Date today;
	
	@Before
	public void init() {
		today = new Date();
		
		userDTO = new UserDTO.Builder(USERNAME)
				.fullname(FULLNAME)
				.email(EMAIL)
				.password(PASSWORD)
				.hostAddress(HOST_ADDRESS)
				.role(Role.MEMBER)
				.active(true)
				.createdAt(today)
				.build();
		
		userOne = new User.Builder(USERNAME)
				.userId(USER_ID)
				.fullname(FULLNAME)
				.email(EMAIL)
				.hostAddress(HOST_ADDRESS)
				.role(Role.MEMBER)
				.active(true)
				.createAt(today)
				.build();
	}

	@Test
	public void whenCreateShouldCreateOnce() throws ServiceException, DaoException {
		when(encryptorMock.encrypt(PASSWORD)).thenReturn(ENCODED);
		userService.create(userDTO);
		verify(userDaoMock).persist((User) captor.capture());
		User user = (User) captor.getValue();
		
		assertThat(user.getUsername(), is(USERNAME));
		assertThat(user.getFullname(), is(FULLNAME));
		assertThat(user.getEmail(), is(EMAIL));
		assertThat(user.getPassword(), is(ENCODED));
		assertThat(user.getHostAddress(), is(HOST_ADDRESS));
		assertThat(user.getRole(), is(Role.MEMBER));
		assertThat(user.getActive(), is(true));
		assertThat(user.getCreateAt(), is(today));
		
		verify(userDaoMock, times(1)).persist(user);
	}
	
	@Test
	public void whenRemoveShouldRemoveOnce() throws DaoException, ServiceException {
		userService.remove(USER_ID);
		verify(userDaoMock).remove((Long) captor.capture());
		assertEquals(USER_ID, ((Long) captor.getValue()).intValue());
		verify(userDaoMock, times(1)).remove(USER_ID);
	}
	
	@Test(expected = ServiceException.class)
	public void whenCreateShouldThrowsException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(userDaoMock).persist(userOne);
		userService.create(userDTO);
		verify(userDaoMock, times(1)).persist(userOne);
	}
	
	@Test(expected = ServiceException.class)
	public void whenRemoveShouldThrowsException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(userDaoMock).remove(USER_ID);
		userService.remove(USER_ID);
		verify(userDaoMock, times(1)).remove(USER_ID);
	}
	
	@Test
	public void whenGetShouldReturnUserDTO() throws DaoException, ServiceException {
		when(userDaoMock.findById(USER_ID)).thenReturn(userOne);
		UserDTO userDTO = userService.get(USER_ID);
		
		assertThat(userDTO.getId(), is(userOne.getUserId()));
		assertThat(userDTO.getUsername(), is(userOne.getUsername()));
		assertThat(userDTO.getFullname(), is(userOne.getFullname()));
		assertThat(userDTO.getEmail(), is(userOne.getEmail()));
		assertThat(userDTO.getPassword(), is(Constants.PASS_MASK));
		assertThat(userDTO.getHostAddress(), is(userOne.getHostAddress()));
		assertThat(userDTO.getRole(), is(userOne.getRole()));
		assertThat(userDTO.getActive(), is(userOne.getActive()));
		assertThat(userDTO.getCreatedAt(), is(userOne.getCreateAt()));
		
		verify(userDaoMock, times(1)).findById(USER_ID);
	}
	
	@Test
	public void whenUpdateShouldReturnUserUpdated() throws DaoException, ServiceException {
		final String FULL_UPDATE = "User One Update";
		final String EMAIL_UPDATE = "user-one@domain.com";
		final String PASS_UPDATE = "password1";
		
		userDTO = new UserDTO.Builder(userDTO.getUsername())
				.id(userDTO.getId())
				.fullname(FULL_UPDATE)
				.email(EMAIL_UPDATE)
				.password(PASS_UPDATE)
				.hostAddress(userDTO.getHostAddress())
				.role(userDTO.getRole())
				.active(userDTO.getActive())
				.build();
		
		userOne.setFullname(FULL_UPDATE);
		userOne.setEmail(EMAIL_UPDATE);
		
		when(userDaoMock.findById(userDTO.getId())).thenReturn(userOne);
		when(userDaoMock.merge(userOne)).thenReturn(userOne);
		
		UserDTO updatedUserDTO = userService.update(userDTO);
		
		assertThat(updatedUserDTO.getId(), is(USER_ID));
		assertThat(updatedUserDTO.getUsername(), is(USERNAME));
		assertThat(updatedUserDTO.getFullname(), is(FULL_UPDATE));
		assertThat(updatedUserDTO.getEmail(), is(EMAIL_UPDATE));
		assertThat(updatedUserDTO.getPassword(), is(Constants.PASS_MASK));
		assertThat(updatedUserDTO.getHostAddress(), is(HOST_ADDRESS));
		assertThat(updatedUserDTO.getRole(), is(Role.MEMBER));
		assertThat(updatedUserDTO.getActive(), is(true));
		
		verify(encryptorMock, times(1)).encrypt(PASS_UPDATE);
		verify(userDaoMock, times(1)).merge(userOne);
	}
	
	@Test(expected = ServiceException.class)
	public void whenGetAllShouldThrowsException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(userDaoMock).findAll();
		userService.getAll();
		verify(userDaoMock, times(1)).findAll();
	}
	
	@Test
	public void whenGetAllShouldReturnTwoUsers() throws ServiceException, DaoException {
		User userTwo = new User.Builder("usertwo")
				.fullname("User Two")
				.email("usertwo@domain.com")
				.hostAddress("127.0.0.1")
				.role(Role.MEMBER)
				.active(true)
				.build();
		
		List<User> users = Arrays.asList(userOne, userTwo);
		when(userDaoMock.findAll()).thenReturn(users);
		List<UserDTO> userDTOs = userService.getAll();
		assertEquals(2, userDTOs.size());
		
		int index = 0;
		for (UserDTO userDTO : userDTOs) {
			assertThat(userDTO.getId(), is(users.get(index).getUserId()));
			assertThat(userDTO.getUsername(), is(users.get(index).getUsername()));
			assertThat(userDTO.getFullname(), is(users.get(index).getFullname()));
			assertThat(userDTO.getEmail(), is(users.get(index).getEmail()));
			assertThat(userDTO.getPassword(), is(Constants.PASS_MASK));
			assertThat(userDTO.getHostAddress(), is(users.get(index).getHostAddress()));
			assertThat(userDTO.getRole(), is(users.get(index).getRole()));
			assertThat(userDTO.getActive(), is(users.get(index).getActive()));
			index++;
		}
	}
}
