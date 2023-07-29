package net.yoedtos.blog.control;

import static net.yoedtos.blog.util.TestConstants.ERROR_403;
import static net.yoedtos.blog.util.TestConstants.ERROR_404;
import static net.yoedtos.blog.util.TestConstants.INVALID_URI;
import static net.yoedtos.blog.util.TestConstants.TEST_CONTEXT;
import static net.yoedtos.blog.util.TestConstants.UNAVAILABLE_URI;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ErrorHandlerIt extends AbstractIT{
	
	@Before
	public void setup() {
		driver = new FirefoxDriver(getFirefoxOptions());
	}

	@After
	public void teardown() {
		driver.quit();
	}

	@Test
	public void whenAccessUnavailablePageShouldGet404() {
		driver.get(TEST_CONTEXT + UNAVAILABLE_URI);
		String actual = getTextFromH2Tag();
		assertThat(actual, equalTo(ERROR_404));
	}
	
	@Test
	public void whenAccessInvalidPageShouldGet403() {
		driver.get(TEST_CONTEXT + INVALID_URI);
		String actual = getTextFromH2Tag();
		assertThat(actual, equalTo(ERROR_403));
	}

	private String getTextFromH2Tag() {
		return driver.findElements(By.tagName("h2")).get(1).getText();
	}
}
