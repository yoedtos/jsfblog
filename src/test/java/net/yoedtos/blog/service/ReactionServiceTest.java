package net.yoedtos.blog.service;

import static net.yoedtos.blog.util.TestConstants.AUTHOR_ONE;
import static net.yoedtos.blog.util.TestConstants.COMMENT_ONE;
import static net.yoedtos.blog.util.TestConstants.COMMENT_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.EMAIL_ONE;
import static net.yoedtos.blog.util.TestConstants.HOST_ADDRESS;
import static net.yoedtos.blog.util.TestConstants.POST_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.REPLY_ONE;
import static net.yoedtos.blog.util.TestConstants.REPLY_TWO;
import static net.yoedtos.blog.util.TestConstants.REPLY_TWO_ID;
import static net.yoedtos.blog.util.TestConstants.USERNAME_ONE;
import static net.yoedtos.blog.util.TestUtil.changeToSeconds;
import static net.yoedtos.blog.util.TestUtil.createCommentDTO;
import static net.yoedtos.blog.util.TestUtil.createReplyDTO;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.yoedtos.blog.exception.ServiceException;
import net.yoedtos.blog.model.dto.CommentDTO;
import net.yoedtos.blog.model.dto.ReplyDTO;

@RunWith(MockitoJUnitRunner.class)
public class ReactionServiceTest {

	@Mock
	private CommentService commentServiceMock;

	@Mock
	private ReplyService replyServiceMock;

	@InjectMocks
	private ReactionService reactionService;

	@Captor
	private ArgumentCaptor<?> captor;

	private Date today;
	private CommentDTO commentDTO;
	private ReplyDTO replyOneDTO;

	@Before
	public void init() {
		today = new Date();
		commentDTO = createCommentDTO(today);
		replyOneDTO = createReplyDTO(today);
	}

	@Test
	public void whenCreateCommentSuccessShouldHaveOnce() throws ServiceException {
		reactionService.create(commentDTO);
		verify(commentServiceMock).create((CommentDTO) captor.capture());
		CommentDTO commentDto = (CommentDTO) captor.getValue();

		assertThat(changeToSeconds(commentDto.getCreateAt()), equalTo(changeToSeconds(today)));
		assertThat(commentDto.getContent(), equalTo(COMMENT_ONE));
		assertThat(commentDto.getAuthor(), equalTo(AUTHOR_ONE));
		assertThat(commentDto.getEmail(), equalTo(EMAIL_ONE));
		assertThat(commentDto.getHostAddress(), equalTo(HOST_ADDRESS));
		assertThat(commentDto.getPostId(), equalTo(POST_ONE_ID));

		verify(commentServiceMock, times(1)).create(commentDto);
	}

	@Test
	public void whenCreateReplySuccessShouldHaveOnce() throws ServiceException {
		reactionService.create(replyOneDTO);
		verify(replyServiceMock).create((ReplyDTO) captor.capture());
		ReplyDTO replyDto = (ReplyDTO) captor.getValue();

		assertThat(changeToSeconds(replyDto.getCreateAt()), equalTo(changeToSeconds(today)));
		assertThat(replyDto.getContent(), equalTo(REPLY_ONE));
		assertThat(replyDto.getAuthor(), equalTo(USERNAME_ONE));
		assertThat(replyDto.getHostAddress(), equalTo(HOST_ADDRESS));
		assertThat(replyDto.getCommentId(), equalTo(COMMENT_ONE_ID));

		verify(replyServiceMock, times(1)).create(replyDto);
	}

	@Test 
	public void whenGetReactionsShouldHave() throws ServiceException {
		ReplyDTO replyTwoDTO = new ReplyDTO.Builder(USERNAME_ONE)
				.replyId(REPLY_TWO_ID)
				.commentId(COMMENT_ONE_ID)
				.content(REPLY_TWO)
				.createAt(today)
				.hostAddress(HOST_ADDRESS)
				.build();
		
		List<ReplyDTO> replies = Arrays.asList(replyOneDTO, replyTwoDTO);
		
		when(commentServiceMock.getAllByPost(POST_ONE_ID)).thenReturn(Arrays.asList(commentDTO));
		when(replyServiceMock.getAllByComment(COMMENT_ONE_ID)).thenReturn(replies);

		List<Entry<CommentDTO, List<ReplyDTO>>> reaction = reactionService.get(POST_ONE_ID);

		for (Entry<CommentDTO, List<ReplyDTO>> entry : reaction) {
			assertThat(entry.getKey(), equalTo(commentDTO));
			assertThat(entry.getValue(), hasItems(replyOneDTO, replyTwoDTO));
		}

		verify(commentServiceMock, times(1)).getAllByPost(POST_ONE_ID);
		verify(replyServiceMock, times(1)).getAllByComment(COMMENT_ONE_ID);
	}
}
