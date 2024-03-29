package net.yoedtos.blog.service;

import static net.yoedtos.blog.util.TestConstants.AUTHOR_ONE;
import static net.yoedtos.blog.util.TestConstants.AUTHOR_TWO;
import static net.yoedtos.blog.util.TestConstants.CATEGORY_TWO_ID;
import static net.yoedtos.blog.util.TestConstants.COMMENT_ONE;
import static net.yoedtos.blog.util.TestConstants.COMMENT_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.COMMENT_TWO;
import static net.yoedtos.blog.util.TestConstants.EMAIL_ONE;
import static net.yoedtos.blog.util.TestConstants.EMAIL_TWO;
import static net.yoedtos.blog.util.TestConstants.HOST_ADDRESS;
import static net.yoedtos.blog.util.TestConstants.POST_ONE_ID;
import static net.yoedtos.blog.util.TestUtil.changeToSeconds;
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
		
		commentDTO = createCommentDTO(null);
		commentOne = createCommentOne(today, post);
	}

	@Test
	public void whenCreateShouldCreateOnce() throws ServiceException, DaoException {
		when(postDao.findById(POST_ONE_ID)).thenReturn(post);
		commentService.create(commentDTO);
		verify(commentDaoMock).persist(captor.capture());
		Comment comment = captor.getValue();

		assertThat(comment.getId(), equalTo(COMMENT_ONE_ID));
		assertThat(changeToSeconds(comment.getCreateAt()), equalTo(changeToSeconds(today)));
		assertThat(comment.getContent(), equalTo(COMMENT_ONE));
		assertThat(comment.getAuthor(), equalTo(AUTHOR_ONE));
		assertThat(comment.getEmail(), equalTo(EMAIL_ONE));
		assertThat(comment.getHostAddress(), equalTo(HOST_ADDRESS));
		assertThat(comment.getPost(), equalTo(post));
		
		verify(postDao, times(1)).findById(POST_ONE_ID);
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
		commentService.remove(COMMENT_ONE_ID);
	}

	@Test
	public void whenGetShouldReturnNull() throws ServiceException {
		CommentDTO nullCommentDTO = commentService.get(COMMENT_ONE_ID);
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
		
		Comment commentTwo = new Comment.Builder(AUTHOR_TWO)
				.id(CATEGORY_TWO_ID)
				.createAt(today)
				.content(COMMENT_TWO)
				.email(EMAIL_TWO)
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
	
	@Test
	public void whenGetAllByPostShouldReturnOneComment() throws ServiceException, DaoException {

		List<Comment> comments = Arrays.asList(commentOne);
		when(commentDaoMock.findAllByPostId(POST_ONE_ID)).thenReturn(comments);
		List<CommentDTO> commentDTOs = commentService.getAllByPost(POST_ONE_ID);
		
		assertEquals(1, commentDTOs.size());
		assertThat(commentDTOs.get(0).getId(), equalTo(COMMENT_ONE_ID));
		assertThat(changeToSeconds(commentDTOs.get(0).getCreateAt()), equalTo(changeToSeconds(today)));
		assertThat(commentDTOs.get(0).getContent(), equalTo(COMMENT_ONE));
		assertThat(commentDTOs.get(0).getAuthor(), equalTo(AUTHOR_ONE));
		assertThat(commentDTOs.get(0).getEmail(), equalTo(EMAIL_ONE));
		assertThat(commentDTOs.get(0).getHostAddress(), equalTo(HOST_ADDRESS));
		assertThat(commentDTOs.get(0).getPostId(), equalTo(POST_ONE_ID));
	}
}
