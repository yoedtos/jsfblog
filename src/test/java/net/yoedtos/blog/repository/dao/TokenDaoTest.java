package net.yoedtos.blog.repository.dao;

import static net.yoedtos.blog.util.TestConstants.*;
import static net.yoedtos.blog.util.TestConstants.CREATE_TWO;
import static net.yoedtos.blog.util.TestConstants.TOKEN_NEW;
import static net.yoedtos.blog.util.TestConstants.TOKEN_NEW_ID;
import static net.yoedtos.blog.util.TestConstants.TOKEN_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.TOKEN_TWO;
import static net.yoedtos.blog.util.TestConstants.TOKEN_TWO_ID;
import static net.yoedtos.blog.util.TestConstants.USERNAME_ONE;
import static net.yoedtos.blog.util.TestUtil.createDate;
import static net.yoedtos.blog.util.TestUtil.createTokenOne;
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
import net.yoedtos.blog.model.entity.Token;

public class TokenDaoTest extends AbstractDaoTest {
	
	private TokenDao tokenDao;
	private Token tokenOne;
	private Date createOne;
	
	public TokenDaoTest() throws DatabaseUnitException, SQLException {
		super();
		createOne = createDate(CREATE_ONE);
		tokenDao = RepositoryFactory.create(TokenDao.class);
		tokenOne = createTokenOne(createOne, TOKEN_ONE_ID);
	}

	@Before
	public void init() throws DatabaseUnitException, SQLException {
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
	}
	
	@Test
	public void whenFindAllShouldReturnTwoTokens() throws DaoException {
		Date createTwo = createDate(CREATE_TWO);
		Token tokenTwo = new Token.Builder()
				.id(TOKEN_TWO_ID)
				.createAt(createTwo)
				.value(TOKEN_TWO)
				.creator(createUserTwo(createTwo))
				.build();
		
		List<Token> tokens = tokenDao.findAll();
		assertThat(tokens.size(), equalTo(2));
		assertThat(tokens, hasItems(new Token[] {tokenOne, tokenTwo}));
	}
	
	@Test
	public void whenFindByUserNameShouldHaveOne() throws DaoException {
		Token token = tokenDao.findByUsername(USERNAME_ONE);
		assertThat(token, equalTo(tokenOne));
	}
	
	@Test
	public void whenFindByUserNameIsUnknowShouldReturnNull() throws DaoException {
		Token token = tokenDao.findByUsername(USER_UNKNOWN);
		assertNull(token);
	}
	
	@Test
	public void whenUpdateTokenShouldHaveOne() throws DaoException {
		tokenOne.getCreator().setPassword(ENC_UPDATE);
		
		Token token = tokenDao.findByUsername(USERNAME_ONE);
		token.getCreator().setPassword(ENC_UPDATE);
		Token tokenUpdated = tokenDao.merge(token);
		assertThat(tokenUpdated, equalTo(tokenOne));
	}
	
	@Test
	public void whenRemoveTokenShouldHaveOne() throws DaoException {
		tokenDao.remove(TOKEN_TWO_ID);
		List<Token> tokens = tokenDao.findAll();
		assertThat(tokens.size(), equalTo(1));
		assertThat(tokens, hasItem(tokenOne));
	}
	
	@Test(expected = DaoException.class)
	public void whenAddTwiceShouldThrowException() throws DaoException {
		Token token = createTokenOne(createOne, TOKEN_ONE_ID);
		tokenDao.persist(token);
	}
	
	@Test
	public void whenAddTokenShouldHaveOne() throws DaoException {
		Token tokenNew = new Token.Builder()
				.createAt(createOne)
				.value(TOKEN_NEW)
				.creator(createUserOne(createOne))
				.build();
		
		tokenDao.persist(tokenNew);
		
		Token tokenDb = tokenDao.findById(TOKEN_NEW_ID);
		assertThat(tokenDb.getId(), equalTo(TOKEN_NEW_ID));
		assertThat(tokenDb.getValue(), equalTo(TOKEN_NEW));
	}
	
	@Test
	public void whenUpdateShouldHaveOneUpdated() throws DaoException {
		Token tokenDb = tokenDao.findById(TOKEN_ONE_ID);
		
		tokenDb.setCreateAt(createDate(CREATE_TWO));
		tokenDb.setValue(TOKEN_NEW);
		
		tokenDao.merge(tokenDb);
		
		Token tokenUpdated = tokenDao.findById(TOKEN_ONE_ID);
		assertThat(tokenUpdated.getCreateAt(), equalTo(Timestamp.valueOf(CREATE_TWO)));
		assertThat(tokenUpdated.getValue(), equalTo(TOKEN_NEW));
	}
}
