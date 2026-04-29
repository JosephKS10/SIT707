package web.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;


public class LoginServiceTest {

    // ===== EDIT THIS FOR YOUR MACHINE =====
    // Absolute file:// URL to login.html on disk.
    private static final String LOGIN_PAGE_URL =
            "file:///Users/josephksaji/Downloads/7.1P-resources/pages/login.html";
    // ======================================

    private WebDriver driver;

   
    @BeforeClass
    public static void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    private void sleep(long sec) {
        try { Thread.sleep(sec * 1000); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    @Before
    public void setUp() {
        ChromeOptions opts = new ChromeOptions();
        // opts.addArguments("--headless=new", "--no-sandbox");  // uncomment for CI
        driver = new ChromeDriver(opts);
        driver.navigate().to(LOGIN_PAGE_URL);
        sleep(1);
    }

    @After
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    private void fillForm(String username, String password, String dob) {
        WebElement u = driver.findElement(By.id("username"));
        u.clear();
        if (username != null) u.sendKeys(username);

        WebElement p = driver.findElement(By.id("passwd"));
        p.clear();
        if (password != null) p.sendKeys(password);

        // Set the date input via JS so locale formatting (en-US vs en-AU)
        // doesn't break the test.
        WebElement d = driver.findElement(By.id("dob"));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value=arguments[1];", d, dob == null ? "" : dob);
    }

    private void submit() {
        driver.findElement(By.cssSelector("[type=submit]")).submit();
        sleep(2);
    }

    /* ---- Positive ---- */

    @Test public void testLoginSuccess_primaryUser() {
        fillForm("ahsan", "ahsan_pass", "1990-05-15");
        submit();
        Assert.assertEquals("success", driver.getTitle());
    }

    @Test public void testLoginSuccess_secondUser() {
        fillForm("joseph", "joseph_pass", "1998-08-20");
        submit();
        Assert.assertEquals("success", driver.getTitle());
    }

    /* ---- Negative: credential mismatches ---- */

    @Test public void testLoginFail_wrongPassword() {
        fillForm("ahsan", "WRONG_PASSWORD", "1990-05-15");
        submit();
        Assert.assertEquals("fail", driver.getTitle());
    }

    @Test public void testLoginFail_unregisteredUsername() {
        fillForm("ghost", "any_pass", "1990-05-15");
        submit();
        Assert.assertEquals("fail", driver.getTitle());
    }

    @Test public void testLoginFail_wrongDob() {
        fillForm("ahsan", "ahsan_pass", "1991-05-15");
        submit();
        Assert.assertEquals("fail", driver.getTitle());
    }

    @Test public void testLoginFail_crossUserCredentials() {
        fillForm("ahsan", "sarthak_pass", "1998-08-20");
        submit();
        Assert.assertEquals("fail", driver.getTitle());
    }

    /* ---- Negative: empty / missing fields ---- */

    @Test public void testLoginFail_emptyUsername() {
        fillForm("", "ahsan_pass", "1990-05-15");
        submit();
        Assert.assertEquals("fail", driver.getTitle());
    }

    @Test public void testLoginFail_emptyPassword() {
        fillForm("ahsan", "", "1990-05-15");
        submit();
        Assert.assertEquals("fail", driver.getTitle());
    }

    @Test public void testLoginFail_emptyDob() {
        fillForm("ahsan", "ahsan_pass", "");
        submit();
        Assert.assertEquals("fail", driver.getTitle());
    }

    @Test public void testLoginFail_allFieldsEmpty() {
        fillForm("", "", "");
        submit();
        Assert.assertEquals("fail", driver.getTitle());
    }

    /* ---- Sanity: assert on body element too, not just title ---- */

    @Test public void testLoginSuccess_bodyAlsoReflectsStatus() {
        fillForm("alice", "alice123", "2000-01-01");
        submit();
        Assert.assertEquals("success", driver.getTitle());
        WebElement status = driver.findElement(By.id("status"));
        Assert.assertTrue(status.getText().toLowerCase().contains("success"));
    }
}