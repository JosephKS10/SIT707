package web.functional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;   

/**
 * Selenium functional tests for the SIT707 8.1P STEM game.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestSeleniumStemGame {

	private static final String BASE_URL = "http://127.0.0.1:8080";
	private static final String VALID_USER = "ahsan";
	private static final String VALID_PASS = "ahsan_pass";

	private WebDriver driver;
	private WebDriverWait wait;

	@Before
	public void setUp() {
		WebDriverManager.chromedriver().setup();     

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless=new", "--disable-gpu", "--no-sandbox");
		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver, 5);
	}

	@After
	public void tearDown() {
		if (driver != null) driver.quit();
	}

	// ---------- helpers (page-object lite) ----------

	private void doLogin(String user, String pass) {
	    driver.get(BASE_URL + "/login");
	    driver.findElement(By.id("username")).sendKeys(user);
	    driver.findElement(By.id("passwd")).sendKeys(pass);
	    WebElement oldBody = driver.findElement(By.tagName("body"));
	    driver.findElement(By.cssSelector("input[type=submit]")).click();
	    wait.until(ExpectedConditions.stalenessOf(oldBody));
	}

	private void submitQuestion(String n1, String n2, String answer) {
	    if (n1 != null) driver.findElement(By.id("number1")).sendKeys(n1);
	    if (n2 != null) driver.findElement(By.id("number2")).sendKeys(n2);
	    if (answer != null) driver.findElement(By.id("result")).sendKeys(answer);
	    WebElement oldBody = driver.findElement(By.tagName("body"));
	    driver.findElement(By.cssSelector("input[type=submit]")).click();
	    wait.until(ExpectedConditions.stalenessOf(oldBody));
	}

	private String currentPath() {
		String url = driver.getCurrentUrl();
		return url.replace(BASE_URL, "");
	}

	private void waitForUrlEndsWith(String suffix) {
		wait.until(ExpectedConditions.urlContains(suffix));
	}

	// ---------- tests (ordered by name) ----------

	@Test
	public void t01_welcomePageLoads() {
		driver.get(BASE_URL + "/");
		Assert.assertTrue(driver.getPageSource().contains("Welcome"));
	}

	@Test
	public void t02_loginPageRenders() {
		driver.get(BASE_URL + "/login");
		Assert.assertNotNull(driver.findElement(By.id("username")));
		Assert.assertNotNull(driver.findElement(By.id("passwd")));
	}

	@Test
	public void t03_loginWithInvalidCredentialsStaysOnLogin() {
		doLogin("wrong", "wrong");
		waitForUrlEndsWith("/login");
		Assert.assertEquals("/login", currentPath());
		Assert.assertTrue(driver.getPageSource().contains("Incorrect credentials"));
	}

	@Test
	public void t04_loginWithValidCredentialsRedirectsToQ1() {
		doLogin(VALID_USER, VALID_PASS);
		waitForUrlEndsWith("/q1");
		Assert.assertEquals("/q1", currentPath());
	}

	@Test
	public void t05_q1WrongAnswerStaysOnQ1WithMessage() {
		doLogin(VALID_USER, VALID_PASS);
		waitForUrlEndsWith("/q1");
		submitQuestion("2", "3", "99");
		waitForUrlEndsWith("/q1");
		Assert.assertTrue(driver.getPageSource().contains("Wrong answer"));
	}

	@Test
	public void t06_q1EmptyInputDoesNotShowWhitelabel() {
		doLogin(VALID_USER, VALID_PASS);
		waitForUrlEndsWith("/q1");
		submitQuestion("", "", "");
		waitForUrlEndsWith("/q1");
		String src = driver.getPageSource();
		Assert.assertFalse("Whitelabel error must not appear",
				src.contains("Whitelabel Error"));
		Assert.assertTrue(src.contains("Please enter valid numbers"));
	}

	@Test
	public void t07_q1NonNumericInputShowsValidationMessage() {
		doLogin(VALID_USER, VALID_PASS);
		waitForUrlEndsWith("/q1");
		submitQuestion("abc", "3", "5");
		waitForUrlEndsWith("/q1");
		Assert.assertFalse(driver.getPageSource().contains("Whitelabel Error"));
	}

	@Test
	public void t08_q1CorrectAnswerMovesToQ2() {
		doLogin(VALID_USER, VALID_PASS);
		waitForUrlEndsWith("/q1");
		submitQuestion("2", "3", "5");
		waitForUrlEndsWith("/q2");
		Assert.assertEquals("/q2", currentPath());
	}

	@Test
	public void t09_q2WrongAnswerStaysOnQ2() {
		doLogin(VALID_USER, VALID_PASS);
		waitForUrlEndsWith("/q1");
		submitQuestion("2", "3", "5");
		waitForUrlEndsWith("/q2");
		submitQuestion("5", "2", "99");
		waitForUrlEndsWith("/q2");
		Assert.assertTrue(driver.getPageSource().contains("Wrong answer"));
	}

	@Test
	public void t10_q2EmptyInputDoesNotShowWhitelabel() {
		doLogin(VALID_USER, VALID_PASS);
		waitForUrlEndsWith("/q1");
		submitQuestion("2", "3", "5");
		waitForUrlEndsWith("/q2");
		submitQuestion("", "", "");
		waitForUrlEndsWith("/q2");
		Assert.assertFalse(driver.getPageSource().contains("Whitelabel Error"));
	}

	@Test
	public void t11_q2CorrectAnswerMovesToQ3() {
		doLogin(VALID_USER, VALID_PASS);
		waitForUrlEndsWith("/q1");
		submitQuestion("2", "3", "5");
		waitForUrlEndsWith("/q2");
		submitQuestion("5", "2", "3");
		waitForUrlEndsWith("/q3");
		Assert.assertEquals("/q3", currentPath());
	}

	@Test
	public void t12_q3WrongAnswerStaysOnQ3() {
		doLogin(VALID_USER, VALID_PASS);
		waitForUrlEndsWith("/q1");
		submitQuestion("2", "3", "5");
		waitForUrlEndsWith("/q2");
		submitQuestion("5", "2", "3");
		waitForUrlEndsWith("/q3");
		submitQuestion("3", "4", "99");
		waitForUrlEndsWith("/q3");
		Assert.assertTrue(driver.getPageSource().contains("Wrong answer"));
	}

	@Test
	public void t13_q3EmptyInputDoesNotShowWhitelabel() {
		doLogin(VALID_USER, VALID_PASS);
		waitForUrlEndsWith("/q1");
		submitQuestion("2", "3", "5");
		waitForUrlEndsWith("/q2");
		submitQuestion("5", "2", "3");
		waitForUrlEndsWith("/q3");
		submitQuestion("", "", "");
		waitForUrlEndsWith("/q3");
		Assert.assertFalse(driver.getPageSource().contains("Whitelabel Error"));
	}

	@Test
	public void t14_happyPathLoginThroughQ3ReachesSuccess() {
		doLogin(VALID_USER, VALID_PASS);
		waitForUrlEndsWith("/q1");
		submitQuestion("2", "3", "5");
		waitForUrlEndsWith("/q2");
		submitQuestion("5", "2", "3");
		waitForUrlEndsWith("/q3");
		submitQuestion("3", "4", "12");
		waitForUrlEndsWith("/success");
		WebElement msg = driver.findElement(By.id("success-message"));
		Assert.assertTrue(msg.getText().contains("Well done"));
	}
}