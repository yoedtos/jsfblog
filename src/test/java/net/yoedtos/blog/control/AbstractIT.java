package net.yoedtos.blog.control;

import static net.yoedtos.blog.util.TestConstants.LOGIN_URI;
import static net.yoedtos.blog.util.TestConstants.TEST_CONTEXT;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class AbstractIT {

	private boolean remember;
	protected String[] ids;
	protected String[] data; 
			
	protected WebDriver driver;

	@BeforeClass
	public static void init() {
		 WebDriverManager.firefoxdriver().setup();
	}
	
	@AfterClass
	public static void halt() {
		
	}
	
	protected FirefoxOptions getFirefoxOptions() {
		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(true);
		return options;
	}
	
	protected void login(String username, String password, boolean remember) {
		this.remember = remember;
		login(username, password);
	}
	
	protected void login(String username, String password) {
		driver.get(TEST_CONTEXT + LOGIN_URI);
		driver.findElement(By.id("login:userLogin")).sendKeys(username);
		driver.findElement(By.id("login:userPassword")).sendKeys(password);
		if(remember)
			driver.findElement(By.id("login:remember")).click();
		driver.findElement(By.id("login:blogin")).click();
	}
	
	protected void fillForm() {
		for (int i = 0; i < ids.length; i++) {
			driver.findElement(By.id(ids[i])).sendKeys(data[i]);
		}
	}
	
	protected void clearForm() {
		for (int i = 0; i < ids.length; i++) {
			driver.findElement(By.id(ids[i])).clear();
		}
	}
}
