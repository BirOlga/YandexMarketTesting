import static helpers.Properties.testsProperties;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import helpers.Screenshoter;

public class BaseTest {
	
	protected WebDriver webDriver;
	
	protected WebDriverWait webDriverWait;
	
	/**
	 * Метод определяет базовые настройки драйвера
	 */
	@BeforeEach
	public void setSettings() {
		System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
		ChromeOptions chromeOptions = new ChromeOptions();
	    chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
	    webDriver = new ChromeDriver(chromeOptions);
		webDriver.manage().window().maximize();
		webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(testsProperties.defaultImplicityTimeout()));
		webDriverWait = new WebDriverWait(webDriver,Duration.ofSeconds(testsProperties.defaultExplicityTimeout()));
	}
	/**
	 * Метод выполняет прекращение работы драйвера и всех связанных окон
	 */
	@AfterEach
	public void closeBrowser() {
		webDriver.quit();
	}
	/**
	 * Метод выполняет настройки скриншотера
	 * 
	 */
	@BeforeAll()
	public static void configureScreenshoterForTests() {
		Screenshoter.configureScreenshoter();
	}

}
