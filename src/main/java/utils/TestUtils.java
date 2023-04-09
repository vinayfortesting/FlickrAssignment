package utils;

import init.BaseTest;
import io.appium.java_client.*;
import io.appium.java_client.ios.IOSDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class TestUtils extends BaseTest {

    public static final long WAIT = 40;

    public String dateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static Logger log() {
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
    }

    public void waitForVisibility(MobileElement e) {
        WebDriverWait wait = new WebDriverWait(getDriver(),TestUtils.WAIT );
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void scroll(){
        final HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("direction", "down");
        ((IOSDriver) getDriver()).executeScript("mobile: scroll", scrollObject);
    }
    public void scrollDown(){
        final HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("direction", "down");
        ((IOSDriver) getDriver()).executeScript("mobile: scroll", scrollObject);
    }

    public  void scrollToBottom()
    {
        int i =0;
        while(i<5)
        {
            scrollDown();
            i++;
        }
    }

    public void clear(MobileElement e) {
        waitForVisibility(e);
        e.clear();
    }

    public void click(MobileElement e) {
        waitForVisibility(e);
        e.click();
    }

    public void sendKeys(MobileElement e, String txt) {
        waitForVisibility(e);
        e.sendKeys(txt);
    }

    public String getAttribute(MobileElement e, String attribute) {
        waitForVisibility(e);
        return e.getAttribute(attribute);
    }

    public String getText(MobileElement e, String msg) {
        String txt = null;
        txt = getAttribute(e, "label");
        return txt;
    }

    public void iOSScrollToElement() {
        RemoteWebElement element = (RemoteWebElement)getDriver().findElement(By.name("test-ADD TO CART"));
        String elementID = element.getId();
        HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("element", elementID);
//	  scrollObject.put("direction", "down");
//	  scrollObject.put("predicateString", "label == 'ADD TO CART'");
//	  scrollObject.put("name", "test-ADD TO CART");
        scrollObject.put("toVisible", "sdfnjksdnfkld");
        getDriver().executeScript("mobile:scroll", scrollObject);
    }
}

