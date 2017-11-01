import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class CyberminerTest {
    private WebDriver driver;
    private String baseUrl;
    private TestLog testLog;
    private enum Browser {CHROME, FIREFOX, SAFARI, IE}
    private Browser browser = Browser.CHROME;


    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        switch (browser) {
            case SAFARI:
                break;
            case IE:
                break;
            case CHROME:
                System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
        }
        baseUrl = "http://localhost:63342/Ciberminer/Cyberminer/gui/index.html";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        testLog = new TestLog(browser.name());
    }


    @Test(priority = 1)
    public void verifyWelcomeScreen() {

        try {
            driver.get(baseUrl);
            assertEquals(driver.findElement(By.id("heading")).getText(), "Welcome to our Cyberminer search engine");
            assertEquals(driver.findElement(By.id("addUrlButton")).getAttribute("value"), "ADD URL");
            assertEquals(driver.findElement(By.id("deleteUrlButton")).getAttribute("value"), "DELETE URL");
            assertEquals(driver.findElement(By.id("searchUrlButton")).getAttribute("value"), "SEARCH URL");
            assertTrue(isElementPresent(By.id("help")));

            testLog.pass();
        }
        catch (Error e) {
            testLog.fail(e.getMessage());
            e.printStackTrace();
            fail();
        }

    }


    @Test(priority = 2)
    public void verifyAbleToGoToAddScreen() {
        try {
            driver.findElement(By.id("addUrlButton")).click();
            assertTrue(isElementPresent(By.linkText("Back to Welcome screen")));
            assertEquals(driver.findElement(By.id("addTitle")).getText(), "Add");
            assertTrue(isElementPresent(By.id("urlLabel")));
            assertTrue(isElementPresent(By.id("urlInput")));
            assertTrue(isElementPresent(By.id("descriptionInput")));
            assertTrue(isElementPresent(By.id("addUrlSubmitButton")));

            testLog.pass();
        }
        catch (Error e) {
            testLog.fail(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }


    @Test(priority = 3)
    public void verifyAbleToGoBackFromAddScreen() {
        try {
            driver.findElement(By.linkText("Back to Welcome screen")).click();
            testLog.pass();
        }
        catch (Error e) {
            testLog.fail(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }


    @Test(priority = 4)
    public void verifyAbleToGoToDeleteScreen() {
        try {
            driver.findElement(By.id("deleteUrlButton")).click();
            assertTrue(isElementPresent(By.linkText("Back to Welcome screen")));
            assertEquals(driver.findElement(By.id("deleteTitle")).getText(), "DELETE");

            // verify elements of the Delete page


            testLog.pass();
        }
        catch (Error e) {
            testLog.fail(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }


    @Test(priority = 5)
    public void verifyAbleToGoBackFromDeleteScreen() {
        try {
            driver.findElement(By.linkText("Back to Welcome screen")).click();
            testLog.pass();
        }
        catch (Error e) {
            testLog.fail(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }


    @Test(priority = 6)
    public void verifyAbleToGoToSearchScreen() {

        try {
            driver.findElement(By.id("searchUrlButton")).click();
            assertTrue(isElementPresent(By.linkText("Back to Welcome screen")));
            assertEquals(driver.findElement(By.id("searchTitle")).getText(), "SEARCH");
            assertTrue(isElementPresent(By.id("searchInput")));
            assertTrue(isElementPresent(By.id("searchButton")));
            assertTrue(isElementPresent(By.id("searchResultArea")));

            testLog.pass();
        }
        catch (Error e) {
            testLog.fail(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }


    @Test(priority = 7)
    public void verifyAbleToGoBackFromSearchScreen() {
        try {
            driver.findElement(By.linkText("Back to Welcome screen")).click();
            testLog.pass();
        }
        catch (Error e) {
            testLog.fail(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }


    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        testLog.save();
    }


    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

}
