package Utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.logging.Logger;

public class DriverManager {
    private WebDriver driver;
    private ChromeOptions options;
    private static DriverManager instance;
    private DriverManager() {
        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();
        driver = new ChromeDriver(options);
    }

    private DriverManager(String profilePath, String profile, String address) {
        WebDriverManager.chromedriver().setup();
        options = new ChromeOptions();
        options.addArguments("--user-data-dir=" + profilePath);
        options.addArguments("--profile-directory=" + profile);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(address);

    }

    // Singleton method to get the instance
    public static  DriverManager getInstance() {
        if (instance == null) {
            instance = new DriverManager();
        }
        return instance;
    }

    public static  DriverManager getInstance(String profilePath, String profile, String address) {
        if (instance == null) {
            LoggerUtil.debug("Creating new instance");
            instance = new DriverManager(profilePath, profile, address);
        }
        return instance;
    }

    public WebDriver getDriver(){
        if(driver==null){
            return null;
        }
        return driver;
    }

    public void quit() {
        LoggerUtil.debug("Quit called");
        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.quit();
            driver = null;
            instance = null;
        }
    }
}
