package com.example.tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class ModifiabilityTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:63342/port-folio/index.html?_ijt=h94p4g2neorm5get593pbkobjf";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testModifiability() throws Exception {
    driver.get(baseUrl + "/Ciberminer/gui/index.html");
    driver.findElement(By.id("addUrlButton")).click();
    driver.findElement(By.id("urlInput")).clear();
    driver.findElement(By.id("urlInput")).sendKeys("njnjnjnjnjn");
    driver.findElement(By.id("descriptionInput")).clear();
    driver.findElement(By.id("descriptionInput")).sendKeys("njnjnjnjnjnjnjnj");
    driver.findElement(By.id("addUrlSubmitButton")).click();
    try {
      assertFalse(driver.findElement(By.id("invalidUrlMessage")).isDisplayed());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    // test delete
    try {
      assertNotEquals("This is google website", driver.findElement(By.id("description0")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertNotEquals("www.google.com", driver.findElement(By.id("url0")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertNotEquals("www.wikipedia.org", driver.findElement(By.id("url1")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    try {
      assertNotEquals("Wikipedia", driver.findElement(By.id("description1")).getText());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    String descriptionToDelete = driver.findElement(By.id("description0")).getText();
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
