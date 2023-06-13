package net.yoedtos.blog.service;

import static net.yoedtos.blog.util.TestConstants.COMMENT_ID;
import static net.yoedtos.blog.util.TestConstants.HOST_ADDRESS;
import static net.yoedtos.blog.util.TestConstants.REPLY_ID;
import static net.yoedtos.blog.util.TestConstants.REPLY_VALUE;
import static net.yoedtos.blog.util.TestConstants.USERNAME;
import static net.yoedtos.blog.util.TestUtil.createCommentOne;
import static net.yoedtos.blog.util.TestUtil.createReplyDTO;
import static net.yoedtos.blog.util.TestUtil.createReplyOne;
import static net.yoedtos.blog.util.TestUtil.createUserOne;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
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
import net.yoedtos.blog.model.dto.ReplyDTO;
import net.yoedtos.blog.model.entity.Comment;
import net.yoedtos.blog.model.entity.Post;
import net.yoedtos.blog.model.entity.Reply;
import net.yoedtos.blog.model.entity.User;
import net.yoedtos.blog.repository.dao.CommentDao;
import net.yoedtos.blog.repository.dao.ReplyDao;
import net.yoedtos.blog.repository.dao.UserDao;

@RunWith(MockitoJUnitRunner.class)
public class ReplyServiceTest {
	
	@Mock
	private UserDao userDao;
	
	@Mock
	private CommentDao commentDao;

	@Mock
	private ReplyDao replyDaoMock;

	@InjectMocks
	private ReplyService replyService;

	@Captor
	private ArgumentCaptor<Reply> captor;

	private Date today;
	private ReplyDTO replyDTO;
	private Reply replyOne;
	private Comment comment;
	private User user;
	
	@Before
	public void init() {
		today = new Date();
		user = createUserOne(today);
		comment = createCommentOne(today, mock(Post.class));
		
		replyDTO = createReplyDTO(today);
		replyOne = createReplyOne(today, comment, user);
	}

	@Test
	public void whenCreateShouldCreateOnce() throws ServiceException, DaoException {
		when(userDao.findByUsername(USERNAME)).thenReturn(user);
		when(commentDao.findById(COMMENT_ID)).thenReturn(comment);
		replyService.create(replyDTO);
		verify(replyDaoMock).persist(captor.capture());
		Reply reply = captor.getValue();

		assertThat(reply.getId(), equalTo(REPLY_ID));
		assertThat(reply.getCreateAt(), equalTo(today));
		assertThat(reply.getContent(), equalTo(REPLY_VALUE));
		assertThat(reply.getAuthor(), equalTo(user));
		assertThat(reply.getHostAddress(), equalTo(HOST_ADDRESS));
		assertThat(reply.getComment(), equalTo(comment));
		
		verify(commentDao, times(1)).findById(COMMENT_ID);
		verify(replyDaoMock, times(1)).persist(reply);
	}

	@Test(expected = ServiceException.class)
	public void whenCreateShouldThrowsException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(replyDaoMock).persist(replyOne);
		replyService.create(replyDTO);
		verify(replyDaoMock, times(1)).persist(replyOne);
	}

	@Test(expected = ServiceException.class)
	public void whenRemoveShouldThrowsException() throws ServiceException {
		replyService.remove(REPLY_ID);
	}

	@Test
	public void whenGetShouldReturnNull() throws ServiceException {
		ReplyDTO nullReplyDTO = replyService.get(REPLY_ID);
		assertNull(nullReplyDTO);
	}

	@Test
	public void whenUpdateShouldReturnNull() throws ServiceException {
		ReplyDTO nullReplyDTO = replyService.update(replyDTO);
		assertNull(nullReplyDTO);
	}

	@Test
	public void whenGetAllShouldThrowsException() throws ServiceException {
		List<ReplyDTO> nullReplyDTOs = replyService.getAll();
		assertNull(nullReplyDTOs);
	}
	
	@Test(expected = ServiceException.class)
	public void whenGetAllByCommentShouldThrowsException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(replyDaoMock).findAllByCommentId(COMMENT_ID);
		replyService.getAllByComment(COMMENT_ID);
		verify(replyDaoMock, times(1)).findAllByCommentId(COMMENT_ID);
	}

	@Test
	public void whenGetAllByCommentShouldReturnTwoComments() throws ServiceException, DaoException {
		final String REPLY_TWO = " Donec vitae sapien ut libero venenatis faucibus."
				+ "Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt.";
		
		Reply replyTwo = new Reply.Builder()
				.id(2L)
				.createAt(today)
				.content(REPLY_TWO)
				.hostAddress(HOST_ADDRESS)
				.author(user)
				.comment(comment)
				.build();

		List<Reply> replys = Arrays.asList(replyOne, replyTwo);
		when(replyDaoMock.findAllByCommentId(COMMENT_ID)).thenReturn(replys);
		List<ReplyDTO> replyDTOs = replyService.getAllByComment(COMMENT_ID);
		assertEquals(2, replyDTOs.size());
		
		int index = 0;
		for (ReplyDTO replyDTO : replyDTOs) {
			assertThat(replyDTO.getId(), equalTo(replys.get(index).getId()));
			assertThat(replyDTO.getCreateAt(), equalTo(replys.get(index).getCreateAt()));
			assertThat(replyDTO.getContent(), equalTo(replys.get(index).getContent()));
			assertThat(replyDTO.getAuthor(), equalTo(replys.get(index).getAuthor().getUsername()));
			assertThat(replyDTO.getCommentId(), equalTo(replys.get(index).getComment().getId()));
			assertThat(replyDTO.getHostAddress(), equalTo(replys.get(index).getHostAddress()));
			index++;
		}
	}
}
