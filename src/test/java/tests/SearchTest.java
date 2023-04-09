package tests;

import init.BaseTest;
import org.openqa.selenium.Keys;
import org.testng.IReporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageobjects.HomeScreen;
import utils.TestUtils;

import javax.swing.*;
import java.util.Properties;


public class SearchTest extends BaseTest {

    private HomeScreen homeScreen;
    private TestUtils utils;
    private Properties props;

    public void initialiseRef() {
        homeScreen = new HomeScreen();
        utils = new TestUtils();
        props = new Properties();
    }

    @Parameters({"testData"})
    @Test(groups={"iOS Automation"})
    public void SearchWithValidData(String testData) throws InterruptedException {
        initialiseRef();
        homeScreen.searchData(testData);
        utils.scrollToBottom();
    }
}
