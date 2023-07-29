package net.yoedtos.blog.control;

import static net.yoedtos.blog.util.TestConstants.EMAIL_NEW;
import static net.yoedtos.blog.util.TestConstants.FULLNAME_NEW;
import static net.yoedtos.blog.util.TestConstants.LOGIN_PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.MSG_USER_NA;
import static net.yoedtos.blog.util.TestConstants.PASSWORD;
import static net.yoedtos.blog.util.TestConstants.PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.REGISTER_PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.REGISTER_URI;
import static net.yoedtos.blog.util.TestConstants.TEST_CONTEXT;
import static net.yoedtos.blog.util.TestConstants.USERNAME_NEW;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegisterIT extends AbstractIT {
	
	@Before
	public void setup() {
		driver = new FirefoxDriver(getFirefoxOptions());
		driver.get(TEST_CONTEXT + REGISTER_URI);
	}

	@After
	public void teardown() {
		driver.quit();
	}
	
	@Test
	public void test1WhenRegisterSuccessShouldGoToLoginPage() {
		assertThat(PG_TITLE, driver.getTitle(), equalTo(REGISTER_PG_TITLE));
		submitRegisterForm();
		assertThat(PG_TITLE, driver.getTitle(), equalTo(LOGIN_PG_TITLE));
	}

	@Test
	public void test2WhenRegisterTwiceShouldShowMessage() {
		submitRegisterForm();
		String invalid =  driver.findElement(By.id("registerForm:m_username")).getText();
		assertThat(invalid, equalTo(MSG_USER_NA));
	}
	
	private void submitRegisterForm() {
		driver.findElement(By.id("registerForm:fullname")).sendKeys(FULLNAME_NEW);
		driver.findElement(By.id("registerForm:username")).sendKeys(USERNAME_NEW);
		driver.findElement(By.id("registerForm:password")).sendKeys(PASSWORD);
		driver.findElement(By.id("registerForm:confirm")).sendKeys(PASSWORD);
		driver.findElement(By.id("registerForm:email")).sendKeys(EMAIL_NEW);
		
		driver.findElement(By.name("registerForm:rbutton")).click();
	}
}
