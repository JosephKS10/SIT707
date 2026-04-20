package sit707_week2;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils; // Make sure you have Apache Commons IO in your pom.xml, or use org.openqa.selenium.io.FileHandler
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;

/**
 * This class demonstrates Selenium locator APIs to identify HTML elements.
 * * Details in Selenium documentation https://www.selenium.dev/documentation/webdriver/elements/locators/
 * * @author Ahsan Habib / Updated by Joseph
 */
public class SeleniumOperations {

	public static void sleep(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void officeworks_registration_page(String url) {
		// Step 1: Use WebDriverManager to automatically setup the ChromeDriver
		WebDriverManager.chromedriver().setup();
		
		// Step 2: Open up a chromium browser.
		System.out.println("Fire up chrome browser.");
		WebDriver driver = new ChromeDriver();
		
		System.out.println("Driver info: " + driver);
		
		sleep(2);
	
		// Load a webpage in chromium browser.
		driver.get(url);
		
		/*
		 * How to identify a HTML input field -
		 * Step 1: Inspect the webpage, 
		 * Step 2: locate the input field, 
		 * Step 3: Find out how to identify it, by id/name/...
		 */
		
		// Find first input field which is firstname
		WebElement firstname = driver.findElement(By.id("firstname"));
		System.out.println("Found element: " + firstname);
		// Send first name
		firstname.sendKeys("Joseph");
		
		WebElement lastname = driver.findElement(By.id("lastname"));
		System.out.println("Found element: " + lastname);
		// Send last name
		lastname.sendKeys("Saji");		
		
		WebElement phoneNumber = driver.findElement(By.id("phoneNumber"));
		System.out.println("Found element: " + phoneNumber);
		// Send phone number
		phoneNumber.sendKeys("0487123234");	
		
		WebElement email = driver.findElement(By.id("email"));
		System.out.println("Found element: " + email);
		// Send email
		email.sendKeys("joseph@gmail.com");	
		
		// Intentionally fail the password requirement to trigger the error state
		WebElement password = driver.findElement(By.id("password"));
		System.out.println("Found element: " + password);
		password.sendKeys("123");
		
		/*
		 * Identify button 'Create account' and click to submit using Selenium API.
		 */
		WebElement createAccountBtn = driver.findElement(By.xpath("//button[contains(text(), 'Create account')]"));
		createAccountBtn.click();
		
		sleep(2); // Wait for the error messages to render
		
		// Take Screenshot
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File("./officeworks_error_screenshot.png"));
			System.out.println("Screenshot saved successfully.");
		} catch (IOException e) {
			System.out.println("Failed to save screenshot.");
			e.printStackTrace();
		}
		
		// close chrome driver
		driver.close();	
	}
	
	public static void alternative_registration_page(String url) {
		// Step 1: Setup WebDriverManager
		WebDriverManager.chromedriver().setup();
		
		// Step 2: Open browser and maximize (Maximizing prevents responsive mobile-menus from hiding your buttons!)
		System.out.println("Firing up chrome browser for alternative site.");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize(); 
		
		sleep(2);
		driver.get(url);
		
		System.out.println("Testing alternative site: " + url);
		
		/*
		 * Unlike Officeworks, many sites don't use clean 'id' tags. 
		 * Here we practice using By.name() and By.xpath() for variety.
		 */
		
		// 2. Locate and fill Email
		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys("joseph@gmail.com");	
		
		// 2. Locate and fill First Name (Check if the site uses 'firstName', 'first-name', or 'fname')
		WebElement firstname = driver.findElement(By.name("username"));
		firstname.sendKeys("Joseph");
		
		// 3. Intentionally fail the password requirement
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("12"); // Way too short, should trigger a red error
		
		/*
		 * 4. Locate Submit Button. 
		 * If there is no ID, finding a button by its type or text using XPath is a great fallback.
		 */
		WebElement submitBtn = driver.findElement(By.id("create-account"));
		// If the above doesn't work, try finding it by text: By.xpath("//button[contains(text(), 'Sign up')]")
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", submitBtn);
		
		sleep(3); // Wait a bit longer for the site's validation logic to catch the bad password
		
		// 6. Take Screenshot
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			// Saving this one with a different name so it doesn't overwrite your Officeworks image
			org.openqa.selenium.io.FileHandler.copy(scrFile, new File("./alternative_error_screenshot.png"));
			System.out.println("Alternative screenshot saved successfully.");
		} catch (IOException e) {
			System.out.println("Failed to save alternative screenshot.");
			e.printStackTrace();
		}
		
		// close chrome driver
		driver.close();	
	}
	
	
}