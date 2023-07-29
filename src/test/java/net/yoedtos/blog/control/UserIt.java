package net.yoedtos.blog.control;

import static net.yoedtos.blog.util.TestConstants.HOST_ADDRESS;
import static net.yoedtos.blog.util.TestConstants.PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.TEST_CONTEXT;
import static net.yoedtos.blog.util.TestConstants.USERNAME_NEW;
import static net.yoedtos.blog.util.TestConstants.USERS_PG_TITLE;
import static net.yoedtos.blog.util.TestConstants.USERS_URI;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import org.openqa.selenium.support.ui.Select;

import net.yoedtos.blog.model.Role;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserIt extends AbstractIT {

	@Before
	public void setup() {
		driver = new FirefoxDriver(getFirefoxOptions());
		driver.get(TEST_CONTEXT + USERS_URI);
	}

	@After
	public void teardown() {
		driver.quit();
	}

	@Test
	public void test1_whenListUsersShouldHave14Elements() {
		assertThat(PG_TITLE, driver.getTitle(), equalTo(USERS_PG_TITLE));
		
		List<WebElement> elements = driver.findElements(By.tagName("td"));
		assertEquals(14, elements.size());
		
		String memberRole = Role.MEMBER.name().toLowerCase();
		Select select = findRoleSelectBox();
		
		assertThat(Long.parseLong(elements.get(7).getText()), equalTo(2L));
		assertFalse(elements.get(8).getText().isEmpty());
		assertThat(elements.get(9).getText(), equalTo(USERNAME_NEW));
		assertThat(elements.get(10).getText(), equalTo(HOST_ADDRESS));
		assertThat(select.getFirstSelectedOption().getText(), equalTo(memberRole));
		assertTrue(elements.get(12).isEnabled());
	}
	
	@Test
	public void test2_whenChangeRoleUserShouldChangeToAdministrator() {
		findRoleSelectBox().selectByValue(Role.ADMINISTRATOR.name());
		
		String adminRole = Role.ADMINISTRATOR.name().toLowerCase();
		Select selectNew = findRoleSelectBox();
		
		assertThat(selectNew.getFirstSelectedOption().getText(), equalTo(adminRole));
	}
	
	@Test
	public void test3_whenDesactiveUserShouldDesactive() {
		findIsActiveCheckBox().click();
		
		assertFalse(findIsActiveCheckBox().isSelected());
	}
	
	@Test
	public void test4_whenDeleteUserShouldHaveOne() {
		driver.findElement(By.id("user-table:1:del-form:udelete")).click();
		
		List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"user-table\"]/tbody"));
	
		assertEquals(1, elements.size());
	}
	
	private WebElement findIsActiveCheckBox() {
		return driver.findElement(By.name("user-table:1:stat-form:stat-ck"));
	}
	
	private Select findRoleSelectBox() {
		return new Select(driver.findElement(By.id("user-table:1:role-form:role-slt")));
	}
}
