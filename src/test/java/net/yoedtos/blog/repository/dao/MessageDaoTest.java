package net.yoedtos.blog.repository.dao;

import static net.yoedtos.blog.util.TestConstants.CONTENT_NEW;
import static net.yoedtos.blog.util.TestConstants.CREATE_ONE;
import static net.yoedtos.blog.util.TestConstants.CREATE_TWO;
import static net.yoedtos.blog.util.TestConstants.EMAIL_NEW;
import static net.yoedtos.blog.util.TestConstants.FULLNAME_NEW;
import static net.yoedtos.blog.util.TestConstants.HOST_ADDRESS;
import static net.yoedtos.blog.util.TestConstants.MESSAGE_NEW_ID;
import static net.yoedtos.blog.util.TestConstants.MESSAGE_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.MESSAGE_TWO_ID;
import static net.yoedtos.blog.util.TestConstants.TITLE_NEW;
import static net.yoedtos.blog.util.TestUtil.createDate;
import static net.yoedtos.blog.util.TestUtil.createMessageOne;
import static net.yoedtos.blog.util.TestUtil.createMessageTwo;
import static org.hamcrest.CoreMatchers.equalTo;
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
import net.yoedtos.blog.model.entity.Message;

public class MessageDaoTest extends AbstractDaoTest {
	
	private MessageDao messageDao;
	private Message messageOne;
	private Message messageTwo;
	private Date createOne;
	
	public MessageDaoTest() throws DatabaseUnitException, SQLException {
		super();
		messageDao = RepositoryFactory.create(MessageDao.class);
		createOne = createDate(CREATE_ONE);
		messageOne = createMessageOne(createOne, MESSAGE_ONE_ID);
		messageTwo = createMessageTwo(createDate(CREATE_TWO), MESSAGE_TWO_ID);
	}

	@Before
	public void init() throws DatabaseUnitException, SQLException {
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
	}
	
	@Test
	public void whenFindAllShouldReturnTwoMessages() throws DaoException {
		List<Message> messages = messageDao.findAll();
		assertThat(messages.size(), equalTo(2));
		assertThat(messages, hasItems(new Message[] {messageOne, messageTwo}));
	}
		
	@Test
	public void whenRemoveMessageOneThenShouldNotExist() throws DaoException {
		messageDao.remove(MESSAGE_ONE_ID);
		Message messageDb = messageDao.findById(MESSAGE_ONE_ID);
		assertNull(messageDb);
	}
	
	@Test
	public void whenAddMessageShouldHaveOne() throws DaoException {
		Message message = new Message.Builder()
				.createAt(createOne)
				.senderName(FULLNAME_NEW)
				.senderEmail(EMAIL_NEW)
				.subject(TITLE_NEW)
				.content(CONTENT_NEW)
				.hostAddress(HOST_ADDRESS)
				.build();
		
		messageDao.persist(message);
		
		Message messageDb = messageDao.findById(MESSAGE_NEW_ID);
		assertThat(messageDb, equalTo(message));
	}
}
