package net.yoedtos.blog.service;

import static net.yoedtos.blog.util.TestConstants.EMAIL;
import static net.yoedtos.blog.util.TestConstants.ENCODED;
import static net.yoedtos.blog.util.TestConstants.FULLNAME;
import static net.yoedtos.blog.util.TestConstants.HOST_ADDRESS;
import static net.yoedtos.blog.util.TestConstants.PASSWORD;
import static net.yoedtos.blog.util.TestConstants.USERNAME;
import static net.yoedtos.blog.util.TestConstants.USER_ID;
import static net.yoedtos.blog.util.TestUtil.createUserDTO;
import static net.yoedtos.blog.util.TestUtil.createUserOne;
import static org.hamcrest.CoreMatchers.equalTo;
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
	
	private User userOne;
	private UserDTO userDTO;
	private Date today;
	
	@Before
	public void init() {
		today = new Date();
		
		userDTO = createUserDTO(today); 
		userOne = createUserOne(today);
	}

	@Test
	public void whenCreateShouldCreateOnce() throws ServiceException, DaoException {
		when(encryptorMock.encrypt(PASSWORD)).thenReturn(ENCODED);
		userService.create(userDTO);
		verify(userDaoMock).persist((User) captor.capture());
		User user = (User) captor.getValue();
		
		assertThat(user.getUsername(), equalTo(USERNAME));
		assertThat(user.getFullname(), equalTo(FULLNAME));
		assertThat(user.getEmail(), equalTo(EMAIL));
		assertThat(user.getPassword(), equalTo(ENCODED));
		assertThat(user.getHostAddress(), equalTo(HOST_ADDRESS));
		assertThat(user.getRole(), equalTo(Role.MEMBER));
		assertThat(user.getActive(), equalTo(true));
		assertThat(user.getCreateAt(), equalTo(today));
		
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
		
		assertThat(userDTO.getId(), equalTo(userOne.getUserId()));
		assertThat(userDTO.getUsername(), equalTo(userOne.getUsername()));
		assertThat(userDTO.getFullname(), equalTo(userOne.getFullname()));
		assertThat(userDTO.getEmail(), equalTo(userOne.getEmail()));
		assertThat(userDTO.getPassword(), equalTo(Constants.PASS_MASK));
		assertThat(userDTO.getHostAddress(), equalTo(userOne.getHostAddress()));
		assertThat(userDTO.getRole(), equalTo(userOne.getRole()));
		assertThat(userDTO.getActive(), equalTo(userOne.getActive()));
		assertThat(userDTO.getCreatedAt(), equalTo(userOne.getCreateAt()));
		
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
		
		assertThat(updatedUserDTO.getId(), equalTo(USER_ID));
		assertThat(updatedUserDTO.getUsername(), equalTo(USERNAME));
		assertThat(updatedUserDTO.getFullname(), equalTo(FULL_UPDATE));
		assertThat(updatedUserDTO.getEmail(), equalTo(EMAIL_UPDATE));
		assertThat(updatedUserDTO.getPassword(), equalTo(Constants.PASS_MASK));
		assertThat(updatedUserDTO.getHostAddress(), equalTo(HOST_ADDRESS));
		assertThat(updatedUserDTO.getRole(), equalTo(Role.MEMBER));
		assertThat(updatedUserDTO.getActive(), equalTo(true));
		
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
			assertThat(userDTO.getId(), equalTo(users.get(index).getUserId()));
			assertThat(userDTO.getUsername(), equalTo(users.get(index).getUsername()));
			assertThat(userDTO.getFullname(), equalTo(users.get(index).getFullname()));
			assertThat(userDTO.getEmail(), equalTo(users.get(index).getEmail()));
			assertThat(userDTO.getPassword(), equalTo(Constants.PASS_MASK));
			assertThat(userDTO.getHostAddress(), equalTo(users.get(index).getHostAddress()));
			assertThat(userDTO.getRole(), equalTo(users.get(index).getRole()));
			assertThat(userDTO.getActive(), equalTo(users.get(index).getActive()));
			index++;
		}
	}
}
