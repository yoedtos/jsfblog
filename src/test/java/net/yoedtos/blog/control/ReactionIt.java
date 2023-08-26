package net.yoedtos.blog.control;

import static net.yoedtos.blog.util.Constants.ADMIN_USER;
import static net.yoedtos.blog.util.Constants.ADMIN_USER_PASS;
import static net.yoedtos.blog.util.TestConstants.AUTHOR_ONE;
import static net.yoedtos.blog.util.TestConstants.COMMENT_ONE;
import static net.yoedtos.blog.util.TestConstants.EMAIL_ONE;
import static net.yoedtos.blog.util.TestConstants.PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.REPLY_ONE;
import static net.yoedtos.blog.util.TestConstants.TEST_CONTEXT;
import static net.yoedtos.blog.util.TestConstants.VIEW_ONE_URI;
import static net.yoedtos.blog.util.TestConstants.VIEW_TITLE;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.time.Duration;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReactionIt extends AbstractIT {
	private By commentSrc = By.xpath("//*[@id=\"cke_comment:comment\"]/div//span[1]/span[3]/a");
	private By commentTxt = By.xpath("//*[@id=\"cke_comment:comment\"]//div/textarea");
	private By replySrc = By.xpath("//*[@id=\"cke_reply:reply\"]/div//span[1]/span[3]/a");
	private By replyTxt = By.xpath("//*[@id=\"cke_reply:reply\"]//div/textarea");
	private By commentTds = By.xpath("//*[@id=\"comments\"]/table[1]//td");
	private By replyTds = By.xpath("//*[@id=\"comments\"]/table[2]//td");
	
	private WebDriverWait driveWait;
	
	public ReactionIt() {
		super();
		super.ids = new String[] {"comment:author","comment:email"};
		super.data = new String[] {AUTHOR_ONE, EMAIL_ONE};
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
	public void test1WhenCommentSuccessShouldShowComment() {
		goToViewOne(false);
		driver.findElement(By.id("commentDialog")).click();
		
		fillCkEditor(commentSrc, commentTxt, COMMENT_ONE);
		fillForm();
		
		driver.findElement(By.id("comment:csave")).click();
		List<WebElement> tdItems = driver.findElements(commentTds);
		assertThat(tdItems.get(0).getText(), equalTo(AUTHOR_ONE));
		assertThat(tdItems.get(1).getText(), containsString(COMMENT_ONE));
	}
	
	@Test
	public void test2WhenReplySuccessShouldShowReply() {
		goToViewOne(true);
		driver.findElement(By.id("ctable:0:replyDialog")).click();
		
		fillCkEditor(replySrc, replyTxt, REPLY_ONE);
		
		driver.findElement(By.id("reply:rsave")).click();
		
		List<WebElement> tdItems = driver.findElements(replyTds);
		assertThat(tdItems.get(0).getText(),  containsString(REPLY_ONE));
		assertThat(tdItems.get(1).getText(),  equalTo(ADMIN_USER));
	}
	
	private void goToViewOne(boolean logged) {
		if (logged) login(ADMIN_USER, ADMIN_USER_PASS);
		driver.get(TEST_CONTEXT + VIEW_ONE_URI);
		assertThat(PG_TITLE, driver.getTitle(), equalTo(VIEW_TITLE));
	}
	
	private void fillCkEditor(By button, By textArea, String text) {
		driveWait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driveWait.until(ExpectedConditions.elementToBeClickable(button)).click();
		driver.findElement(textArea).sendKeys(text);
	}
}
