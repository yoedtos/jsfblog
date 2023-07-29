package net.yoedtos.blog.control;

import static net.yoedtos.blog.util.TestConstants.CATEGORY_ONE;
import static net.yoedtos.blog.util.TestConstants.CATEGORY_ONE_ID;
import static net.yoedtos.blog.util.TestConstants.CATEGORY_PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.CATEGORY_URI;
import static net.yoedtos.blog.util.TestConstants.PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.TEST_CONTEXT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CategoryIT extends AbstractIT {

	@Before
	public void setup() {
		driver = new FirefoxDriver(getFirefoxOptions());
		driver.get(TEST_CONTEXT + CATEGORY_URI);
	}

	@After
	public void teardown() {
		driver.quit();
	}

	@Test
	public void test1_whenAddCategoryShouldHaveOne() {
		assertThat(PG_TITLE, driver.getTitle(), equalTo(CATEGORY_PG_TITLE));
		
		driver.findElement(By.id("cat-form:category")).sendKeys(CATEGORY_ONE);
		driver.findElement(By.id("cat-form:ctadd")).click();
		
		List<WebElement> elements = driver.findElements(By.tagName("td"));
		
		assertThat(Long.parseLong(elements.get(0).getText()), equalTo(CATEGORY_ONE_ID));
		assertThat(elements.get(1).getText(), equalTo(CATEGORY_ONE));
	}
	
	@Test
	public void test2_whenDeleteCategoryId1_ShouldHaveZero() {
		driver.findElement(By.id("cat-form:cat-table:0:udelete")).click();
		
		List<WebElement> elements = driver.findElements(By.tagName("td"));
		
		assertTrue(elements.get(0).getText().isEmpty());
		assertTrue(elements.get(1).getText().isEmpty());
	}
}
