package net.yoedtos.blog.control;

import static net.yoedtos.blog.util.Constants.ADMIN_USER;
import static net.yoedtos.blog.util.Constants.ADMIN_USER_PASS;
import static net.yoedtos.blog.util.TestConstants.*;
import static net.yoedtos.blog.util.TestConstants.LOGIN_PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.LOGIN_URI;
import static net.yoedtos.blog.util.TestConstants.PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.TEST_CONTEXT;
import static net.yoedtos.blog.util.TestConstants.USER_HOME_PG_TITLE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.time.Duration;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthIt extends AbstractIT {
	
	@Before
	public void setup() {
		driver = new FirefoxDriver(getFirefoxOptions());
		driver.get(TEST_CONTEXT + LOGIN_URI);
	}

	@After
	public void teardown() {
		driver.quit();
	}
	
	@Test
	public void test1WhenLoginSuccessShouldGoToHomePage() {
		assertThat(PG_TITLE, driver.getTitle(), equalTo(LOGIN_PG_TITLE));
		submitLoginForm(By.id("login:userLogin"), By.id("login:userPassword"), By.id("login:blogin"));
		String welcome = driver.findElements(By.tagName("h3")).get(0).getText();
		
		assertThat(PG_TITLE, driver.getTitle(), equalTo(USER_HOME_PG_TITLE));
		assertThat(welcome, equalTo("Welcome " + ADMIN_USER +"!"));
	}
	
	@Test
	public void test2WhenLogoutSuccessShouldGoToIndexPage() {
		submitLoginForm(By.id("login:userLogin"), By.id("login:userPassword"), By.id("login:blogin"));
		
		new Actions(driver).moveToElement(new WebDriverWait(driver, Duration.ofSeconds(2))
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("drop-menu")))).build().perform();
		new WebDriverWait(driver, Duration.ofSeconds(2))
				.until(ExpectedConditions.elementToBeClickable(By.id("drop-form:blogout"))).click();

		assertThat(PG_TITLE, driver.getCurrentUrl(), equalTo(TEST_CONTEXT + INDEX_URI));
	}
	
	@Test
	public void test3WhenLoginBoxSuccessShouldGoToHomePage() {
		driver.findElement(By.name("lg-box-login")).click();
		submitLoginForm(By.id("lg-box:userLogin"), By.id("lg-box:userPassword"), By.id("lg-box:blogin"));
		
		assertThat(PG_TITLE, driver.getTitle(), equalTo(USER_HOME_PG_TITLE));
	}
	
	private void submitLoginForm(By login, By password, By button) {
		driver.findElement(login).sendKeys(ADMIN_USER);
		driver.findElement(password).sendKeys(ADMIN_USER_PASS);	
		driver.findElement(button).click();
	}
}
