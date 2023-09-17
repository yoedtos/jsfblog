package net.yoedtos.blog.mail;

import static net.yoedtos.blog.util.TestConstants.EMAIL_ONE;
import static net.yoedtos.blog.util.TestConstants.LANGUAGE;
import static net.yoedtos.blog.util.TestConstants.LOCAL_PORT;
import static net.yoedtos.blog.util.TestConstants.LOCAL_SERVER;
import static net.yoedtos.blog.util.TestConstants.NOREPLY_SENDER;
import static net.yoedtos.blog.util.TestConstants.RESET_HTML_MSG;
import static net.yoedtos.blog.util.TestConstants.RESET_SUBJECT;
import static net.yoedtos.blog.util.TestConstants.RESET_TXT_MSG;
import static net.yoedtos.blog.util.TestConstants.SITE_EMAIL;
import static net.yoedtos.blog.util.TestConstants.SITE_NAME;
import static net.yoedtos.blog.util.TestConstants.SMTP_PASSWORD;
import static net.yoedtos.blog.util.TestConstants.SMTP_USER;
import static net.yoedtos.blog.util.TestConstants.TEST_CONTEXT;
import static net.yoedtos.blog.util.TestUtil.createTokenOne;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.RejectException;
import org.subethamail.smtp.TooMuchDataException;
import org.subethamail.smtp.server.SMTPServer;

import net.yoedtos.blog.exception.MailerException;
import net.yoedtos.blog.model.dto.SettingDTO;
import net.yoedtos.blog.model.entity.Token;

public class MailerTest implements MessageHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(MailerTest.class);
	
	private SettingDTO settingDTO;
	private Mailer mailer;
	private MailBody mailBody;
	
	private static SMTPServer server;
	
	@BeforeClass
	public static void start() {
		server = new SMTPServer(new MessageHandlerFactory() {
			
			@Override
			public MessageHandler create(MessageContext ctx) {
				return new MailerTest();
			}
		});
		server.setPort(Integer.parseInt(LOCAL_PORT));
		server.start();
	}
	
	@AfterClass
	public static void stop() {
		server.stop();
	}
	
	@Before
	public void init() {
		String siteAddress = TEST_CONTEXT;
		settingDTO = new SettingDTO.Builder()
				.language(LANGUAGE)
				.siteName(SITE_NAME)
				.siteAddress(siteAddress)
				.siteEmail(SITE_EMAIL)
				.smtpServer(LOCAL_SERVER)
				.smtpPort(LOCAL_PORT)
				.useTLS(false)
				.smtpUser(SMTP_USER)
				.smtpPassword(SMTP_PASSWORD)
				.build();
		
		mailer = new Mailer(settingDTO);
	}
		
	@Test
	public void whenMailerSendResetEmailShouldHaveCorrect() throws MailerException {
		Token token = createTokenOne(new Date(), null);		
		String result = mailer.createResetMail(token).send();
		assertNotNull(result);
	}
	
	@Test
	public void whenMailerTlsSendResetEmailShouldHaveCorrect() throws MailerException {
		settingDTO.setUseTLS(true);
		Token token = createTokenOne(new Date(), null);
		String result = mailer.createResetMail(token).send();
		assertNotNull(result);
	}

	@Override
	public void from(String from) throws RejectException {
		assertThat(from, equalTo(NOREPLY_SENDER));
	}

	@Override
	public void recipient(String recipient) throws RejectException {
		assertThat(recipient, equalTo(EMAIL_ONE));
	}

	@Override
	public void data(InputStream data) throws RejectException, TooMuchDataException, IOException {
		mailBody = parseStreamToData(data);  
		
		assertThat(mailBody.getSubject(), equalTo(RESET_SUBJECT));
		assertThat(mailBody.getTxt(), equalTo(RESET_TXT_MSG));
		assertThat(mailBody.getHtml(), equalTo(RESET_HTML_MSG));
  }

	@Override
	public void done() {}
		
	private MailBody parseStreamToData(InputStream input) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		StringBuilder builder = new StringBuilder();
		
		String subject = null;
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				if(line.contains(Tags.SUBJECT)) {
					subject = line.replace(Tags.SUBJECT, "").trim();
				}
				if(line.startsWith("-") || line.contains(Tags.CONTENT_TRANS) || line.isEmpty()) {
					continue;
				} else if (line.contains(Tags.CONTENT_TYPE)) {
					builder.append(Tags.SEPARATOR);
					continue;
				} else {
					builder.append(line + "\n");
				}
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		String[] content = builder.toString().split(Tags.SEPARATOR);
		return new MailBody(subject, 
				content[Tags.CNT_TEXT].trim(), 
				content[Tags.CNT_HTML].trim());
	}
	
	class MailBody {
		String subject;
		String txt;
		String html;
		
		public MailBody(String subject, String txt, String html) {
			this.subject = subject;
			this.txt = txt;
			this.html = html;
		}

		public String getSubject() {
			return subject;
		}

		public String getTxt() {
			return txt;
		}

		public String getHtml() {
			return html;
		}
	}
	
	class Tags {
		protected final static String SUBJECT = "Subject:";
		protected final static String SEPARATOR = "separator\n";
		protected final static String CONTENT_TRANS = "Content-Transfer-Encoding";
		protected final static String CONTENT_TYPE = "Content-Type:";
		protected final static int CNT_TEXT = 3;
		protected final static int CNT_HTML = 4;
	}
}
