package net.yoedtos.blog.control;

import static net.yoedtos.blog.util.Constants.ADMIN_USER;
import static net.yoedtos.blog.util.Constants.ADMIN_USER_PASS;
import static net.yoedtos.blog.util.TestConstants.*;
import static net.yoedtos.blog.util.TestUtil.*;
import static org.hamcrest.CoreMatchers.containsString;
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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MediaIt extends AbstractIT {
	
	private By uploadMenu = By.xpath("//div[2]/div[2]//div/a[1]");
	private By listMenu = By.xpath("//div[2]/div[2]//div/a[2]");
	
	@Before
	public void setup() {
		driver = new FirefoxDriver(getFirefoxOptions());
		login(ADMIN_USER, ADMIN_USER_PASS);
	}

	@After
	public void teardown() {
		driver.quit();
	}
	
	@Test
	public void test1WhenMediaUploadSuccessShouldGoMediasPage() {
		activeMenu(uploadMenu);

		driver.findElement(By.id("media-form:fdescription")).sendKeys(MEDIA_IMG_DESC);
		driver.findElement(By.id("media-form:upload")).sendKeys(loadMediaFile(MEDIA_IMG));
		changeTypeSelect();
		
		driver.findElement(By.id("media-form:sbutton")).click();

		assertThat(PG_TITLE, driver.getTitle(), equalTo(MEDIAS_PG_TITLE));
	}
	
	@Test
	public void test2WhenListSearchSuccessShouldHaveOneMedia() {
		activeMenu(listMenu);
		
		List<WebElement> mediaTds = driver.findElements(By.xpath("//form/table/tbody/tr/td"));
		assertThat(mediaTds.size(), equalTo(6));
		
		assertThat(Long.parseLong(mediaTds.get(0).getText()), equalTo(1L));
		assertFalse(mediaTds.get(1).getText().isEmpty());
		assertThat(mediaTds.get(2).getText(), equalTo(MEDIA_IMG_DESC));
		String link = mediaTds.get(4).findElement(By.tagName("a")).getAttribute("href");
		assertThat(link, equalTo(TEST_CONTEXT + MEDIA_IMG_SURN));
	}
	
	@Test
	public void test3WhenRequestMediaURLShouldShowMedia() {
		driver.get(TEST_CONTEXT + MEDIA_IMG_SURN);
		WebElement image = driver.findElement(By.tagName("img"));
		
		assertThat(image.getAttribute("src"), equalTo(TEST_CONTEXT + MEDIA_IMG_SURN));
		assertThat(PG_TITLE, driver.getTitle(),  containsString(MEDIA_IMG));
	}
	
	@Test
	public void test4WhenDeleteMediaShouldHaveZeroMedia() {
		driver.get(TEST_CONTEXT + MEDIAS_URI);
		
		driver.findElement(By.id("medias-form:medias-table:0:mdelete")).click();
		
		List<WebElement> mediaTds = driver.findElements(By.xpath("//form/table/tbody/tr/td"));
		assertThat(mediaTds.size(), equalTo(0));
		assertThat(PG_TITLE, driver.getTitle(), equalTo(MEDIAS_PG_TITLE));
	}
	
	private void activeMenu(By menuItem) {
		new Actions(driver).moveToElement(new WebDriverWait(driver, Duration.ofSeconds(2))
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("media-menu")))).build().perform();
		new WebDriverWait(driver, Duration.ofSeconds(2))
				.until(ExpectedConditions.elementToBeClickable(menuItem)).click();
	}
	
	private void changeTypeSelect() {
		Select langSelect = new Select(driver.findElement(By.id("media-form:selectType")));
		langSelect.selectByIndex(2);
	}
}
