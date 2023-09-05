package net.yoedtos.blog.service;

import static net.yoedtos.blog.util.TestConstants.CONTENT_TWO;
import static net.yoedtos.blog.util.TestConstants.EMAIL_TWO;
import static net.yoedtos.blog.util.TestConstants.FULLNAME_TWO;
import static net.yoedtos.blog.util.TestConstants.HOST_ADDRESS;
import static net.yoedtos.blog.util.TestConstants.MESSAGE_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.MESSAGE_TWO_ID;
import static net.yoedtos.blog.util.TestConstants.TITLE_TWO;
import static net.yoedtos.blog.util.TestUtil.createMessageDTO;
import static net.yoedtos.blog.util.TestUtil.createMessageOne;
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
import net.yoedtos.blog.model.dto.MessageDTO;
import net.yoedtos.blog.model.entity.Message;
import net.yoedtos.blog.repository.dao.MessageDao;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {
	
	@Mock
	private MessageDao messageDaoMock;

	@InjectMocks
	private MessageService messageService;

	@Captor
	private ArgumentCaptor<?> captor;

	private Message messageOne;
	private MessageDTO messageDTO;
	private Date today;
	
	@Before
	public void init() {
		today = new Date();
		messageDTO = createMessageDTO();
		messageOne = createMessageOne(today, null);
	}

	@Test
	public void whenCreateShouldCreateOnce() throws ServiceException, DaoException {
		messageService.create(messageDTO);
		verify(messageDaoMock).persist((Message)captor.capture());
		Message message = (Message)captor.getValue();
		assertEquals(messageOne, message);
		verify(messageDaoMock, times(1)).persist(message);
	}
	
	@Test
	public void whenRemoveShouldRemoveOnce() throws DaoException, ServiceException {
		messageService.remove(MESSAGE_ONE_ID);
		verify(messageDaoMock).remove((Long) captor.capture());
		assertEquals(MESSAGE_ONE_ID, ((Long) captor.getValue()).intValue());
		verify(messageDaoMock, times(1)).remove(MESSAGE_ONE_ID);
	}
	
	@Test(expected = ServiceException.class)
	public void whenCreateShouldThrowsException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(messageDaoMock).persist(messageOne);
		messageService.create(messageDTO);
		verify(messageDaoMock, times(1)).persist(messageOne);
	}
	
	@Test(expected = ServiceException.class)
	public void whenRemoveShouldThrowsException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(messageDaoMock).remove(MESSAGE_ONE_ID);
		messageService.remove(MESSAGE_ONE_ID);
		verify(messageDaoMock, times(1)).remove(MESSAGE_ONE_ID);
	}
	
	@Test
	public void whenGetShouldReturnNull() throws ServiceException {
		MessageDTO nullNoticeDTO = messageService.get(MESSAGE_ONE_ID);
		assertNull(nullNoticeDTO);
	}
	
	@Test
	public void whenUpdateShouldReturnNull() throws ServiceException {
		MessageDTO nullNoticeDTO = messageService.update(messageDTO);
		assertNull(nullNoticeDTO );
	}
	
	@Test(expected = ServiceException.class)
	public void whenGetAllShouldThrowsException() throws DaoException, ServiceException {
		doThrow(new DaoException()).when(messageDaoMock).findAll();
		messageService.getAll();
		verify(messageDaoMock, times(1)).findAll();
	}
	
	@Test
	public void whenGetAllShouldReturnTwoNotices() throws ServiceException, DaoException {
		messageOne.setId(MESSAGE_ONE_ID);
		
		Message messageTwo = new Message.Builder()
				.id(MESSAGE_TWO_ID)
				.createAt(today)
				.senderName(FULLNAME_TWO)
				.senderEmail(EMAIL_TWO)
				.subject(TITLE_TWO)
				.content(CONTENT_TWO)
				.hostAddress(HOST_ADDRESS)
				.build();
		
		List<Message> messages = Arrays.asList(messageOne, messageTwo);
		when(messageDaoMock.findAll()).thenReturn(messages);
		List<MessageDTO> messageDTOs = messageService.getAll();
		assertEquals(2, messageDTOs.size());
		
		int index = 0;
		for (MessageDTO messageDTO : messageDTOs) {
			assertThat(messageDTO.getId(), equalTo(messages.get(index).getId()));
			assertThat(messageDTO.getCreateAt(), equalTo(messages.get(index).getCreateAt()));
			assertThat(messageDTO.getSenderName(), equalTo(messages.get(index).getSenderName()));
			assertThat(messageDTO.getSenderEmail(), equalTo(messages.get(index).getSenderEmail()));
			assertThat(messageDTO.getSubject(), equalTo(messages.get(index).getSubject()));
			assertThat(messageDTO.getContent(), equalTo(messages.get(index).getContent()));
			assertThat(messageDTO.getHostAddress(), equalTo(messages.get(index).getHostAddress()));
			index++;
		}
	}
}
