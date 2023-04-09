package init;

import com.aventstack.extentreports.Status;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;
import pageobjects.HomeScreen;
import utils.TestUtils;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

public class BaseTest {
    final static Logger log = LogManager.getLogger(BaseTest.class);
    protected static ThreadLocal <IOSDriver> driver = new ThreadLocal<IOSDriver>();
    protected static ThreadLocal <Properties> props = new ThreadLocal<Properties>();
    protected static ThreadLocal <HashMap<String, String>> strings = new ThreadLocal<HashMap<String, String>>();
    protected static ThreadLocal <String> platform = new ThreadLocal<String>();
    protected static ThreadLocal <String> deviceName = new ThreadLocal<String>();
    private static AppiumDriverLocalService server;

    public IOSDriver getDriver() {
        return driver.get();
    }

    public void setDriver(IOSDriver driver2) {
        driver.set(driver2);
    }

    public void setProps(Properties props2) {
        props.set(props2);
    }

    public  String getPlatform() {
        return platform.get();
    }
    public void setPlatform(String platform2) {
        platform.set(platform2);
    }

    public  String getDeviceName() {
        return deviceName.get();
    }
    public void setDeviceName(String deviceName2) {
        deviceName.set(deviceName2);
    }

    public BaseTest() {
        PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
    }


    @BeforeMethod
    public void  beforeMethod(Method m)
    {
        HomeScreen home = new HomeScreen();
    }

    @Parameters({"platformName", "udid", "deviceName"})
    @BeforeGroups("iOS Automation")
    public void beforeTest( String platformName, String udid, String deviceName) throws Exception {
        setPlatform(platformName);
        setDeviceName(deviceName);
        URL url;
        InputStream inputStream = null;
        InputStream stringsis = null;
        Properties props = new Properties();
        IOSDriver driver;

        String strFile = "logs" + File.separator + platformName + "_" + deviceName;
        File logFile = new File(strFile);
        if (!logFile.exists()) {
            logFile.mkdirs();
        }
        ThreadContext.put("ROUTINGKEY", strFile);

        try {
            props = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);
            setProps(props);

            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("appium:platformName", platformName);
            desiredCapabilities.setCapability("appium:deviceName", deviceName);
            desiredCapabilities.setCapability("appium:udid", udid);

            url = new URL(props.getProperty("appiumURL"));

            switch(platformName) {
                case "iOS":
                    String iOSAppUrl = props.getProperty("iOSAppLocation");
                    desiredCapabilities.setCapability("appium:automationName", props.getProperty("iOSAutomationName"));
                    desiredCapabilities.setCapability("appium:platformVersion",props.getProperty("platformVersion"));
                    desiredCapabilities.setCapability("appium:app", iOSAppUrl);
                    desiredCapabilities.setCapability("autoAcceptAlerts", "true");
                    desiredCapabilities.setCapability("appium:useNativeCachingStrategy",props.getProperty("useNativeCachingStrategy"));
                    desiredCapabilities.setCapability("newCommandTimeout", 100);
                    driver = new IOSDriver(new URL(props.getProperty("appiumURL")), desiredCapabilities);
                    log.info("Appium is started");
                    break;
                default:
                    throw new Exception("Invalid platform! - " + platformName);
            }
            setDriver(driver);
            //utils.log().info("driver initialized: " + driver);
        } catch (Exception e) {
        //    utils.log().fatal("driver initialization failure. ABORT!!!\n" + e.toString());
            throw e;
        } finally {
            if(inputStream != null) {
                inputStream.close();
            }
            if(stringsis != null) {
                stringsis.close();
            }
        }
    }

    @AfterTest (alwaysRun = true)
    public void afterTest() {
        if(getDriver() != null){
            getDriver().quit();
        }
    }
}
