import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class ChallengeTest {

    private enum Browser {CHROME, FIREFOX, SAFARI, EDGE}

    private WebDriver driver;
    private WebDriverWait wait;
    private String baseUrl;
    private TestLog testLog;


    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "http://10.176.169.150/challenges";
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 15);
        driver.get(baseUrl);
    }

    @Test
    public void test() throws Throwable {
        driver.findElement(By.id("name-input")).clear();
        driver.findElement(By.id("name-input")).sendKeys("bcn140030");
        driver.findElement(By.id("password-input")).clear();
        driver.findElement(By.id("password-input")).sendKeys("99Green$");
        driver.findElement(By.id("submit")).click();

        waitForVisible(By.xpath("//button[@value='14']"));
        driver.findElement(By.xpath("//button[@value='14']")).click();
        waitForVisible(By.id("answer-input"));

        for(int i = 11; i < 750; i++) {
            driver.findElement(By.id("answer-input")).clear();
            driver.findElement(By.id("answer-input")).sendKeys(Integer.toString(i));
            driver.findElement(By.id("submit-key")).click();
//            Thread.sleep(500);
            waitForVisible(By.id("result-notification"));
            if(!driver.findElement(By.id("result-notification")).getText().equals("Incorrect")) {
                break;
            }
        }
    }



    private void waitForNotVisible(By by) throws Throwable {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }


    private void waitForVisible(By by) throws Throwable {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

}
