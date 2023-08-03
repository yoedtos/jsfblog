package net.yoedtos.blog.control;

import static net.yoedtos.blog.util.TestConstants.EMAIL_UPDATE;
import static net.yoedtos.blog.util.TestConstants.FULL_UPDATE;
import static net.yoedtos.blog.util.TestConstants.PASSWORD;
import static net.yoedtos.blog.util.TestConstants.PASS_UPDATE;
import static net.yoedtos.blog.util.TestConstants.PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.PROFILE_PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.PROFILE_URI;
import static net.yoedtos.blog.util.TestConstants.TEST_CONTEXT;
import static net.yoedtos.blog.util.TestConstants.USERNAME_NEW;
import static net.yoedtos.blog.util.TestConstants.USER_HOME_PG_TITLE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProfileIt extends AbstractIT {
	
	public ProfileIt() {
		super();
		super.ids = new String[] {"profile:fullname", "profile:password", "profile:confirm", "profile:email"};
		super.data = new String[] {FULL_UPDATE, PASS_UPDATE, PASS_UPDATE, EMAIL_UPDATE};
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
	public void test1WhenUpdateProfileSuccessShouldGoToHomeUserPage() {
		login(USERNAME_NEW, PASSWORD);
		driver.get(TEST_CONTEXT + PROFILE_URI);
		assertThat(PG_TITLE, driver.getTitle(), equalTo(PROFILE_PG_TITLE));
		
		clearForm();
		fillForm();
		driver.findElement(By.name("profile:pubutton")).click();
		assertThat(PG_TITLE, driver.getTitle(), equalTo(USER_HOME_PG_TITLE));
	}
	
	@Test
	public void test2checkUpdatedProfileFieldsSuccess() {
		login(USERNAME_NEW, PASS_UPDATE);
		driver.get(TEST_CONTEXT + PROFILE_URI);
		assertThat(PG_TITLE, driver.getTitle(), equalTo(PROFILE_PG_TITLE));
		
		for (int i = 0; i < ids.length; i++) {
			if(i > 0 && i < 3) {
				String actual = driver.findElement(By.id(ids[i])).getAttribute("value");
				assertEquals(ids[i], "", actual);
				continue;
			}
			String actual = driver.findElement(By.id(ids[i])).getAttribute("value");
			assertThat(ids[i], actual, equalTo(data[i]));
		}
	}
}
