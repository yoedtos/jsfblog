package net.yoedtos.blog.repository.dao;

import static net.yoedtos.blog.util.TestConstants.AUTHOR_NEW;
import static net.yoedtos.blog.util.TestConstants.AUTHOR_TWO;
import static net.yoedtos.blog.util.TestConstants.COMMENT_NEW;
import static net.yoedtos.blog.util.TestConstants.COMMENT_NEW_ID;
import static net.yoedtos.blog.util.TestConstants.COMMENT_TWO;
import static net.yoedtos.blog.util.TestConstants.COMMENT_TWO_ID;
import static net.yoedtos.blog.util.TestConstants.CREATE_ONE;
import static net.yoedtos.blog.util.TestConstants.CREATE_TWO;
import static net.yoedtos.blog.util.TestConstants.EMAIL_NEW;
import static net.yoedtos.blog.util.TestConstants.EMAIL_TWO;
import static net.yoedtos.blog.util.TestConstants.HOST_ADDRESS;
import static net.yoedtos.blog.util.TestConstants.POST_ONE_ID;
import static net.yoedtos.blog.util.TestUtil.createCategory;
import static net.yoedtos.blog.util.TestUtil.createCommentOne;
import static net.yoedtos.blog.util.TestUtil.createDate;
import static net.yoedtos.blog.util.TestUtil.createPostOne;
import static net.yoedtos.blog.util.TestUtil.createUserOne;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
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
import net.yoedtos.blog.model.entity.Comment;
import net.yoedtos.blog.model.entity.Post;

public class CommentDaoTest extends AbstractDaoTest {
	
	private CommentDao commentDao;
	private Comment commentOne;
	private Comment commentTwo;
	private Post postOne;
	private Date createOne;
	
	public CommentDaoTest() throws DatabaseUnitException, SQLException {
		super();
		commentDao = RepositoryFactory.create(CommentDao.class);
		createOne = createDate(CREATE_ONE);
		postOne = createPostOne(createOne, createUserOne(createOne), createCategory());
		commentOne = createCommentOne(createOne, postOne);
		commentTwo = new Comment.Builder(AUTHOR_TWO)
				.id(COMMENT_TWO_ID)
				.createAt(createDate(CREATE_TWO))
				.content(COMMENT_TWO)
				.email(EMAIL_TWO)
				.hostAddress(HOST_ADDRESS)
				.post(postOne)
				.build();
	}

	@Before
	public void init() throws DatabaseUnitException, SQLException {
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
	}
	
	@Test
	public void whenFindAllShouldReturnTwoComments() throws DaoException {
		List<Comment> comments = commentDao.findAll();
		assertThat(comments.size(), equalTo(2));
		assertThat(comments, hasItems(new Comment[] {commentOne, commentTwo}));
	}
	
	@Test(expected = DaoException.class)
	public void whenAddCommentTwiceShouldThrowException() throws DaoException {
		commentDao.persist(commentOne);
	}
	 
	@Test
	public void whenAddCommentShouldHaveOne() throws DaoException {
		Comment comment = new Comment.Builder(AUTHOR_NEW)
				.createAt(createOne)
				.content(COMMENT_NEW)
				.email(EMAIL_NEW)
				.hostAddress(HOST_ADDRESS)
				.post(postOne)
				.build();
		
		commentDao.persist(comment);
		
		Comment commentDb = commentDao.findById(COMMENT_NEW_ID);
		assertThat(commentDb.getId(), equalTo(COMMENT_NEW_ID));
		assertThat(commentDb.getCreateAt(), equalTo(Timestamp.valueOf(CREATE_ONE)));
		assertThat(commentDb.getContent(), equalTo(COMMENT_NEW));
		assertThat(commentDb.getAuthor(), equalTo(AUTHOR_NEW));
		assertThat(commentDb.getEmail(), equalTo(EMAIL_NEW));
		assertThat(commentDb.getHostAddress(), equalTo(HOST_ADDRESS));
	}
	
	@Test
	public void whenFindAllByPostIdShouldReturnTwoComments() throws DaoException {
		List<Comment> comments = commentDao.findAllByPostId(POST_ONE_ID);
		assertThat(comments.size(), equalTo(2));
		assertThat(comments, hasItems(new Comment[] {commentOne, commentTwo}));
	}
}
