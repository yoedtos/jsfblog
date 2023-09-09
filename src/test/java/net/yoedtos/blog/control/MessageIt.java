package net.yoedtos.blog.control;

import static net.yoedtos.blog.util.Constants.ADMIN_USER;
import static net.yoedtos.blog.util.Constants.ADMIN_USER_PASS;
import static net.yoedtos.blog.util.TestConstants.CONTENT_NEW;
import static net.yoedtos.blog.util.TestConstants.EMAIL_NEW;
import static net.yoedtos.blog.util.TestConstants.FULLNAME_NEW;
import static net.yoedtos.blog.util.TestConstants.INDEX_PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.MESSAGE_PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.MESSAGE_URI;
import static net.yoedtos.blog.util.TestConstants.PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.TEST_CONTEXT;
import static net.yoedtos.blog.util.TestConstants.TITLE_NEW;
import static net.yoedtos.blog.util.TestConstants.USER_HOME_PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.USER_HOME_URI;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MessageIt extends AbstractIT {
	
	private By messageTrs = By.xpath("//*[@id=\"messageForm:message-table\"]/tbody/tr");
	private By messageTds = By.xpath("//*[@id=\"messageModal\"]/div/header/table/tbody/tr[2]/td");
	private By btnImg = By.xpath("//*[@id=\"messageForm:message-table\"]/tbody/tr/td[4]/img");
	
	
	public MessageIt() {
		super();
		super.ids = new String[] {"contactForm:sender", "contactForm:email", 
									"contactForm:subject", "contactForm:content"};
		super.data = new String[] {FULLNAME_NEW, EMAIL_NEW, TITLE_NEW, CONTENT_NEW};
	}
	
	@Before
	public void setup() {
		driver = new FirefoxDriver(getFirefoxOptions());
		driver.navigate().to(TEST_CONTEXT + MESSAGE_URI);
	}

	@After
	public void teardown() {
		driver.quit();
	}

	@Test
	public void test1_whenCreateMessageSuccessShouldGoToIndex() {
		assertThat(PG_TITLE, driver.getTitle(), equalTo(MESSAGE_PG_TITLE));
		fillForm();
		
		driver.findElement(By.id("contactForm:send-btn")).click();
		assertThat(PG_TITLE, driver.getTitle(), equalTo(INDEX_PG_TITLE));
	}
	
	@Test
	public void test2_whenListMessageShouldHaveOne() {
		goToAdminUserHomePage();
		assertThat(PG_TITLE, driver.getTitle(), equalTo(USER_HOME_PG_TITLE));
		
		List<WebElement> trs = driver.findElements(messageTrs);
		assertThat(trs.size(), equalTo(1));
		
		List<WebElement> tds = trs.get(0).findElements(By.tagName("td"));
		assertThat(tds.get(1).getText(), equalTo(FULLNAME_NEW));
		assertThat(tds.get(2).getText(), equalTo(TITLE_NEW));
	}

	@Test
	public void test3_whenListThenViewMessageShouldhShow() {
		goToAdminUserHomePage();
		driver.findElement(btnImg).click();
		
		List<WebElement> tds = driver.findElements(messageTds);
		assertThat(tds.size(), equalTo(4));

		WebElement bodyDiv = driver.findElement(By.xpath("//*[@id=\"messageModal\"]/div/div"));
		assertThat(bodyDiv.findElement(By.tagName("h5")).getText(), equalTo(TITLE_NEW));
		assertThat(bodyDiv.findElement(By.tagName("p")).getText(), equalTo(CONTENT_NEW));
	}

	@Test
	public void test4_whenDeleteMessageId1_ShouldHaveZero() {
		goToAdminUserHomePage();
		driver.findElement(By.id("messageForm:message-table:0:mdelete")).click();

		List<WebElement> trs = driver.findElements(messageTrs);
		assertThat(trs.size(), equalTo(0));
	}
	
	private void goToAdminUserHomePage() {
		login(ADMIN_USER, ADMIN_USER_PASS);
		driver.navigate().to(TEST_CONTEXT + USER_HOME_URI);
	}
}
