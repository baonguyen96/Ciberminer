import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class CyberminerTest {
    private enum Browser {CHROME, FIREFOX, SAFARI, IE}
    private WebDriver driver;
    private String baseUrl;
    private TestLog testLog;
    private Browser browser = Browser.FIREFOX;
    private String system = System.getProperty("os.name");
    private int currentTestCase;


    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        /*
         * if cannot open the browser, check out:
         * https://intellij-support.jetbrains.com/hc/en-us/community/posts/207120569-Page-requested-without-authorization
         */
        setBrowser();
        baseUrl = "http://localhost:63342/Cyberminer/gui/index.html";
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        testLog = new TestLog(browser.name());
        driver.get(baseUrl);
    }


    private void setBrowser() {
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
    }


    @Test(priority = 1)
    public void verifyWelcomeScreen() {
        currentTestCase = 1;
        
        try {
            assertEquals(driver.findElement(By.id("heading")).getText(), "Welcome to our Cyberminer search engine");
            assertEquals(driver.findElement(By.id("addUrlButton")).getAttribute("value"), "ADD URL");
            assertEquals(driver.findElement(By.id("deleteUrlButton")).getAttribute("value"), "DELETE URL");
            assertEquals(driver.findElement(By.id("searchUrlButton")).getAttribute("value"), "SEARCH URL");
            assertTrue(isElementPresent(By.id("help")));

            testLog.pass(currentTestCase);
        }
        catch (Throwable t) {
            handleThrowable(t);
        }

    }


    @Test(priority = 2)
    public void verifyAbleToGoToAddScreen() {
        currentTestCase = 2;

        try {
            driver.findElement(By.id("addUrlButton")).click();
            assertEquals(driver.findElement(By.id("addTitle")).getText(), "Add");
            assertTrue(isElementPresent(By.id("urlLabel")));
            assertTrue(isElementPresent(By.id("urlInput")));
            assertTrue(isElementPresent(By.id("descriptionInput")));
            assertTrue(isElementPresent(By.id("addUrlSubmitButton")));

            testLog.pass(currentTestCase);
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 3)
    public void verifyAbleToGoBackFromAddScreen() {
        currentTestCase = 3;

        try {
            assertTrue(isElementPresent(By.linkText("Back to Welcome screen")));
            driver.findElement(By.linkText("Back to Welcome screen")).click();
            assertTrue(driver.findElement(By.id("heading")).isDisplayed());
            testLog.pass(currentTestCase);
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 4)
    public void verifyAbleToGoToDeleteScreen() {
        currentTestCase = 4;

        try {
            driver.findElement(By.id("deleteUrlButton")).click();
            assertEquals(driver.findElement(By.id("deleteTitle")).getText(), "DELETE");
            assertEquals(driver.findElement(By.linkText("Back to Welcome screen")).getText(), "Back to Welcome screen");
            assertEquals(driver.findElement(By.id("deleteTitle")).getText(), "DELETE");
            assertEquals(driver.findElement(By.id("urlHeader")).getText(), "URL");
            assertEquals(driver.findElement(By.id("descriptionHeader")).getText(), "DESCRIPTION");
            assertTrue(isElementPresent(By.id("allUrlTable")));

            testLog.pass(currentTestCase);
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 5)
    public void verifyAbleToGoBackFromDeleteScreen() {
        currentTestCase = 5;

        try {
            assertTrue(isElementPresent(By.linkText("Back to Welcome screen")));
            driver.findElement(By.linkText("Back to Welcome screen")).click();
            assertTrue(driver.findElement(By.id("heading")).isDisplayed());
            testLog.pass(currentTestCase);
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 6)
    public void verifyAbleToGoToSearchScreen() {
        currentTestCase = 6;

        try {
            driver.findElement(By.id("searchUrlButton")).click();
            assertEquals(driver.findElement(By.id("searchTitle")).getText(), "SEARCH");
            assertTrue(isElementPresent(By.id("searchInput")));
            assertTrue(isElementPresent(By.id("searchButton")));
            assertTrue(isElementPresent(By.id("searchResultTable")));

            testLog.pass(currentTestCase);
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 7)
    public void verifyAbleToGoBackFromSearchScreen() {
        currentTestCase = 7;

        try {
            assertTrue(isElementPresent(By.linkText("Back to Welcome screen")));
            driver.findElement(By.linkText("Back to Welcome screen")).click();
            assertTrue(driver.findElement(By.id("heading")).isDisplayed());
            testLog.pass(currentTestCase);
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 8)
    public void verifyAbleToAddNewValidUrl() {
        currentTestCase = 8;

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
                assertTrue(driver.findElement(By.id("validUrlMessage")).isDisplayed());
            }

            testLog.pass(currentTestCase);
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 9)
    public void verifyAbleToRejectAddingInvalidUrl() {
        currentTestCase = 9;

        try {
            for (String[] item : TestAssets.INVALID_ITEMS) {
                driver.findElement(By.id("urlInput")).clear();
                driver.findElement(By.id("urlInput")).sendKeys(
                        item[TestAssets.Component.URL.ordinal()]);
                driver.findElement(By.id("descriptionInput")).clear();
                driver.findElement(By.id("descriptionInput")).sendKeys(
                        item[TestAssets.Component.DESCRIPTION.ordinal()]);
                driver.findElement(By.id("addUrlSubmitButton")).click();
                assertTrue(driver.findElement(By.id("invalidUrlMessage")).isDisplayed());
                assertFalse(driver.findElement(By.id("validUrlMessage")).isDisplayed());
            }

            testLog.pass(currentTestCase);
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 10)
    public void verifyAbleToRejectAddingExistingUrl() {
        currentTestCase = 10;

        try {
            // try google
            driver.findElement(By.id("urlInput")).clear();
            driver.findElement(By.id("urlInput")).sendKeys(
                    TestAssets.VALID_ITEMS
                            [TestAssets.ValidSite.GOOGLE.ordinal()]
                            [TestAssets.Component.URL.ordinal()]
            );
            driver.findElement(By.id("descriptionInput")).clear();
            driver.findElement(By.id("descriptionInput")).sendKeys(
                    TestAssets.VALID_ITEMS
                            [TestAssets.ValidSite.GOOGLE.ordinal()]
                            [TestAssets.Component.DESCRIPTION.ordinal()]
            );
            driver.findElement(By.id("addUrlSubmitButton")).click();
            assertTrue(driver.findElement(By.id("invalidUrlMessage")).isDisplayed());

            // try wikipedia
            driver.findElement(By.id("urlInput")).clear();
            driver.findElement(By.id("urlInput")).sendKeys(
                    TestAssets.VALID_ITEMS
                            [TestAssets.ValidSite.WIKIPEDIA.ordinal()]
                            [TestAssets.Component.URL.ordinal()]
            );
            driver.findElement(By.id("descriptionInput")).clear();
            driver.findElement(By.id("descriptionInput")).sendKeys(
                    TestAssets.VALID_ITEMS
                            [TestAssets.ValidSite.WIKIPEDIA.ordinal()]
                            [TestAssets.Component.DESCRIPTION.ordinal()]
            );
            driver.findElement(By.id("addUrlSubmitButton")).click();
            assertTrue(driver.findElement(By.id("invalidUrlMessage")).isDisplayed());

            testLog.pass(currentTestCase);
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
        finally {
            assertTrue(isElementPresent(By.linkText("Back to Welcome screen")));
            driver.findElement(By.linkText("Back to Welcome screen")).click();
            assertTrue(driver.findElement(By.id("heading")).isDisplayed());
        }
    }


    @Test(priority = 11)
    public void verifyAbleToDeleteUrl() {
        currentTestCase = 11;

        try {
            driver.findElement(By.id("deleteUrlButton")).click();
            assertEquals(driver.findElement(By.id("deleteTitle")).getText(), "DELETE");
            assertTrue(driver.findElement(By.id("urlNumber0")).isDisplayed());

            String urlToDelete = driver.findElement(By.id("urlNumber0")).getText();
            String descriptionToDelete = driver.findElement(By.id("descriptionNumber0")).getText();

            driver.findElement(By.id("deleteButtonNumber0")).click();

            ArrayList<WebElement> remainingUrlList = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[contains(@id, 'urlNumber')]"));
            ArrayList<WebElement> remainingDescriptionList = (ArrayList<WebElement>) driver.findElements(By.xpath("//*[contains(@id, 'descriptionNumber')]"));


//            assertTrue(urlToDelete.equals(remainingUrlList.get(0).getText()));
//            assertTrue(descriptionToDelete.equals(remainingDescriptionList.get(0).getText()));
//            assertFalse(urlToDelete.equals(remainingUrlList.get(1).getText()));
//            assertFalse(descriptionToDelete.equals(remainingDescriptionList.get(1).getText()));

            for(int i = 0; i < remainingUrlList.size(); i++) {
                assertFalse(urlToDelete.equals(remainingUrlList.get(i).getText()));
                assertFalse(descriptionToDelete.equals(remainingDescriptionList.get(i).getText()));
            }

            testLog.pass(currentTestCase);
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
        finally {
            assertTrue(isElementPresent(By.linkText("Back to Welcome screen")));
            driver.findElement(By.linkText("Back to Welcome screen")).click();
            assertTrue(driver.findElement(By.id("heading")).isDisplayed());
        }
    }


    @Test(priority = 12)
    public void verifyAbleToSearchSingleWord() {
        currentTestCase = 12;

        try {
            driver.findElement(By.id("searchUrlButton")).click();

            // search google

            // search utd

            // search wikipedia

            // search fanfiction
            testLog.pass(currentTestCase);
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 13)
    public void verifyAbleToSearchMultiWordsOriginalOrder() {
        currentTestCase = 13;

        try {
            // search google

            // search utd

            // search wikipedia

            // search fanfiction
            testLog.pass(currentTestCase);
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 14)
    public void verifyAbleToSearchMultiWordsShuffledOrder() {
        currentTestCase = 14;

        try {
            // search google

            // search utd

            // search wikipedia

            // search fanfiction
            testLog.pass(currentTestCase);
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }

    @Test(priority = 15)
    public void verifyAbleToDetectNonExistingSearch() {
        currentTestCase = 15;

        try {
            for(String invalidSearch : TestAssets.INVALID_SEARCHES) {
                driver.findElement(By.id("searchInput")).clear();
                driver.findElement(By.id("searchInput")).sendKeys(invalidSearch);
                driver.findElement(By.id("searchButton")).click();
                assertTrue(driver.findElement(By.id("invalidSearchMessage")).isDisplayed());
                assertEquals(driver.findElement(By.id("invalidSearchMessage")).getText(), "Error! No result found for your search.");
            }

            testLog.pass(currentTestCase);
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 16)
    public void verifyAndSearch() {
        currentTestCase = 16;

        try {
            testLog.pass(currentTestCase);
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 17)
    public void verifyOrSearch() {
        currentTestCase = 17;

        try {
            testLog.pass(currentTestCase);
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 18)
    public void verifyNotSearch() {
        currentTestCase = 18;

        try {
            testLog.pass(currentTestCase);
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 19)
    public void verifyAbleToSortAscendingDescription() {
        currentTestCase = 19;

        try {
            // https://stackoverflow.com/questions/36950061/how-to-check-webelements-in-webtable-is-sorted-alphabetically-using-selenium-web
            driver.findElement(By.id("searchInput")).clear();
            driver.findElement(By.id("searchInput")).sendKeys(TestAssets.SEARCH_WITH_MULTIPLE_RESULTS);
            driver.findElement(By.id("searchButton")).click();

            // sort results

            testLog.pass(currentTestCase);
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 20)
    public void verifyAbleToSortDescendingDescription() {
        currentTestCase = 20;

        try {
            //https://stackoverflow.com/questions/36950061/how-to-check-webelements-in-webtable-is-sorted-alphabetically-using-selenium-web
            testLog.pass(currentTestCase);
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
        testLog.fail(currentTestCase, errorMessage);
        t.printStackTrace();
        fail();
    }

}
