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
    private String system = System.getProperty("os.name");


    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        switch (browser) {
            case SAFARI:
                break;
            case IE:
                break;
            case CHROME:
                System.setProperty("webdriver.chrome.driver",
                        String.format("drivers/chromedriver%s", system.contains("mac") ? "" : ".exe"));
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                System.setProperty("webdriver.gecko.driver",
                        String.format("drivers/geckodriver%s", system.contains("mac") ? "" : ".exe"));
                driver = new FirefoxDriver();
                break;
        }
        baseUrl = "http://localhost:63342/Cyberminer/gui/index.html";
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
        catch (Throwable t) {
            handleThrowable(t);
        }

    }


    @Test(priority = 2)
    public void verifyAbleToGoToAddScreen() {
        try {
            driver.findElement(By.id("addUrlButton")).click();
            assertEquals(driver.findElement(By.id("addTitle")).getText(), "Add");
            assertTrue(isElementPresent(By.id("urlLabel")));
            assertTrue(isElementPresent(By.id("urlInput")));
            assertTrue(isElementPresent(By.id("descriptionInput")));
            assertTrue(isElementPresent(By.id("addUrlSubmitButton")));

            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 3)
    public void verifyAbleToGoBackFromAddScreen() {
        try {
            assertTrue(isElementPresent(By.linkText("Back to Welcome screen")));
            driver.findElement(By.linkText("Back to Welcome screen")).click();
            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 4)
    public void verifyAbleToGoToDeleteScreen() {
        try {
            driver.findElement(By.id("deleteUrlButton")).click();
            assertEquals(driver.findElement(By.id("deleteTitle")).getText(), "DELETE");

            // verify elements of the Delete page


            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 5)
    public void verifyAbleToGoBackFromDeleteScreen() {
        try {
            assertTrue(isElementPresent(By.linkText("Back to Welcome screen")));
            driver.findElement(By.linkText("Back to Welcome screen")).click();
            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 6)
    public void verifyAbleToGoToSearchScreen() {

        try {
            driver.findElement(By.id("searchUrlButton")).click();
            assertEquals(driver.findElement(By.id("searchTitle")).getText(), "SEARCH");
            assertTrue(isElementPresent(By.id("searchInput")));
            assertTrue(isElementPresent(By.id("searchButton")));
            assertTrue(isElementPresent(By.id("searchResultArea")));

            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 7)
    public void verifyAbleToGoBackFromSearchScreen() {
        try {
            assertTrue(isElementPresent(By.linkText("Back to Welcome screen")));
            driver.findElement(By.linkText("Back to Welcome screen")).click();
            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 8)
    public void verifyNewValidUrl() {
        try {
            driver.get(baseUrl);
            driver.findElement(By.id("addUrlButton")).click();

            for (String[] item : TestAssets.VALID_ITEMS) {
                driver.findElement(By.id("urlInput")).clear();
                driver.findElement(By.id("urlInput")).sendKeys(
                        item[TestAssets.Component.URL.ordinal()]);
                driver.findElement(By.id("descriptionInput")).clear();
                driver.findElement(By.id("descriptionInput")).sendKeys(
                        item[TestAssets.Component.DESCRIPTION.ordinal()]);
                driver.findElement(By.id("addUrlSubmitButton")).click();
                assertFalse(driver.findElement(By.id("invalidUrlMessage")).isDisplayed());
            }

            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 9)
    public void verifyAbleToRejectInvalidUrl() {
        try {
//            driver.get(baseUrl + "/Ciberminer/gui/index.html");
//            driver.findElement(By.id("addUrlButton")).click();

            for (String[] item : TestAssets.INVALID_ITEMS) {
                driver.findElement(By.id("urlInput")).clear();
                driver.findElement(By.id("urlInput")).sendKeys(
                        item[TestAssets.Component.URL.ordinal()]);
                driver.findElement(By.id("descriptionInput")).clear();
                driver.findElement(By.id("descriptionInput")).sendKeys(
                        item[TestAssets.Component.DESCRIPTION.ordinal()]);
                driver.findElement(By.id("addUrlSubmitButton")).click();
                assertTrue(driver.findElement(By.id("invalidUrlMessage")).isDisplayed());
            }

            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 10)
    public void verifyAbleToDeleteUrl() {
        try {
            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 11)
    public void verifyAbleToSearchSingleWord() {
        try {
            driver.findElement(By.id("searchUrlButton")).click();

            // search google

            // search utd

            // search wikipedia

            // search fanfiction
            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 12)
    public void verifyAbleToSearchMultiWordsOriginalOrder() {
        try {
            // search google

            // search utd

            // search wikipedia

            // search fanfiction
            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 13)
    public void verifyAbleToSearchMultiWordsShuffledOrder() {
        try {
            // search google

            // search utd

            // search wikipedia

            // search fanfiction
            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }

    @Test(priority = 14)
    public void verifyAbleToDetectNonExistingSearch() {
        try {
            // DNE 1

            // DNE 2
            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 15)
    public void verifyAndSearch() {
        try {
            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 16)
    public void verifyOrSearch() {
        try {
            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 17)
    public void verifyNotSearch() {
        try {
            testLog.pass();
        }
        catch (Throwable t) {
            testLog.fail(t.getMessage());
            t.printStackTrace();
            fail();
        }
    }


    @Test(priority = 18)
    public void verifyAbleToSortAscendingDescription() {
        try {
            // https://stackoverflow.com/questions/36950061/how-to-check-webelements-in-webtable-is-sorted-alphabetically-using-selenium-web
            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 19)
    public void verifyAbleToSortDescendingDescription() {
        try {
            //https://stackoverflow.com/questions/36950061/how-to-check-webelements-in-webtable-is-sorted-alphabetically-using-selenium-web
            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
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


    private void handleThrowable(Throwable t) {
        String errorMessage = t.getMessage().split("\n")[0];
        testLog.fail(errorMessage);
        t.printStackTrace();
        fail();
    }

}
