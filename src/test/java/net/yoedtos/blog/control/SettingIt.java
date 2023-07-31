package net.yoedtos.blog.control;

import static net.yoedtos.blog.util.TestConstants.ADMIN_HOME_PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.SETTING_PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.SETTING_PG_TITLE_PT;
import static net.yoedtos.blog.util.TestConstants.SETTING_URI;
import static net.yoedtos.blog.util.TestConstants.SITE_ADDRESS;
import static net.yoedtos.blog.util.TestConstants.SITE_EMAIL;
import static net.yoedtos.blog.util.TestConstants.SITE_META_DESC;
import static net.yoedtos.blog.util.TestConstants.SITE_META_KEY;
import static net.yoedtos.blog.util.TestConstants.SITE_NAME;
import static net.yoedtos.blog.util.TestConstants.SMTP_PASSWORD;
import static net.yoedtos.blog.util.TestConstants.SMTP_PORT;
import static net.yoedtos.blog.util.TestConstants.SMTP_SERVER;
import static net.yoedtos.blog.util.TestConstants.SMTP_USER;
import static net.yoedtos.blog.util.TestConstants.TEST_CONTEXT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SettingIt extends AbstractIT {

	private String[] ids = new String[] {"set-form:sitename", "set-form:siteaddress", "set-form:siteemail",
			"set-form:usetls", "set-form:smtpserver", "set-form:smtpport", "set-form:smtpuser",
			"set-form:smtppassword", "set-form:sitemetadesc", "set-form:sitemetakey"};
	
	private String[] data = new String[] { SITE_NAME, SITE_ADDRESS, SITE_EMAIL, String.valueOf(true), SMTP_SERVER,
			SMTP_PORT, SMTP_USER, SMTP_PASSWORD, SITE_META_DESC, SITE_META_KEY };
	
	private By inputUseTLS = By.id("set-form:usetls_input");
	
	@Before
	public void setup() {
		driver = new FirefoxDriver(getFirefoxOptions());
		driver.get(TEST_CONTEXT + SETTING_URI);
	}

	@After
	public void teardown() {
		driver.quit();	
	}
	
	@Test
	public void test1WhenSaveSettingShouldSaveCorrect() {
		assertThat(PG_TITLE, driver.getTitle(), equalTo(SETTING_PG_TITLE));
		clearFields();
		
		for (int i = 0; i < ids.length; i++) {
			if (i == 3) {
				driver.findElement(By.id(ids[i])).click();
				continue;
			}
			driver.findElement(By.id(ids[i])).sendKeys(data[i]);
		}
		
		driver.findElement(By.id("set-form:save-btn")).click();
		assertThat(PG_TITLE, driver.getTitle(), equalTo(ADMIN_HOME_PG_TITLE));
	}
	
	@Test
	public void test2WhenGetSettingShouldShowCorrectSetting() {
		for (int i = 0; i < ids.length; i++) {
			if (i == 3) {
				assertTrue(driver.findElement(inputUseTLS).isSelected());
				continue;
			}
			String actual = driver.findElement(By.id(ids[i])).getAttribute("value");
			assertThat(actual, equalTo(data[i]));
		}
	}
	
	@Test
	public void test3WhenChangeLanguageShouldChangeCorrect() {
		Select langSelect = new Select(driver.findElement(By.id("lang-form:lang-sel")));
		langSelect.selectByIndex(1);
		assertThat(PG_TITLE, driver.getTitle(), equalTo(SETTING_PG_TITLE_PT));
	}
	
	private void clearFields() {
		for (int i = 0; i < ids.length; i++) {
			if (i == 3) {
				disableUseTls(i);
				continue;
			}
			driver.findElement(By.id(ids[i])).clear();
		}
	}

	private void disableUseTls(int index) {
		WebElement useTls = driver.findElement(inputUseTLS);
		if (useTls.isSelected()) {
			driver.findElement(By.id(ids[index])).click();
		}
	}
}
