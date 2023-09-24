package net.yoedtos.blog.repository.dao;

import static net.yoedtos.blog.util.TestConstants.BADGE_NEW;
import static net.yoedtos.blog.util.TestConstants.BADGE_TWO;
import static net.yoedtos.blog.util.TestConstants.CREATE_ONE;
import static net.yoedtos.blog.util.TestConstants.CREATE_TWO;
import static net.yoedtos.blog.util.TestConstants.REMEMBER_NEW_ID;
import static net.yoedtos.blog.util.TestConstants.REMEMBER_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.REMEMBER_TWO_ID;
import static net.yoedtos.blog.util.TestConstants.USERNAME_ONE;
import static net.yoedtos.blog.util.TestConstants.USER_UNKNOWN;
import static net.yoedtos.blog.util.TestUtil.createDate;
import static net.yoedtos.blog.util.TestUtil.createRememberOne;
import static net.yoedtos.blog.util.TestUtil.createUserOne;
import static net.yoedtos.blog.util.TestUtil.createUserTwo;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.dbunit.DatabaseUnitException;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.factory.RepositoryFactory;
import net.yoedtos.blog.model.entity.Remember;

public class RememberDaoTest extends AbstractDaoTest {
	
	private RememberDao rememberDao;
	private Remember rememberOne;
	private Date createOne;
	
	public RememberDaoTest() throws DatabaseUnitException, SQLException {
		super();
		createOne = createDate(CREATE_ONE);
		rememberDao = RepositoryFactory.create(RememberDao.class);
		rememberOne = createRememberOne(createOne, REMEMBER_ONE_ID);
	}

	@Before
	public void init() throws DatabaseUnitException, SQLException {
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
	}
	
	@Test
	public void whenFindAllShouldReturnTwoRemembers() throws DaoException {
		Date createTwo = createDate(CREATE_TWO);
		Remember rememberTwo = new Remember.Builder()
				.id(REMEMBER_TWO_ID)
				.createAt(createTwo)
				.value(BADGE_TWO)
				.user(createUserTwo(createTwo))
				.build();
		
		List<Remember> remembers = rememberDao.findAll();
		assertThat(remembers.size(), equalTo(2));
		assertThat(remembers, hasItems(new Remember[] {rememberOne, rememberTwo}));
	}
	
	@Test
	public void whenFindByUserNameShouldHaveOne() throws DaoException {
		Remember remember = rememberDao.findByUsername(USERNAME_ONE);
		assertThat(remember, equalTo(rememberOne));
	}
	
	@Test
	public void whenFindByUserNameIsUnknowShouldReturnNull() throws DaoException {
		Remember remember = rememberDao.findByUsername(USER_UNKNOWN);
		assertNull(remember);
	}
	
	@Test
	public void whenRemoveRememberShouldHaveOne() throws DaoException {
		rememberDao.remove(REMEMBER_TWO_ID);
		List<Remember> remembers = rememberDao.findAll();
		assertThat(remembers.size(), equalTo(1));
		assertThat(remembers, hasItem(rememberOne));
	}
	
	@Test(expected = DaoException.class)
	public void whenAddTwiceShouldThrowException() throws DaoException {
		Remember remember = createRememberOne(createOne, REMEMBER_ONE_ID);
		rememberDao.persist(remember);
	}
	
	@Test
	public void whenAddRememberShouldHaveOne() throws DaoException {
		Remember rememberNew = new Remember.Builder()
				.createAt(createOne)
				.value(BADGE_NEW)
				.user(createUserOne(createOne))
				.build();
		
		rememberDao.persist(rememberNew);
		
		Remember remember = rememberDao.findById(REMEMBER_NEW_ID);
		assertThat(remember.getId(), equalTo(REMEMBER_NEW_ID));
		assertThat(remember.getCreateAt(), equalTo(Timestamp.valueOf(CREATE_ONE)));
		assertThat(remember.getValue(), equalTo(BADGE_NEW));
	}
	
	@Test
	public void whenUpdateShouldHaveOneUpdated() throws DaoException {
		Remember remember = rememberDao.findById(REMEMBER_ONE_ID);
		
		remember.setCreateAt(createDate(CREATE_TWO));
		remember.setValue(BADGE_NEW);
		
		rememberDao.merge(remember);
		
		Remember rememberUpdated = rememberDao.findById(REMEMBER_ONE_ID);
		assertThat(rememberUpdated.getCreateAt(), equalTo(Timestamp.valueOf(CREATE_TWO)));
		assertThat(rememberUpdated.getValue(), equalTo(BADGE_NEW));
	}
}
