package net.yoedtos.blog.repository.dao;

import static net.yoedtos.blog.util.TestConstants.CONTENT_TYPE_ONE;
import static net.yoedtos.blog.util.TestConstants.CONTENT_TYPE_TWO;
import static net.yoedtos.blog.util.TestConstants.CREATE_ONE;
import static net.yoedtos.blog.util.TestConstants.CREATE_TWO;
import static net.yoedtos.blog.util.TestConstants.MEDIA_NEW_DESC;
import static net.yoedtos.blog.util.TestConstants.MEDIA_NEW_ID;
import static net.yoedtos.blog.util.TestConstants.MEDIA_NEW_NAME;
import static net.yoedtos.blog.util.TestConstants.MEDIA_NEW_URN;
import static net.yoedtos.blog.util.TestConstants.MEDIA_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.MEDIA_OUT_ID;
import static net.yoedtos.blog.util.TestConstants.MEDIA_TWO_DESC;
import static net.yoedtos.blog.util.TestConstants.MEDIA_TWO_ID;
import static net.yoedtos.blog.util.TestConstants.MEDIA_TWO_NAME;
import static net.yoedtos.blog.util.TestConstants.MEDIA_TWO_URN;
import static net.yoedtos.blog.util.TestConstants.USERNAME_ONE;
import static net.yoedtos.blog.util.TestUtil.createDate;
import static net.yoedtos.blog.util.TestUtil.createMediaOne;
import static net.yoedtos.blog.util.TestUtil.createUserOne;
import static net.yoedtos.blog.util.TestUtil.createUserTwo;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.dbunit.DatabaseUnitException;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;

import net.yoedtos.blog.exception.DaoException;
import net.yoedtos.blog.factory.RepositoryFactory;
import net.yoedtos.blog.model.entity.Media;
import net.yoedtos.blog.model.entity.User;

public class MediaDaoTest extends AbstractDaoTest {
	
	private MediaDao mediaDao;
	private User userOne;
	private Media mediaOne;
	private Date dateOne;
	
	public MediaDaoTest() throws DatabaseUnitException, SQLException {
		super();
		mediaDao = RepositoryFactory.create(MediaDao.class);
		dateOne = createDate(CREATE_ONE);
		userOne = createUserOne((dateOne));
		mediaOne = createMediaOne(dateOne);
	}

	@Before
	public void init() throws DatabaseUnitException, SQLException {
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
	}
	
	@Test
	public void whenFindAllShouldReturnTwoMedia() throws DaoException {
		Date createTwo = createDate(CREATE_TWO);
		User userTwo = createUserTwo(createTwo);
		
		Media mediaTwo = new Media.Builder()
				.id(MEDIA_TWO_ID)
				.createAt(createTwo)
				.owner(userTwo)
				.name(MEDIA_TWO_NAME)
				.description(MEDIA_TWO_DESC)
				.contentType(CONTENT_TYPE_TWO)
				.urn(MEDIA_TWO_URN)
				.build();
		
		List<Media> medias = mediaDao.findAll();
		assertThat(medias.size(), equalTo(2));
		assertThat(medias, hasItems(new Media[] {mediaOne, mediaTwo}));
	}
	
	@Test
	public void whenFindAllByUserShouldReturnOne() throws DaoException {
		List<Media> medias = mediaDao.findAllByUser(USERNAME_ONE);
		assertThat(medias.size(), equalTo(1));
		assertThat(medias, hasItem(mediaOne));
	}
	
	@Test
	public void whenFindByIdShouldReturnMedia() throws DaoException {
		Media mediaDb = mediaDao.findById(MEDIA_ONE_ID);
		assertThat(mediaDb, equalTo(mediaOne));
	}
	
	@Test
	public void whenFindByIdUnknowMediaIdShouldReturnNull() throws DaoException {
		Media nullMedia =  mediaDao.findById(MEDIA_OUT_ID);
		assertNull(nullMedia);
	}
	
	@Test
	public void whenAddMediaShouldHaveOne() throws DaoException {
		Media media = new Media.Builder()
				.createAt(dateOne)
				.owner(userOne)
				.name(MEDIA_NEW_NAME)
				.description(MEDIA_NEW_DESC)
				.contentType(CONTENT_TYPE_ONE)
				.urn(MEDIA_NEW_URN)
				.build();
				
		mediaDao.persist(media);
		
		Media mediaDb = mediaDao.findById(MEDIA_NEW_ID);
		assertThat(mediaDb, equalTo(media));
	}
}
