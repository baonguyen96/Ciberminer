import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class CyberminerTest {
    private enum Browser {CHROME, FIREFOX, SAFARI, EDGE}

    private WebDriver driver;
    private String baseUrl;
    private TestLog testLog;


    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        /*
         * if cannot open the browser, check out:
         * https://intellij-support.jetbrains.com/hc/en-us/community/posts/207120569-Page-requested-without-authorization
         */
        Browser browser = Browser.CHROME;
        setBrowser(browser);
        // clear database
        baseUrl = "http://localhost:63342/Cyberminer/gui/index.html";
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        testLog = new TestLog(browser.name());
        driver.get(baseUrl);
    }


    private void setBrowser(Browser browser) {
        switch (browser) {
            case SAFARI:
                break;
            case EDGE:
                System.setProperty("webdriver.edge.driver", "drivers/MicrosoftWebDriver.exe");
                driver = new EdgeDriver();
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
    }


    @Test(priority = 1)
    public void verifyWelcomeScreen() {

        try {
            assertEquals(driver.findElement(By.id("heading")).getText(), "Welcome to our Cyberminer search engine");
            assertEquals(driver.findElement(By.id("addUrlButton")).getAttribute("value"), "ADD URL");
            assertEquals(driver.findElement(By.id("deleteUrlButton")).getAttribute("value"), "DELETE URL");
            assertEquals(driver.findElement(By.id("searchUrlButton")).getAttribute("value"), "SEARCH URL");
            assertEquals(driver.findElement(By.id("configurationButton")).getAttribute("value"), "CONFIGURATION");
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
            assertEquals(driver.findElement(By.id("addTitle")).getText(), "ADD");
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
            goBackToWelcomeScreen();
        }
        catch (Throwable t) {
            handleThrowable(t);
            driver.get(baseUrl);
        }
    }


    @Test(priority = 4)
    public void verifyAbleToGoToDeleteScreen() {

        try {
            driver.findElement(By.id("deleteUrlButton")).click();
            assertEquals(driver.findElement(By.id("deleteTitle")).getText(), "DELETE");
            assertEquals(driver.findElement(By.id("backToWelcome")).getText(), "Back to Welcome screen");
            assertEquals(driver.findElement(By.id("deleteTitle")).getText(), "DELETE");
            assertEquals(driver.findElement(By.id("urlHeader")).getText(), "URL");
            assertEquals(driver.findElement(By.id("descriptionHeader")).getText(), "DESCRIPTION");
            assertTrue(isElementPresent(By.id("allUrlTable")));

            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 5)
    public void verifyAbleToGoBackFromDeleteScreen() {

        try {
            goBackToWelcomeScreen();
        }
        catch (Throwable t) {
            handleThrowable(t);
            driver.get(baseUrl);
        }
    }


    @Test(priority = 6)
    public void verifyAbleToGoToSearchScreen() {

        try {
            driver.findElement(By.id("searchUrlButton")).click();
            assertEquals(driver.findElement(By.id("searchTitle")).getText(), "SEARCH");
            assertTrue(isElementPresent(By.id("searchInput")));
            assertTrue(isElementPresent(By.id("searchButton")));
            assertTrue(isElementPresent(By.id("searchResultTable")));

            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 7)
    public void verifyAbleToGoBackFromSearchScreen() {

        try {
            goBackToWelcomeScreen();
        }
        catch (Throwable t) {
            handleThrowable(t);
            driver.get(baseUrl);
        }
    }


    @Test(priority = 8)
    public void verifyAbleToGoToConfigurationScreen() {

        try {
            driver.findElement(By.id("configurationButton")).click();
            assertEquals(driver.findElement(By.id("configurationTitle")).getText(), "CONFIGURATION");
            assertEquals(driver.findElement(By.id("configurableSymbolsLabel")).getText(), "Symbols to ignore");
            assertTrue(isElementPresent(By.id("configurableSymbolsInput")));
            assertEquals(driver.findElement(By.id("resultsPerPageLabel")).getText(), "Maximum results per page:");
            assertTrue(isElementPresent(By.id("resultsPerPageOptions")));
            assertFalse(driver.findElement(By.id("configurationUpdateMessage")).isDisplayed());
            driver.findElement(By.id("configurableSymbolsInput")).clear();
            driver.findElement(By.id("configurableSymbolsInput")).sendKeys(TestAssets.IGNORED_SYMBOLS);
            driver.findElement(By.id("3resultPerPage")).click();
            assertTrue(driver.findElement(By.id("configurationSubmitButton")).isDisplayed());
            driver.findElement(By.id("configurationSubmitButton")).click();
            assertTrue(driver.findElement(By.id("configurationUpdateMessage")).isDisplayed());

            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 9)
    public void verifyAbleToGoBackFromConfigurationScreen() {

        try {
            goBackToWelcomeScreen();
        }
        catch (Throwable t) {
            handleThrowable(t);
            driver.get(baseUrl);
        }
    }


    @Test(priority = 10)
    public void verifyAbleToAddNewValidUrl() {

        try {
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

            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 11)
    public void verifyAbleToRejectAddingInvalidUrl() {

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

            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 12)
    public void verifyAbleToRejectAddingExistingUrl() {

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
            assertFalse(driver.findElement(By.id("validUrlMessage")).isDisplayed());

            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
        finally {
            driver.findElement(By.id("backToWelcome")).click();
        }
    }


    @Test(priority = 13)
    public void verifyAbleToDeleteUrl() {

        try {
            driver.findElement(By.id("deleteUrlButton")).click();
            assertEquals(driver.findElement(By.id("deleteTitle")).getText(), "DELETE");
            assertTrue(driver.findElement(By.id("urlNumber0")).isDisplayed());

            int originalNumberOfTuple = driver.findElements(By.xpath("//*[contains(@id, 'urlNumber')]")).size();
            String urlToDelete = driver.findElement(By.id("urlNumber0")).getText();
            String descriptionToDelete = driver.findElement(By.id("descriptionNumber0")).getText();

            driver.findElement(By.id("deleteButtonNumber0")).click();

            List<WebElement> remainingUrlList = driver.findElements(By.xpath("//*[contains(@id, 'urlNumber')]"));
            List<WebElement> remainingDescriptionList = driver.findElements(By.xpath("//*[contains(@id, 'descriptionNumber')]"));

            assertEquals(remainingUrlList.size(), originalNumberOfTuple - 1);

            for (int i = 0; i < remainingUrlList.size(); i++) {
                assertEquals(remainingUrlList.get(i).getText(), urlToDelete);
                assertEquals(remainingDescriptionList.get(i).getText(), descriptionToDelete);
            }


            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
        finally {
            driver.findElement(By.id("backToWelcome")).click();
        }
    }


    @Test(priority = 14)
    public void verifyAbleToSearchSingleWord() {

        try {
            driver.findElement(By.id("searchUrlButton")).click();
            assertEquals(driver.findElement(By.id("searchTitle")).getText(), "SEARCH");

            String expectedUrl = null, actualUrl = null,
                    expectedDescription = null, actualDescription = null;

            // search by site name --> only 1 result
            for (TestAssets.ValidSite site : TestAssets.ValidSite.values()) {

                driver.findElement(By.id("searchInput")).clear();
                driver.findElement(By.id("searchInput")).sendKeys(
                        TestAssets.VALID_SEARCHES[site.ordinal()][TestAssets.SearchBy.ONE_WORD.ordinal()]
                );
                driver.findElement(By.id("searchButton")).click();

                expectedUrl = TestAssets.VALID_ITEMS[site.ordinal()][TestAssets.Component.URL.ordinal()];
                actualUrl = driver.findElement(By.id("urlNumber0")).getText();
                assertEquals(actualUrl, expectedUrl);

                expectedDescription = TestAssets.VALID_ITEMS[site.ordinal()][TestAssets.Component.DESCRIPTION.ordinal()];
                actualDescription = driver.findElement(By.id("descriptionNumber0")).getText();
                assertEquals(actualDescription, expectedDescription);

                assertFalse(isElementPresent(By.id("urlNumber1")));
            }

            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 15)
    public void verifyAbleToSearchMultiWords() {

        try {
            String expectedUrl = null, actualUrl = null,
                    expectedDescription = null, actualDescription = null;

            for (TestAssets.ValidSite site : TestAssets.ValidSite.values()) {
                driver.findElement(By.id("searchInput")).clear();
                driver.findElement(By.id("searchInput")).sendKeys(
                        TestAssets.VALID_SEARCHES[site.ordinal()][TestAssets.SearchBy.MULTI_WORDS.ordinal()]
                );
                driver.findElement(By.id("searchButton")).click();

                expectedUrl = TestAssets.INVALID_ITEMS[site.ordinal()][TestAssets.Component.URL.ordinal()];
                actualUrl = driver.findElement(By.id("urlNumber0")).getText();
                assertEquals(actualUrl, expectedUrl);

                expectedDescription = TestAssets.INVALID_ITEMS[site.ordinal()][TestAssets.Component.DESCRIPTION.ordinal()];
                actualDescription = driver.findElement(By.id("descriptionNumber0")).getText();
                assertEquals(actualDescription, expectedDescription);

            }

            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 16)
    public void verifyAbleToDetectNonExistingSearch() {

        try {
            for (String invalidSearch : TestAssets.INVALID_SEARCHES) {
                driver.findElement(By.id("searchInput")).clear();
                driver.findElement(By.id("searchInput")).sendKeys(invalidSearch);
                driver.findElement(By.id("searchButton")).click();

                assertFalse(isElementPresent(By.id("searchResultTable")));
                assertTrue(driver.findElement(By.id("invalidSearchMessage")).isDisplayed());
                assertEquals(driver.findElement(By.id("invalidSearchMessage")).getText(), "Error! No result found for your search.");
            }

            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 17)
    public void verifyAbleToPerformAndSearch() {

        try {
            // error ("google" and "wikipedia")
            String searchQuery = String.format("%s [AND] %s",
                    TestAssets.VALID_SEARCHES
                            [TestAssets.ValidSite.GOOGLE.ordinal()]
                            [TestAssets.SearchBy.ONE_WORD.ordinal()],
                    TestAssets.VALID_SEARCHES
                            [TestAssets.ValidSite.WIKIPEDIA.ordinal()]
                            [TestAssets.SearchBy.ONE_WORD.ordinal()]
            );
            driver.findElement(By.id("searchInput")).clear();
            driver.findElement(By.id("searchInput")).sendKeys(searchQuery);
            driver.findElement(By.id("searchButton")).click();
            assertTrue(driver.findElement(By.id("invalidSearchMessage")).isDisplayed());
            assertEquals(driver.findElement(By.id("invalidSearchMessage")).getText(), "Error! No result found for your search.");
            assertFalse(isElementPresent(By.id("searchResultTable")));

            // ok (return google)
            searchQuery = String.format("%s [AND] %s",
                    TestAssets.VALID_SEARCHES
                            [TestAssets.ValidSite.GOOGLE.ordinal()]
                            [TestAssets.SearchBy.ONE_WORD.ordinal()],
                    TestAssets.SEARCH_WITH_MULTIPLE_RESULTS
            );
            driver.findElement(By.id("searchInput")).clear();
            driver.findElement(By.id("searchInput")).sendKeys(searchQuery);
            driver.findElement(By.id("searchButton")).click();

            String expectedUrl = TestAssets.VALID_ITEMS[TestAssets.ValidSite.GOOGLE.ordinal()][TestAssets.Component.URL.ordinal()];
            String actualUrl = driver.findElement(By.id("urlNumber0")).getText();
            String expectedDescription = TestAssets.VALID_ITEMS[TestAssets.ValidSite.GOOGLE.ordinal()][TestAssets.Component.DESCRIPTION.ordinal()];
            String actualDescription = driver.findElement(By.id("descriptionNumber0")).getText();

            assertEquals(actualUrl, expectedUrl);
            assertEquals(actualDescription, expectedDescription);
            assertFalse(isElementPresent(By.id("urlNumber1")));

            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 18)
    public void verifyAbleToPerformOrSearch() {

        try {
            // error
            String searchQuery = String.format("%s [OR] %s",
                    TestAssets.INVALID_SEARCHES[TestAssets.SearchBy.ONE_WORD.ordinal()],
                    TestAssets.INVALID_SEARCHES[TestAssets.SearchBy.MULTI_WORDS.ordinal()]
            );

            driver.findElement(By.id("searchInput")).clear();
            driver.findElement(By.id("searchInput")).sendKeys(searchQuery);
            driver.findElement(By.id("searchButton")).click();

            assertFalse(isElementPresent(By.id("searchResultTable")));
            assertTrue(driver.findElement(By.id("invalidSearchMessage")).isDisplayed());
            assertEquals(driver.findElement(By.id("invalidSearchMessage")).getText(), "Error! No result found for your search.");

            // utd or fanfiction
            searchQuery = String.format("%s [OR] %s",
                    TestAssets.VALID_SEARCHES
                            [TestAssets.ValidSite.UTD.ordinal()]
                            [TestAssets.SearchBy.ONE_WORD.ordinal()],
                    TestAssets.VALID_SEARCHES
                            [TestAssets.ValidSite.FANFICTION.ordinal()]
                            [TestAssets.SearchBy.ONE_WORD.ordinal()]
            );
            int[] expectedSiteIndices = {
                    TestAssets.ValidSite.UTD.ordinal(),
                    TestAssets.ValidSite.FANFICTION.ordinal()
            };

            driver.findElement(By.id("searchInput")).clear();
            driver.findElement(By.id("searchInput")).sendKeys(searchQuery);
            driver.findElement(By.id("searchButton")).click();

            validateUrlDescriptionTableUnordered(expectedSiteIndices);

            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 19)
    public void verifyAbleToPerformNotSearch() {

        try {
            // error
            String searchQuery = String.format("[NOT] %s",
                    TestAssets.SEARCH_COMMON_TO_ALL);
            driver.findElement(By.id("searchInput")).clear();
            driver.findElement(By.id("searchInput")).sendKeys(searchQuery);
            driver.findElement(By.id("searchButton")).click();

            assertFalse(isElementPresent(By.id("searchResultTable")));
            assertTrue(driver.findElement(By.id("invalidSearchMessage")).isDisplayed());
            assertEquals(driver.findElement(By.id("invalidSearchMessage")).getText(), "Error! No result found for your search.");

            // anything but google
            searchQuery = String.format("[NOT] %s",
                    TestAssets.VALID_SEARCHES
                            [TestAssets.ValidSite.GOOGLE.ordinal()]
                            [TestAssets.SearchBy.ONE_WORD.ordinal()]);

            int[] expectedSiteIndices = {
                    TestAssets.ValidSite.UTD.ordinal(),
                    TestAssets.ValidSite.WIKIPEDIA.ordinal(),
                    TestAssets.ValidSite.FANFICTION.ordinal()
            };

            driver.findElement(By.id("searchInput")).clear();
            driver.findElement(By.id("searchInput")).sendKeys(searchQuery);
            driver.findElement(By.id("searchButton")).click();

            validateUrlDescriptionTableUnordered(expectedSiteIndices);

            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 20)
    public void verifyAbleToSortAscendingDescription() {

        try {
            // https://stackoverflow.com/questions/36950061/how-to-check-webelements-in-webtable-is-sorted-alphabetically-using-selenium-web

            // continue from NOT search results (utd, wikipedia, fanfiction)

            int[] expectedAscendingDescriptionSiteIndices = {
                    TestAssets.ValidSite.UTD.ordinal(),
                    TestAssets.ValidSite.FANFICTION.ordinal(),
                    TestAssets.ValidSite.WIKIPEDIA.ordinal()
            };

            driver.findElement(By.id("ascendingButton")).click();
            validateUrlDescriptionTableOrdered(expectedAscendingDescriptionSiteIndices);
            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 21)
    public void verifyAbleToSortDescendingDescription() {

        try {
            //https://stackoverflow.com/questions/36950061/how-to-check-webelements-in-webtable-is-sorted-alphabetically-using-selenium-web

            int[] expectedDescendingDescriptionSiteIndices = {
                    TestAssets.ValidSite.WIKIPEDIA.ordinal(),
                    TestAssets.ValidSite.FANFICTION.ordinal(),
                    TestAssets.ValidSite.UTD.ordinal()
            };

            driver.findElement(By.id("descendingButton")).click();
            validateUrlDescriptionTableOrdered(expectedDescendingDescriptionSiteIndices);
            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
    }


    @Test(priority = 22)
    public void verifyAbleToGoToGivenUrl() {
        // continue from search -> already have result links
        try {
            String oldCurrentUrl = driver.getCurrentUrl();
            WebElement webElement = driver.findElement(By.id("urlNumber0"));
            String expectedNextUrl = webElement.getText().concat("/");
            webElement.click();
            String newCurrentUrl = driver.getCurrentUrl();

            assertFalse(isElementPresent(By.id("urlNumber0")));
            assertNotEquals(newCurrentUrl, oldCurrentUrl);
            assertEquals(newCurrentUrl, expectedNextUrl);

            driver.navigate().back();
            driver.findElement(By.id("backToWelcome")).click();

            testLog.pass();
        }
        catch (Throwable t) {
            handleThrowable(t);
        }
        finally {
            driver.get(baseUrl);
        }

    }


    @Test(priority = 23)
    public void verifyAbleToAccessUserManual() {
        try {
            driver.findElement(By.id("help")).click();
            String currentUrl = driver.getCurrentUrl();
            assertNotEquals(currentUrl, baseUrl);
            assertTrue(currentUrl.contains("userManual"));
            driver.navigate().back();

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


    private void goBackToWelcomeScreen() throws Throwable {
        assertTrue(isElementPresent(By.id("backToWelcome")));
        driver.findElement(By.id("backToWelcome")).click();
        assertTrue(driver.findElement(By.id("heading")).isDisplayed());
        testLog.pass();
    }


    private void validateUrlDescriptionTableUnordered(int[] expectedSiteIndices) throws Throwable {
        // find list of all urls and descriptions
        List<WebElement> actualUrls = driver.findElements(By.xpath("//*[contains(@id, 'urlNumber')]"));
        List<WebElement> actualDescriptions = driver.findElements(By.xpath("//*[contains(@id, 'descriptionNumber')]"));
        LinkedList<Integer> orderExpectedSiteIndices = new LinkedList<Integer>();

        // make sure the actual number of items is equal to the expected number of items
        assertEquals(actualUrls.size(), expectedSiteIndices.length);

        // find the order of the expected items according to the order of the actual items
        for(WebElement element : actualUrls) {
            for (int site : expectedSiteIndices) {
                if (element.getText().equals(TestAssets.VALID_ITEMS
                        [site][TestAssets.Component.URL.ordinal()])) {
                    orderExpectedSiteIndices.add(site);
                    break;
                }
            }
        }

        assertEquals(orderExpectedSiteIndices.size(), expectedSiteIndices.length);

        int currentSiteIndex = 0;
        String expectedCurrentUrl = null, expectedCurrentDescription = null;

        // check pairs
        for(int i = 0; i < actualUrls.size(); i++) {
            currentSiteIndex = orderExpectedSiteIndices.removeFirst();
            expectedCurrentUrl = TestAssets.VALID_ITEMS[currentSiteIndex][TestAssets.Component.URL.ordinal()];
            expectedCurrentDescription = TestAssets.VALID_ITEMS[currentSiteIndex][TestAssets.Component.DESCRIPTION.ordinal()];

            assertEquals(actualUrls.get(i).getText(), expectedCurrentUrl);
            assertEquals(actualDescriptions.get(i).getText(), expectedCurrentDescription);
        }
    }


    private void validateUrlDescriptionTableOrdered(int[] expectedOrderedSiteIndices) throws Throwable {
        List<WebElement> actualUrls = driver.findElements(By.xpath("//*[contains(@id, 'urlNumber')]"));
        List<WebElement> actualDescriptions = driver.findElements(By.xpath("//*[contains(@id, 'descriptionNumber')]"));
        String actualUrl = null, expectedUrl = null, actualDescription = null, expectedDescription = null;

        assertEquals(actualUrls.size(), expectedOrderedSiteIndices.length);

        for(int i = 0; i < actualUrls.size(); i++) {
            actualUrl = actualUrls.get(i).getText();
            actualDescription = actualDescriptions.get(i).getText();
            expectedUrl = TestAssets.VALID_ITEMS[expectedOrderedSiteIndices[i]]
                    [TestAssets.Component.URL.ordinal()];
            expectedDescription = TestAssets.VALID_ITEMS[expectedOrderedSiteIndices[i]]
                    [TestAssets.Component.DESCRIPTION.ordinal()];

            assertEquals(actualUrl, expectedUrl);
            assertEquals(actualDescription, expectedDescription);
        }
    }

}
