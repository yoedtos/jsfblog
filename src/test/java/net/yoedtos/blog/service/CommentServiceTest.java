package net.yoedtos.blog.service;

import static net.yoedtos.blog.util.TestConstants.AUTHOR;
import static net.yoedtos.blog.util.TestConstants.COMMENT_ID;
import static net.yoedtos.blog.util.TestConstants.COMMENT_VALUE;
import static net.yoedtos.blog.util.TestConstants.EMAIL;
import static net.yoedtos.blog.util.TestConstants.HOST_ADDRESS;
import static net.yoedtos.blog.util.TestConstants.POST_ID;
import static net.yoedtos.blog.util.TestUtil.createCategory;
import static net.yoedtos.blog.util.TestUtil.createCommentDTO;
import static net.yoedtos.blog.util.TestUtil.createCommentOne;
import static net.yoedtos.blog.util.TestUtil.createPostOne;
import static net.yoedtos.blog.util.TestUtil.createUserOne;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
import net.yoedtos.blog.model.dto.CommentDTO;
import net.yoedtos.blog.model.entity.Comment;
import net.yoedtos.blog.model.entity.Post;
import net.yoedtos.blog.model.entity.User;
import net.yoedtos.blog.repository.dao.CommentDao;
import net.yoedtos.blog.repository.dao.PostDao;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {
	
	@Mock
	private PostDao postDao;

	@Mock
	private CommentDao commentDaoMock;

	@InjectMocks
	private CommentService commentService;

	@Captor
	private ArgumentCaptor<Comment> captor;

	private Date today;
	private CommentDTO commentDTO;
	private Comment commentOne;
	private Post post;
	private User user;

	@Before
	public void init() {
		today = new Date();
		user = createUserOne(today);
		post = createPostOne(today, user, createCategory());
		
		commentDTO = createCommentDTO(today);
		commentOne = createCommentOne(today, post);
	}

	@Test
	public void whenCreateShouldCreateOnce() throws ServiceException, DaoException {
		when(postDao.findById(POST_ID)).thenReturn(post);
		commentService.create(commentDTO);
		verify(commentDaoMock).persist(captor.capture());
		Comment comment = captor.getValue();

		assertThat(comment.getId(), equalTo(COMMENT_ID));
		assertThat(comment.getCreateAt(), equalTo(today));
		assertThat(comment.getContent(), equalTo(COMMENT_VALUE));
		assertThat(comment.getAuthor(), equalTo(AUTHOR));
		assertThat(comment.getEmail(), equalTo(EMAIL));
		assertThat(comment.getHostAddress(), equalTo(HOST_ADDRESS));
		assertThat(comment.getPost(), equalTo(post));
		
		verify(postDao, times(1)).findById(POST_ID);
		verify(commentDaoMock, times(1)).persist(comment);
	}

	@Test(expected = ServiceException.class)
	public void whenCreateShouldThrowsException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(commentDaoMock).persist(commentOne);
		commentService.create(commentDTO);
		verify(commentDaoMock, times(1)).persist(commentOne);
	}

	@Test(expected = ServiceException.class)
	public void whenRemoveShouldThrowsException() throws ServiceException {
		commentService.remove(COMMENT_ID);
	}

	@Test
	public void whenGetShouldReturnNull() throws ServiceException {
		CommentDTO nullCommentDTO = commentService.get(COMMENT_ID);
		assertNull(nullCommentDTO);
	}

	@Test
	public void whenUpdateShouldReturnNull() throws ServiceException {
		CommentDTO nullCommenttDTO = commentService.update(commentDTO);
		assertNull(nullCommenttDTO);
	}

	@Test(expected = ServiceException.class)
	public void whenGetAllShouldThrowsException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(commentDaoMock).findAll();
		commentService.getAll();
		verify(commentDaoMock, times(1)).findAll();
	}

	@Test
	public void whenGetAllShouldReturnTwoComments() throws ServiceException, DaoException {
		final String COMMENT_TWO = " Donec vitae sapien ut libero venenatis faucibus."
				+ "Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt.";
		
		Comment commentTwo = new Comment.Builder("Vistor Two")
				.id(2L)
				.createAt(today)
				.content(COMMENT_TWO)
				.email("visitor2@domain.com")
				.hostAddress(HOST_ADDRESS)
				.post(post)
				.build();
		
		List<Comment> comments = Arrays.asList(commentOne, commentTwo);
		when(commentDaoMock.findAll()).thenReturn(comments);
		List<CommentDTO> commentDTOs = commentService.getAll();
		assertEquals(2, commentDTOs.size());
		
		int index = 0;
		for (CommentDTO commentDTO : commentDTOs) {
			assertThat(commentDTO.getId(), equalTo(comments.get(index).getId()));
			assertThat(commentDTO.getCreateAt(), equalTo(comments.get(index).getCreateAt()));
			assertThat(commentDTO.getContent(), equalTo(comments.get(index).getContent()));
			assertThat(commentDTO.getAuthor(), equalTo(comments.get(index).getAuthor()));
			assertThat(commentDTO.getEmail(), equalTo(comments.get(index).getEmail()));
			assertThat(commentDTO.getHostAddress(), equalTo(comments.get(index).getHostAddress()));
			assertThat(commentDTO.getPostId(), equalTo(comments.get(index).getPost().getId()));
			index++;
		}
	}
}
