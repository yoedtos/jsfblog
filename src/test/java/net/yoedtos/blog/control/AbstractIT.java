package net.yoedtos.blog.control;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class AbstractIT {

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
}
