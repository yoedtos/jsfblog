package net.yoedtos.blog.control;

import static net.yoedtos.blog.util.TestConstants.LOCAL_PORT;
import static net.yoedtos.blog.util.TestConstants.LOCAL_SERVER;
import static net.yoedtos.blog.util.TestConstants.LOGIN_URI;
import static net.yoedtos.blog.util.TestConstants.PASS_UPDATE;
import static net.yoedtos.blog.util.TestConstants.PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.RESET_EMAIL_FILE;
import static net.yoedtos.blog.util.TestConstants.TEST_CONTEXT;
import static net.yoedtos.blog.util.TestConstants.USERNAME_NEW;
import static net.yoedtos.blog.util.TestConstants.USER_HOME_PG_TITLE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import com.google.common.io.Files;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResetIt extends AbstractIT {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResetIt.class);
	
	private static File emailFile;
	private static Wiser wiser;
	
	public ResetIt() {
		super();
		super.ids = new String[] {"reset:userLogin", "reset:password", "reset:confirm",};
		super.data = new String[] {USERNAME_NEW, PASS_UPDATE, PASS_UPDATE};
	}
	
	@BeforeClass
	public static void startup() {
		wiser = new Wiser(Integer.parseInt(LOCAL_PORT));
		wiser.setHostname(LOCAL_SERVER);
		wiser.start();
	}
	
	@AfterClass
	public static void shutdown() {
		wiser.getMessages().clear();
		wiser.stop();
		
		emailFile.deleteOnExit();
	}
	
	@Before
	public void setup() {
		driver = new FirefoxDriver(getFirefoxOptions());
	}

	@After
	public void teardown() {
		driver.quit();
	}

	@Test
	public void test1_whenSendUsernameShouldInitiateReset() {
		Exception exception = null;
		
		driver.get(TEST_CONTEXT + LOGIN_URI);
		driver.findElement(By.id("lg-reset:passlost")).sendKeys(USERNAME_NEW);
		driver.findElement(By.id("lg-reset:breset_send")).click();
		
		try {
			writeMessage();
		} catch (Exception e) {
			exception = e;
		}
		assertNull(exception);
	}

	@Test
	public void test2_whenClickResetLinkFillUserDataShouldComplete() {
		driver.get("file:///" + emailFile.toString());
		driver.findElement(By.xpath("//p[4]/a")).click();
		
		fillForm();
		driver.findElement(By.id("reset:bchange")).click();
		
		login(USERNAME_NEW, PASS_UPDATE);
		assertThat(PG_TITLE, driver.getTitle(), equalTo(USER_HOME_PG_TITLE));
	}
	
	@SuppressWarnings("deprecation")
	private void writeMessage() throws Exception {
		emailFile = new File(Files.createTempDir(), RESET_EMAIL_FILE);

		List<WiserMessage> messages = wiser.getMessages();
		try {
			messages.get(0).dumpMessage(new PrintStream(emailFile));
		} catch (NullPointerException |
					FileNotFoundException | MessagingException e) {
			LOGGER.error(e.getMessage());
			throw new Exception();
		}
	}
}
