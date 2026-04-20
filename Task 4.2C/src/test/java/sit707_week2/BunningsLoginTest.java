package sit707_week2;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BunningsLoginTest {

    // Helper function to pause the browser so elements can load
    private void sleep(int sec) {
        try { 
            Thread.sleep(sec * 1000); 
        } catch (InterruptedException e) { 
            e.printStackTrace(); 
        }
    }

    @Test
    public void testLoginInvalidEmailFormat() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        
        // FIX 1: Maximize the window to ensure elements are visible
        driver.manage().window().maximize();
        
        System.out.println("Testing Invalid Email Format...");
        driver.get("https://www.bunnings.com.au/login");
        sleep(2);
        
        WebElement emailField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        
        emailField.sendKeys("this-is-not-an-email");
        passwordField.sendKeys("SomePassword123!");
        
        // Find the submit button
        WebElement submitBtn = driver.findElement(By.xpath("//button[@type='submit']"));
        
        // FIX 2: Use JavascriptExecutor to force the click
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", submitBtn);
        
        sleep(3);

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("Test Failed: URL should still contain 'login' for invalid input", currentUrl.contains("login"));
        
        driver.quit();
    }

    @Test
    public void testLoginEmptyCredentials() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        
        // FIX 1: Maximize the window
        driver.manage().window().maximize();
        
        System.out.println("Testing Empty Credentials...");
        driver.get("https://www.bunnings.com.au/login");
        sleep(2);
        
        WebElement submitBtn = driver.findElement(By.xpath("//button[@type='submit']"));
        
        // FIX 2: Use JavascriptExecutor to force the click
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", submitBtn);
        
        sleep(3);

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("Test Failed: URL should still contain 'login' when fields are empty", currentUrl.contains("login"));
        
        driver.quit();
    }
}