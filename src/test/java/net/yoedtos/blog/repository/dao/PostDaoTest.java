package net.yoedtos.blog.repository.dao;

import static net.yoedtos.blog.util.TestConstants.BEGIN_ZERO;
import static net.yoedtos.blog.util.TestConstants.CONTENT_NEW;
import static net.yoedtos.blog.util.TestConstants.CONTENT_ONE;
import static net.yoedtos.blog.util.TestConstants.CONTENT_UPDATE;
import static net.yoedtos.blog.util.TestConstants.CREATE_ONE;
import static net.yoedtos.blog.util.TestConstants.INTRO_NEW;
import static net.yoedtos.blog.util.TestConstants.INTRO_ONE;
import static net.yoedtos.blog.util.TestConstants.INTRO_UPDATE;
import static net.yoedtos.blog.util.TestConstants.MAX_PAGES;
import static net.yoedtos.blog.util.TestConstants.META_DESC;
import static net.yoedtos.blog.util.TestConstants.META_KEY;
import static net.yoedtos.blog.util.TestConstants.POST_NEW_ID;
import static net.yoedtos.blog.util.TestConstants.POST_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.TITLE_NEW;
import static net.yoedtos.blog.util.TestConstants.TITLE_ONE;
import static net.yoedtos.blog.util.TestConstants.TITLE_UPDATE;
import static net.yoedtos.blog.util.TestConstants.USERNAME_ONE;
import static net.yoedtos.blog.util.TestUtil.createCategory;
import static net.yoedtos.blog.util.TestUtil.createDate;
import static net.yoedtos.blog.util.TestUtil.createPostOne;
import static net.yoedtos.blog.util.TestUtil.createUserOne;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
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
import net.yoedtos.blog.model.entity.Category;
import net.yoedtos.blog.model.entity.Post;
import net.yoedtos.blog.model.entity.User;

public class PostDaoTest extends AbstractDaoTest {
	
	private PostDao postDao;
	private User userOne;
	private Post postOne;
	private Date createOne;
	private Category category;
	
	public PostDaoTest() throws DatabaseUnitException, SQLException {
		super();
		postDao = RepositoryFactory.create(PostDao.class);
		createOne = createDate(CREATE_ONE);
		category = createCategory();
		userOne = createUserOne((createOne));
		postOne = createPostOne(createOne, userOne, category);
	}

	@Before
	public void init() throws DatabaseUnitException, SQLException {
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
	}
	
	@Test
	public void whenFindAllShouldReturnTwoPosts() throws DaoException {
		List<Post> posts = postDao.findAll();
		assertThat(posts.size(), equalTo(2));
		
		assertThat(posts.get(0).getId(), equalTo(POST_ONE_ID));
		assertThat(posts.get(0).getCreatedAt(), equalTo(Timestamp.valueOf(CREATE_ONE)));
		assertThat(posts.get(0).getTitle(), equalTo(TITLE_ONE));
		assertThat(posts.get(0).getAuthor(), equalTo(userOne));
		assertThat(posts.get(0).getCategory(), equalTo(category));
		assertThat(posts.get(0).getIntro(), equalTo(INTRO_ONE));
		assertThat(posts.get(0).getContent(), equalTo(CONTENT_ONE));
		assertThat(posts.get(0).getMetaDesc(), equalTo(META_DESC));
		assertThat(posts.get(0).getMetaKey(), equalTo(META_KEY));
	}
	
	@Test
	public void whenFindByIdShouldReturnPost() throws DaoException {
		Post postDb = postDao.findById(POST_ONE_ID);
		assertThat(postDb, equalTo(postOne));
	}
	
	@Test(expected = DaoException.class)
	public void whenAddPostTwiceShouldThrowException() throws DaoException {
		postDao.persist(postOne);
	}
	 
	@Test
	public void whenAddPostShouldHaveOne() throws DaoException {
		Post post = new Post.Builder()
				.createdAt(createOne)
				.title(TITLE_NEW)
				.author(userOne)
				.category(category)
				.intro(INTRO_NEW)
				.content(CONTENT_NEW)
				.metaDesc(META_DESC)
				.metaKey(META_KEY)
				.build();
				
		postDao.persist(post);
		
		Post postDb = postDao.findById(POST_NEW_ID);
		assertThat(postDb.getId(), equalTo(POST_NEW_ID));
		assertThat(postDb.getCreatedAt(), equalTo(Timestamp.valueOf(CREATE_ONE)));
		assertThat(postDb.getTitle(), equalTo(TITLE_NEW));
		assertThat(postDb.getAuthor(), equalTo(userOne));
		assertThat(postDb.getCategory(), equalTo(category));
		assertThat(postDb.getIntro(), equalTo(INTRO_NEW));
		assertThat(postDb.getContent(), equalTo(CONTENT_NEW));
		assertThat(postDb.getMetaDesc(), equalTo(META_DESC));
		assertThat(postDb.getMetaKey(), equalTo(META_KEY));
	}
	
	@Test
	public void whenUpdatePostShouldReturnPost() throws DaoException {
		Post post = new Post.Builder()
				.id(POST_ONE_ID)
				.createdAt(createOne)
				.title(TITLE_UPDATE)
				.author(userOne)
				.category(category)
				.intro(INTRO_UPDATE)
				.content(CONTENT_UPDATE)
				.metaDesc(META_DESC)
				.metaKey(META_KEY)
				.build();
		
		Post postDb = postDao.merge(post);
		assertThat(postDb, equalTo(post));
	}
	
	@Test
	public void whenGetLatestBetweenShouldReturnTwo() throws DaoException {
		List<Post> posts = postDao.getLatestBetween(BEGIN_ZERO, MAX_PAGES);
		assertThat(posts.size(), equalTo(2));
		
		Post lightPost = new Post.Builder()
				.id(POST_ONE_ID)
				.createdAt(createOne)
				.title(TITLE_ONE)
				.author(userOne)
				.category(new Category())
				.intro(INTRO_ONE)
				.content("")
				.metaDesc("")
				.metaKey("")
				.build();
		
		assertThat(posts, hasItem(lightPost));
	}
	
	@Test
	public void whenGetTotalPostShouldReturnTwo() throws DaoException {
		Long total = postDao.getTotalPosts();
		assertThat(total, equalTo(2L));
	}
	
	@Test
	public void whenFindAllByUserShouldReturnOne() throws DaoException {
		List<Post> posts = postDao.findAllByUser(USERNAME_ONE);
		assertThat(posts.size(), equalTo(1));
		assertThat(posts, hasItem(postOne));
	}
}
