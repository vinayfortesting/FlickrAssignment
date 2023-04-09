package pageobjects;

import init.BaseTest;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;
import utils.TestUtils;

import java.awt.event.KeyEvent;

public class HomeScreen extends BaseTest {
   final static Logger log = LogManager.getLogger(HomeScreen.class);
   /*
   public By searchTextFBox= MobileBy.AccessibilityId("Search here...");
   public By firstElement= MobileBy.AccessibilityId("id_image_0");*/
   TestUtils utils = new TestUtils();

   @FindBy(xpath = "//XCUIElementTypeSearchField[@name=\"Search here...\"]")
   private static MobileElement txtField;

   @FindBy(xpath="//XCUIElementTypeImage[@name=\"id_image_0\"]")
   private static MobileElement firstResult;

   public void searchData(String testData)
   {
      log.info("Waiting for the search text field to appear");
      utils.waitForVisibility(txtField);

      log.info("Clearing the search text field");
      utils.clear(txtField);

      //sendKeys(txtField,testData);
      log.info("Sending "+ testData + "to search text field");
      txtField.sendKeys(testData, Keys.ENTER);
      log.info("Searched for "+ testData + " is successfull");
   }





}
