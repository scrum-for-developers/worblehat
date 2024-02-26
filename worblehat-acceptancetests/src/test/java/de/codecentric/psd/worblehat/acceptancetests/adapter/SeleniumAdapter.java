package de.codecentric.psd.worblehat.acceptancetests.adapter;

import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;

import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.HtmlBookList;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.psd.worblehat.acceptancetests.adapter.wrapper.PageElement;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.lifecycle.TestDescription;

/** Itegrates Selenium into the tests. */
public class SeleniumAdapter {

  @LocalServerPort private int port;

  private static final Logger LOGGER = LoggerFactory.getLogger(SeleniumAdapter.class);

  private WebDriver driver;

  private String folderName;

  public void setDriver(WebDriver driver) {
    this.driver = driver;
  }

  //    @ClassRule - not supported by Cucumber at this point
  @SuppressWarnings("rawtypes")
  public static BrowserWebDriverContainer chromeContainer =
      new BrowserWebDriverContainer<>()
          .withCapabilities(
              new ChromeOptions().addArguments("--no-sandbox", "--disable-dev-shm-usage"))
          .withRecordingMode(RECORD_ALL, new File("./target/"));

  // a class that extends thread that is to be called when program is exiting
  static final Thread afterAllThread =
      new Thread() {

        public void run() {
          chromeContainer.afterTest(
              new TestDescription() {
                @Override
                public String getTestId() {
                  return "ID";
                }

                @Override
                public String getFilesystemFriendlyName() {
                  return "Worblehat-AcceptanceTests";
                }
              },
              Optional.empty());
        }
      };

  @Before
  public void setup() {
    if (!chromeContainer.isRunning()) {
      Runtime.getRuntime().addShutdownHook(afterAllThread);
      Testcontainers.exposeHostPorts(80, 8080, 9100, 9101, port);
      chromeContainer.start();
      LOGGER.info("Connect to VNC via " + chromeContainer.getVncAddress());
      try {
        Runtime.getRuntime().exec(new String[] {"open", chromeContainer.getVncAddress()});
      } catch (IOException e) {
        e.printStackTrace();
        // silently fail, if it's not working – e.printStackTrace();
      }
    }
    setDriver(chromeContainer.getWebDriver());
  }

  @Before
  public void initSelenium() {
    folderName =
        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).concat(File.separator);
    folderName = "target" + File.separator + "screenshots" + File.separator + folderName;
    new File(folderName).mkdirs();
  }

  public void gotoPage(Page page) {
    String concreteUrl =
        "http://host.testcontainers.internal:" + port + "/worblehat/" + page.getUrl();
    driver.get(concreteUrl);
  }

  public void typeIntoField(String id, String value) {
    WebElement element = driver.findElement(By.id(id));
    element.clear();
    element.sendKeys(value);
  }

  public HtmlBookList getTableContent(PageElement pageElement) {
    WebElement table = driver.findElement(By.className(pageElement.getElementId()));
    return new HtmlBookList(table);
  }

  public void clickOnPageElement(PageElement pageElement) {
    WebElement element = driver.findElement(By.id(pageElement.getElementId()));
    element.click();
  }

  public List<String> findAllStringsForElement(PageElement pageElement) {
    List<WebElement> webElements = driver.findElements(By.className(pageElement.getElementId()));
    List<String> strings = new ArrayList<>();
    for (WebElement element : webElements) {
      strings.add(element.getText());
    }
    return strings;
  }

  @After
  public void afterAnyScenario() {
    driver.manage().deleteAllCookies();
  }

  public String getTextFromElement(PageElement pageElement) {
    WebElement element = driver.findElement(By.id(pageElement.getElementId()));
    return element.getText();
  }

  public void takeScreenShot(String filename) {

    try {
      File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      FileUtils.copyFile(scrFile, new File(folderName.concat(filename).concat(".png")));
    } catch (IOException e) {
      LOGGER.error("Could not take screenshot!", e);
    }
  }
}
