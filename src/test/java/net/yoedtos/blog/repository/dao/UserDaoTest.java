package net.yoedtos.blog.repository.dao;

import static net.yoedtos.blog.util.TestConstants.CREATE_ONE;
import static net.yoedtos.blog.util.TestConstants.CREATE_TWO;
import static net.yoedtos.blog.util.TestConstants.EMAIL_NEW;
import static net.yoedtos.blog.util.TestConstants.EMAIL_UPDATE;
import static net.yoedtos.blog.util.TestConstants.FULLNAME_NEW;
import static net.yoedtos.blog.util.TestConstants.FULL_UPDATE;
import static net.yoedtos.blog.util.TestConstants.HOST_ADDRESS;
import static net.yoedtos.blog.util.TestConstants.PASS_UPDATE;
import static net.yoedtos.blog.util.TestConstants.USERNAME_NEW;
import static net.yoedtos.blog.util.TestConstants.USERNAME_ONE;
import static net.yoedtos.blog.util.TestConstants.USER_NEW_ID;
import static net.yoedtos.blog.util.TestConstants.USER_ONE_ID;
import static net.yoedtos.blog.util.TestUtil.createDate;
import static net.yoedtos.blog.util.TestUtil.createUserOne;
import static net.yoedtos.blog.util.TestUtil.createUserTwo;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.factory.RepositoryFactory;
import net.yoedtos.blog.model.Role;
import net.yoedtos.blog.model.entity.User;

public class UserDaoTest extends AbstractDaoTest {
	
	private UserDao userDao;
	private User userOne;
	private Date createOne;
	
	public UserDaoTest() throws DatabaseUnitException, SQLException {
		super();
		userDao = RepositoryFactory.create(UserDao.class);
		createOne = createDate(CREATE_ONE);
		userOne = createUserOne((createOne));
	}

	@Before
	public void init() throws DatabaseUnitException, SQLException {
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
	}
	
	@Test
	public void whenFindAllShouldReturnTwoUsers() throws DaoException {
		User userTwo = createUserTwo(createDate(CREATE_TWO));
		
		List<User> users = userDao.findAll();
		assertThat(users.size(), equalTo(2));
		assertThat(users, hasItems(new User[] {userOne, userTwo}));
	}
	
	@Test(expected = DaoException.class)
	public void whenAddUserTwiceShouldThrowException() throws DaoException {
		userDao.persist(userOne);
	}
	 
	@Test
	public void whenAddUserShouldHaveOne() throws DaoException {
		User user = new User.Builder(USERNAME_NEW)
				.fullname(FULLNAME_NEW)
				.email(EMAIL_NEW)
				.hostAddress(HOST_ADDRESS)
				.role(Role.MEMBER)
				.active(true)
				.createAt(createOne)
				.build();
				
		userDao.persist(user);
		
		User userDb = userDao.findById(USER_NEW_ID);
		assertThat(userDb.getUserId(), equalTo(USER_NEW_ID));
		assertThat(userDb.getUsername(), equalTo(USERNAME_NEW));
		assertThat(userDb.getFullname(), equalTo(FULLNAME_NEW));
		assertThat(userDb.getEmail(), equalTo(EMAIL_NEW));
		assertThat(userDb.getHostAddress(), equalTo(HOST_ADDRESS));
		assertThat(userDb.getRole(), equalTo(Role.MEMBER));
		assertThat(userDb.getActive(), equalTo(true));
		assertThat(userDb.getCreateAt(), equalTo(Timestamp.valueOf(CREATE_ONE)));
	}
	
	@Test
	public void whenUpdateUserShouldReturnUser() throws DaoException {
		User user = new User.Builder(USERNAME_ONE)
				.userId(USER_ONE_ID)
				.fullname(FULL_UPDATE)
				.email(EMAIL_UPDATE)
				.hostAddress(HOST_ADDRESS)
				.role(Role.MEMBER)
				.active(true)
				.createAt(createDate(CREATE_TWO))
				.build();
		
		user.setPassword(PASS_UPDATE);
		
		User userDb = userDao.merge(user);
		assertThat(userDb, equalTo(user));
	}
	
	@Test(expected = NoResultException.class)
	public void whenFindByUsernameHasNoResultShouldThrowException() throws DaoException {
		userDao.findByUsername(USERNAME_NEW);
	}
}
