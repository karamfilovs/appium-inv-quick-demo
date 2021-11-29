import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class InvTest {
    public static final Logger LOGGER = LoggerFactory.getLogger(InvTest.class);
    private AppiumDriver driver;


    @BeforeEach
    public void beforeEach() throws MalformedURLException {
//        AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder()
//                .usingAnyFreePort()
//                .withArgument(GeneralServerFlag.LOG_LEVEL, "info");
//        appiumServiceBuilder.withArgument(GeneralServerFlag.RELAXED_SECURITY);
//        AppiumDriverLocalService service = appiumServiceBuilder.build();
//        service.start();
        LOGGER.info("Starting service");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.APPLICATION_NAME, "Xiaomi Mi9");
        capabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11");
        capabilities.setCapability("appPackage", "com.invbg");
        capabilities.setCapability("appActivity", "com.invbg.MainActivity");
        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AndroidDriver(url, capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    @DisplayName("Can login and logout with valid credentials")
    public void canLoginInMobileApplication() {
        LOGGER.info("Navigate to Login page");
        WebElement loginInitiateButton = driver.findElement(By.xpath("//*[@text ='ВХОД']"));
        LOGGER.info("Click Login button");
        loginInitiateButton.click();
        List<MobileElement> inputElements = driver.findElements(By.xpath("//android.widget.EditText"));
        LOGGER.info("Input elements found:" + inputElements.size());
        LOGGER.info("Entering domain");
        inputElements.get(1).sendKeys("st2016");
        LOGGER.info("Entering email");
        inputElements.get(3).sendKeys("karamfilovs@gmail.com");
        LOGGER.info("Entering password");
        inputElements.get(4).sendKeys("123456");
        LOGGER.info("Clicking Login button");
        WebElement loginButton = driver.findElement(By.xpath("//android.widget.TextView[@text ='ВХОД']"));
        loginButton.click();
        LOGGER.info("Clicking More button");
        WebElement moreButton = driver.findElement(By.xpath("//*[@text ='Още']"));
        moreButton.click();
        LOGGER.info("Clicking Logout button");
        WebElement logoutButton = driver.findElement(By.xpath("//android.widget.TextView[@text ='Изход']"));
        logoutButton.click();
        //Check that the user is logged out
        WebElement forgottenPasswordLink = driver.findElement(By.xpath("//android.widget.ViewGroup[contains(@text, 'РЕГИ')]"));
        Assertions.assertEquals("ЗАБРАВЕНА ПАРО", forgottenPasswordLink.getText().trim());
    }

    @Test
    @DisplayName("Cant login with invalid password")
    public void cantLoginInMobileApplicationWithInvalidPassword() {
        LOGGER.info("Navigate to Login page");
        WebElement loginInitiateButton = driver.findElement(By.xpath("//*[@text ='ВХОД']"));
        LOGGER.info("Click Login button");
        loginInitiateButton.click();
        List<MobileElement> inputElements = driver.findElements(By.xpath("//android.widget.EditText"));
        LOGGER.info("Input elements found:" + inputElements.size());
        LOGGER.info("Entering domain");
        inputElements.get(1).sendKeys("st2016");
        LOGGER.info("Entering email");
        inputElements.get(3).sendKeys("karamfilovs@gmail.com");
        LOGGER.info("Entering password");
        inputElements.get(4).sendKeys("12345678");
        LOGGER.info("Clicking Login button");
        WebElement loginButton = driver.findElement(By.xpath("//android.widget.TextView[@text ='ВХОД']"));
        loginButton.click();
        WebElement errorMessage = driver.findElement(By.xpath("//android.widget.TextView[contains(@text, 'Адресът')]"));
        Assertions.assertEquals("Адресът, имейлът или паролата Ви са грешни.", errorMessage.getText().trim());
    }
}
