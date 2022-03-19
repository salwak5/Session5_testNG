package variousconcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class crmtest {
	String browser;
	//String url;
	WebDriver driver;
	By USERNAME_LOCATOR = By.xpath("//input[@id='username']");
	By PASSWORD_LOCATOR = By.xpath("//input[@id='password']");
	By SIGNIN_BUTTON_LOCATOR = By.xpath("//button[@name='login' and @type='submit']");
	By DASHBOARD_HEADER_LOCATOR = By.xpath("//h2[contains(text(),dashBoard)]");
	//By ORDER_MENU_LOCATOR = By.xpath("//*[@id=\"side-menu\"]/li[7]/a");
	//By ADD_ORDER_MENU_LOCATOR = By.xpath("//*[@id=\"side-menu\"]/li[7]/ul/li[2]/a");
	By CUSTOMER_MENU_LOCATOR = By.xpath("//*[@id=\"side-menu\"]/li[3]/a");
	By ADD_CUSTOMER_MENU_LOCATOR = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
	By ADDCONTACT_HEADER_LOCATOR = By.xpath("//*[@id=\"page-wrapper\"]/div[3]/div[1]/div/div/div/div[1]/h5");
	By FULL_NAME_LOCATOR = By.xpath("//*[@id=\"account\"]");
	By COMPANY_NAME_LOCATOR = By.xpath("//Select[@id=\"cid\"]");
	By EMAIL_LOCATOR = By.xpath("//input[@id='email']");
	By PHONE_NUMBER_LOCATOR = By.xpath("//input[@id='phone']");
	By ADDRESS_LOCATOR = By.xpath("//input[@id='address']");
	By COUNTRY_LOCATOR = By.xpath("//select[@id=\"country\"]");
	By SAVE_BUTTON_LOCATOR = By.xpath("//*[@id=\"submit\"]");
	
	
	
	@BeforeTest
	public void readConfig() {
		// FileReader //Scanner //InputStream //BufferedReader

		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used is "+ browser);
			//url = prop.getProperty("url");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@BeforeMethod
	public void init() {
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
			driver = new ChromeDriver();

		} else if (browser.equalsIgnoreCase("firefox")) {
			// launching firefox
			System.setProperty("webdriver.gecko.driver", "driver\\geckodriver.exe");
			driver = new FirefoxDriver();

		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		// driver.get("https://www.cnn.com/?refresh=1");
		driver.get("https://techfios.com/billing/?ng=dashboard/");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

	}

//	@Test(priority = 1)
	public void logintest() {
		

		driver.findElement(USERNAME_LOCATOR).sendKeys("demo@techfios.com");
		driver.findElement(PASSWORD_LOCATOR).sendKeys("abc123");
		driver.findElement(SIGNIN_BUTTON_LOCATOR).click();

		String dashboardheadertext = driver.findElement(DASHBOARD_HEADER_LOCATOR).getText();
		System.out.println(dashboardheadertext);
		Assert.assertEquals(dashboardheadertext, "Dashboard", "wrong page!!");
		
	}
	@Test(priority = 2)
	
	public void addcustomerTest() {
	logintest();
		driver.findElement(CUSTOMER_MENU_LOCATOR).click();
		driver.findElement(ADD_CUSTOMER_MENU_LOCATOR).click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ADDCONTACT_HEADER_LOCATOR));
		Assert.assertEquals(driver.findElement(ADDCONTACT_HEADER_LOCATOR).getText(),"Add Contact","wrong page");
		
	generateRandom(9999);
		driver.findElement(FULL_NAME_LOCATOR).sendKeys("Shah Rukh Khan" + generateRandom(2));
		
		SelectFromDropDown(COMPANY_NAME_LOCATOR,"ATT");
	
		driver.findElement(EMAIL_LOCATOR).sendKeys(generateRandom(2) +"demo@techfios.com");
      SelectFromDropDown(COUNTRY_LOCATOR, "Virgin Islands, British");
		
      driver.findElement(ADDRESS_LOCATOR).sendKeys("1234 A Dr");
      driver.findElement(PHONE_NUMBER_LOCATOR).sendKeys(generateRandom(3) + "456789065");
      driver.findElement(SAVE_BUTTON_LOCATOR).click();
	
	}

	public void SelectFromDropDown(By Locator, String visibletext) {
		Select sel = new Select(driver.findElement(Locator));
		sel.selectByVisibleText(visibletext);
		// TODO Auto-generated method stub
		
	}

	

	public int generateRandom(int boundaryNum) {
		Random rnd = new Random();
		int generatednum =	rnd.nextInt(boundaryNum);
		return generatednum;
	
		
	}

	//@AfterMethod
	//public void teardown() {
		//driver.close();
		//driver.quit();
	//}

}
