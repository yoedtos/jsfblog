package net.yoedtos.blog.control;

import static net.yoedtos.blog.util.Constants.ADMIN_USER;
import static net.yoedtos.blog.util.Constants.ADMIN_USER_PASS;
import static net.yoedtos.blog.util.TestConstants.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FiltersIt extends AbstractIT {
	
	@Before
	public void setup() {
		driver = new FirefoxDriver(getFirefoxOptions());
	}

	@After
	public void teardown() {
		driver.quit();
	}
	
	@Test
	public void testWhenNotLoggedInShouldGoLoginPage() {
		driver.get(TEST_CONTEXT + USER_HOME_URI);
		assertThat(PG_TITLE, driver.getTitle(), equalTo(LOGIN_PG_TITLE));
		
		driver.get(TEST_CONTEXT + USER_HOME_URI);
		assertThat(PG_TITLE, driver.getTitle(), equalTo(LOGIN_PG_TITLE));
	}
	
	@Test
	public void testWhenLoggedInAsAdminShouldGoAdminHomePage() {
		login(ADMIN_USER, ADMIN_USER_PASS);
		driver.get(TEST_CONTEXT + ADMIN_HOME_URI);
		
		assertThat(PG_TITLE, driver.getTitle(), equalTo(ADMIN_HOME_PG_TITLE));
	}
	
	@Test
	public void testWhenLoggedInAsMemberShouldGoUserHomePage() {
		login(USERNAME_NEW, PASSWORD);
		driver.get(TEST_CONTEXT + USER_HOME_URI);
		
		assertThat(PG_TITLE, driver.getTitle(), equalTo(USER_HOME_PG_TITLE));
	}
	
	@Test
	public void testWhenLoggedInAsMemberGoToAdminHomeShouldGoUserHomePage() {
		login(USERNAME_NEW, PASSWORD);
		driver.get(TEST_CONTEXT + ADMIN_HOME_URI);
		
		assertThat(PG_TITLE, driver.getTitle(), equalTo(USER_HOME_PG_TITLE));
	}
}
