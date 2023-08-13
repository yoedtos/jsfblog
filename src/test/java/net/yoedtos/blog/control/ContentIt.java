package net.yoedtos.blog.control;

import static net.yoedtos.blog.util.TestConstants.INDEX_PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.INDEX_URI;
import static net.yoedtos.blog.util.TestConstants.PG_META_DESC;
import static net.yoedtos.blog.util.TestConstants.PG_META_KEY;
import static net.yoedtos.blog.util.TestConstants.PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.TEST_CONTEXT;
import static net.yoedtos.blog.util.TestConstants.VIEW_META_DESC;
import static net.yoedtos.blog.util.TestConstants.VIEW_META_KEY;
import static net.yoedtos.blog.util.TestConstants.VIEW_TITLE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ContentIt extends AbstractIT {

	private By frontLinks = By.xpath("//*[@id=\"front-panel\"]/div[1]/a");
	private By forward = By.xpath("//*[@id=\"pager-panel\"]/a[1]");
	private By backward = By.xpath("//*[@id=\"pager-panel\"]/a[2]");
	private By first = By.xpath("//*[@id=\"pager-panel\"]/a[1]");
	private By last = By.xpath("//*[@id=\"pager-panel\"]/a[2]");
	
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
	public void whenGetIndexShouldHave4FrontPost() {
		assertThat(PG_TITLE, driver.getTitle(), equalTo(INDEX_PG_TITLE));
		List<WebElement> elements = driver.findElements(frontLinks);
		assertThat(elements.size(), equalTo(4));
	}
	
	@Test
	public void whenGoNextAndPreviousShouldHave2And4FrontPost() {
		driver.findElement(forward).click();
		List<WebElement> elements = driver.findElements(frontLinks);
		assertThat(elements.size(), equalTo(2));
		
		driver.findElement(backward).click();
		elements = driver.findElements(frontLinks);
		assertThat(elements.size(), equalTo(4));
	}
	
	@Test
	public void whenGoLastAndFirstShouldHave2And4FrontPost() {
		driver.findElement(last).click();
		List<WebElement> elements = driver.findElements(frontLinks);
		assertThat(elements.size(), equalTo(2));
		
		driver.findElement(first).click();
		elements = driver.findElements(frontLinks);
		assertThat(elements.size(), equalTo(4));
	}
	
	@Test
	public void whenGoToFrontPostOneShouldShowView() {
		driver.findElement(By.xpath("//*[@id=\"front-panel\"]/div[1]/a")).click();
		assertThat(PG_TITLE, driver.getTitle(), equalTo(VIEW_TITLE));
		
		WebElement title = driver.findElement(By.id("title-panel"));
		WebElement content = driver.findElement(By.id("content-panel"));
		assertThat(title.getText(), equalTo(VIEW_TITLE));
		assertFalse(content.getText().isEmpty());
		
		WebElement metaDesc = driver.findElement(By.xpath("//meta[@name='description']"));
		WebElement metaKey = driver.findElement(By.xpath("//meta[@name='keywords']"));
		assertThat(PG_META_DESC, metaDesc.getAttribute("content"), equalTo(VIEW_META_DESC));
		assertThat(PG_META_KEY ,metaKey.getAttribute("content"), equalTo(VIEW_META_KEY));
	}
}
