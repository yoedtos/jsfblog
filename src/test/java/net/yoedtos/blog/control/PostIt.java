package net.yoedtos.blog.control;

import static net.yoedtos.blog.util.TestConstants.CONTENT_ONE;
import static net.yoedtos.blog.util.TestConstants.CONTENT_TWO;
import static net.yoedtos.blog.util.TestConstants.CONTENT_UPDATE;
import static net.yoedtos.blog.util.TestConstants.ERROR_404;
import static net.yoedtos.blog.util.TestConstants.INTRO_ONE;
import static net.yoedtos.blog.util.TestConstants.INTRO_TWO;
import static net.yoedtos.blog.util.TestConstants.INTRO_UPDATE;
import static net.yoedtos.blog.util.TestConstants.META_DESC;
import static net.yoedtos.blog.util.TestConstants.META_KEY;
import static net.yoedtos.blog.util.TestConstants.PASSWORD;
import static net.yoedtos.blog.util.TestConstants.PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.POSTS_PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.POSTS_URI;
import static net.yoedtos.blog.util.TestConstants.POST_PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.POST_URI;
import static net.yoedtos.blog.util.TestConstants.TEST_CONTEXT;
import static net.yoedtos.blog.util.TestConstants.TITLE_NEW;
import static net.yoedtos.blog.util.TestConstants.TITLE_UPDATE;
import static net.yoedtos.blog.util.TestConstants.USERNAME_NEW;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.time.Duration;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.yoedtos.blog.util.Constants;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PostIt extends AbstractIT {

	private By introSrc = By.xpath("//form//div[3]/div//span[2]/span[1]/span[3]/a");
	private By introTxt = By.xpath("//form//div[3]//div/textarea");
	private By mainSrc = By.xpath("//form//div[5]/div//span[1]/span[3]/a[1]");
	private By mainTxt = By.xpath("//form//div[5]//div/textarea");
	private By tableTr = By.xpath("//table/tbody/tr");
	
	private WebDriverWait driveWait;
	
	public PostIt() {
		super();
		super.ids = new String[] {"postForm:title","postForm:metaDesc","postForm:metaKey"};
		super.data = new String[] {TITLE_NEW, META_DESC, META_KEY};
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
	public void test1WhenPostSuccessShouldGoToPostsPage() {
		goToPost(USERNAME_NEW, PASSWORD);
		
		Select langSelect = new Select(driver.findElement(By.id("postForm:category-sel")));
		langSelect.selectByIndex(1);
		
		fillCkEditor(introSrc, introTxt, INTRO_ONE + INTRO_TWO, false);
		fillCkEditor(mainSrc, mainTxt, CONTENT_ONE + CONTENT_TWO, false);
		fillForm();
		
		driver.findElement(By.name("postForm:sbutton")).click();
		assertThat(driver.getCurrentUrl(), equalTo(TEST_CONTEXT + POSTS_URI));
	}

	@Test
	public void test2WhenTryEditSomeElsePostShouldGoTo404Page(){
		goToPost(USERNAME_NEW, PASSWORD);
		driver.navigate().to(TEST_CONTEXT + POST_URI + "?post_id=1");
		assertThat(driver.findElements(By.tagName("h2")).get(1).getText(), equalTo(ERROR_404)); 
	}
	
	@Test
	public void test3WhenUpdateSuccessShouldGoToPostsPage(){
		goToPosts(USERNAME_NEW, PASSWORD);
		driver.findElement(By.id("posts-form:posts-table:0:pedit")).click();
		
		assertThat(PG_TITLE, driver.getTitle(), equalTo(POST_PG_TITLE));
		super.data = new String[] {TITLE_UPDATE, META_DESC, META_KEY};
		
		fillCkEditor(introSrc, introTxt, INTRO_UPDATE + INTRO_ONE, true);
		fillCkEditor(mainSrc, mainTxt, CONTENT_UPDATE + CONTENT_ONE, true);
		
		clearForm();
		fillForm();
		
		driver.findElement(By.name("postForm:ubutton")).click();
		assertThat(driver.getCurrentUrl(), equalTo(TEST_CONTEXT + POSTS_URI));
	}

	@Test
	public void test4WhenFindAllShouldHaveSeven() {
		goToPosts(Constants.ADMIN_USER, Constants.ADMIN_USER_PASS);
		List<WebElement> posts = driver.findElements(tableTr);
		assertThat(posts.size(), equalTo(7));
	}

	@Test
	public void test5WhenFindAllByUserShouldHaveOne(){
		goToPosts(USERNAME_NEW, PASSWORD);
		List<WebElement> posts = driver.findElements(tableTr);
		assertThat(posts.size(), equalTo(1));
		
		List<WebElement> columns = driver.findElements(By.xpath("//table/tbody/tr/td"));
		assertThat(Long.parseLong(columns.get(0).getText()), equalTo(1L));
		assertFalse(columns.get(1).getText().isEmpty());
		assertThat(columns.get(2).getText(), equalTo(TITLE_UPDATE));
	}
	
	@Test
	public void test6WhenDeleteSuccessShouldHaveZero(){
		goToPosts(USERNAME_NEW, PASSWORD);
		driver.findElement(By.id("posts-form:posts-table:0:pdelete")).click();
		List<WebElement> posts = driver.findElements(tableTr);
		assertThat(posts.size(), equalTo(0));
	}
	
	private void goToPost(String username, String password) {
		login(username, password);
		driver.get(TEST_CONTEXT + POST_URI);
		assertThat(PG_TITLE, driver.getTitle(), equalTo(POST_PG_TITLE));
	}
	
	private void goToPosts(String username, String password) {
		login(username, password);
		driver.get(TEST_CONTEXT + POSTS_URI);
		assertThat(PG_TITLE, driver.getTitle(), equalTo(POSTS_PG_TITLE));
	}
	
	private void fillCkEditor(By button, By textArea, String text, boolean clear) {
		driveWait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driveWait.until(ExpectedConditions.elementToBeClickable(button)).click();
		WebElement txtArea = driver.findElement(textArea);
		if (clear)
			txtArea.clear();
		
		txtArea.sendKeys(text); 
	}
}
