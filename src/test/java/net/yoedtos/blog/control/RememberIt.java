package net.yoedtos.blog.control;

import static net.yoedtos.blog.control.Session.BADGE;
import static net.yoedtos.blog.control.Session.INDEX;
import static net.yoedtos.blog.util.TestConstants.GREETING;
import static net.yoedtos.blog.util.TestConstants.INDEX_URI;
import static net.yoedtos.blog.util.TestConstants.PASSWORD;
import static net.yoedtos.blog.util.TestConstants.TEST_CONTEXT;
import static net.yoedtos.blog.util.TestConstants.USERNAME_NEW;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.Duration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RememberIt extends AbstractIT {
	
	@Before
	public void setup() {
		driver = new FirefoxDriver(getFirefoxOptions());
		driver.get(TEST_CONTEXT + INDEX_URI);
		login(USERNAME_NEW, PASSWORD, true);
	}
	
	@After
	public void teardown() {
		driver.quit();
	}
	
	@Test
	public void testWhenLoginWithRememberShouldShowUsernameAndReturnCookies() {
		Cookie index = driver.manage().getCookieNamed(INDEX);
		Cookie badge = driver.manage().getCookieNamed(BADGE);
		WebElement button =  getGreetingButton();
		
		assertNotNull(index);
		assertNotNull(badge);
		assertEquals(GREETING, button.getText());
	}
	
	@Test
	public void testWhenLogoutShouldNotShowUsernameAndReturnNullCookies() {
		logout();
		
		Cookie index = driver.manage().getCookieNamed(INDEX);
		Cookie badge = driver.manage().getCookieNamed(BADGE);
		WebElement button =  getGreetingButton();
		
		assertNull(index);
		assertNull(badge);
		assertNull(button);
	}

	private WebElement getGreetingButton() {
		WebElement button;
		try {
			button = driver.findElement(By.xpath("//*[@id=\"drop-menu\"]/button"));
		} catch (NoSuchElementException e) {
			return null;
		}
		return button;
	}
	
	private void logout() {
		new Actions(driver).moveToElement(new WebDriverWait(driver, Duration.ofSeconds(2))
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("drop-menu")))).build().perform();
		new WebDriverWait(driver, Duration.ofSeconds(2))
				.until(ExpectedConditions.elementToBeClickable(By.id("drop-form:blogout"))).click();
	}
}
