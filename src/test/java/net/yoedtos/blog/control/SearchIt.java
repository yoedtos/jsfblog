package net.yoedtos.blog.control;

import static net.yoedtos.blog.util.TestConstants.INDEX_PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.INDEX_URI;
import static net.yoedtos.blog.util.TestConstants.PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.RESULT_DESC;
import static net.yoedtos.blog.util.TestConstants.RESULT_LINK;
import static net.yoedtos.blog.util.TestConstants.RESULT_TITLE;
import static net.yoedtos.blog.util.TestConstants.SEARCH_META;
import static net.yoedtos.blog.util.TestConstants.TEST_CONTEXT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SearchIt extends AbstractIT {
	
	private By founds = By.xpath("//*[@id=\"result\"]");
	private By p = By.tagName("p");
	private By a = By.tagName("a");

	@Before
	public void setup() {
		driver = new FirefoxDriver(getFirefoxOptions());
		driver.get(TEST_CONTEXT + INDEX_URI);
	}

	@After
	public void teardown() {
		driver.quit();
	}
	
	@Test
	public void whenSearchMetaWordShouldShowOneResult() {
		assertThat(PG_TITLE, driver.getTitle(), equalTo(INDEX_PG_TITLE));
		driver.findElement(By.id("search-form:keyword")).sendKeys(SEARCH_META);
		driver.findElement(By.name("search-form:search-btn")).click();
		List<WebElement> results = driver.findElements(founds);
		assertThat(results.size(), equalTo(1));
		
		WebElement div = results.get(0); 
		assertThat(div.findElement(p).getText(), equalTo(RESULT_DESC));
		assertThat(div.findElement(a).getText(), equalTo(RESULT_TITLE));
		assertThat(div.findElement(a).getAttribute("href"), equalTo(RESULT_LINK));
	}
}
