package net.yoedtos.blog.repository.dao;

import static net.yoedtos.blog.util.TestConstants.*;
import static net.yoedtos.blog.util.TestUtil.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

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
import net.yoedtos.blog.model.entity.Reply;
import net.yoedtos.blog.model.entity.User;
import net.yoedtos.blog.model.entity.Comment;
import net.yoedtos.blog.model.entity.Post;

public class ReplyDaoTest extends AbstractDaoTest {
	
	private ReplyDao replyDao;
	private Reply replyOne;
	private Comment commentOne;
	private User userOne;
	private Date createOne;
	
	public ReplyDaoTest() throws DatabaseUnitException, SQLException {
		super();
		replyDao = RepositoryFactory.create(ReplyDao.class);
		createOne = createDate(CREATE_ONE);
		userOne = createUserOne(createOne);
		commentOne = createCommentOne(createOne, mock(Post.class));
		replyOne = createReplyOne(createOne, commentOne, userOne);
	}

	@Before
	public void init() throws DatabaseUnitException, SQLException {
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
	}
	
	@Test
	public void whenFindAllShouldReturnTwoReplies() throws DaoException {
		Reply replyTwo = new Reply.Builder()
				.id(REPLY_TWO_ID)
				.createAt(createDate(CREATE_TWO))
				.content(REPLY_TWO)
				.hostAddress(HOST_ADDRESS)
				.author(userOne)
				.comment(commentOne)
				.build();
		
		List<Reply> replies = replyDao.findAll();
		assertThat(replies.size(), equalTo(2));
		assertThat(replies, hasItems(new Reply[] {replyOne, replyTwo}));
	}
	
	@Test
	public void whenFindAllByCommentIdShouldReturnOneReply() throws DaoException {
		List<Reply> replies = replyDao.findAllByCommentId(COMMENT_ONE_ID);
		assertThat(replies.size(), equalTo(1));		
		assertThat(replies, hasItems(new Reply[] {replyOne}));
	}
	
	@Test(expected = DaoException.class)
	public void whenAddReplyTwiceShouldThrowException() throws DaoException {
		replyDao.persist(replyOne);
	}
	 
	@Test
	public void whenAddReplyShouldHaveOne() throws DaoException {
		Reply reply = new Reply.Builder()
				.createAt(createOne)
				.content(REPLY_NEW)
				.hostAddress(HOST_ADDRESS)
				.author(userOne)
				.comment(commentOne)
				.build();
		
		replyDao.persist(reply);
		
		Reply replyDb = replyDao.findById(REPLY_NEW_ID);
		assertThat(replyDb.getId(), equalTo(REPLY_NEW_ID));
		assertThat(replyDb.getCreateAt(), equalTo(Timestamp.valueOf(CREATE_ONE)));
		assertThat(replyDb.getContent(), equalTo(REPLY_NEW));
		assertThat(replyDb.getAuthor(), equalTo(userOne));
		assertThat(replyDb.getHostAddress(), equalTo(HOST_ADDRESS));
	}
}
