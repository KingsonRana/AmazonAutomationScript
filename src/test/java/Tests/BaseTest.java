package Tests;

import Utils.DriverManager;
import Utils.LoggerUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BaseTest {
    public static ExtentTest test;
    public static ExtentReports extent;
    public static ExtentTest parentTest;
    public String profilePath = "C:/Users/SSC/AppData/Local/Google/Chrome/User Data/";
    public String url = "https://www.amazon.in/ap/signin?openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.in%2Flog-in%2Fs%3Fk%3Dlog%2Bin%26ref_%3Dnav_signin&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=inflex&openid.mode=checkid_setup&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0";
    public static WebDriver driver;
    public static DriverManager driverManager;
    @BeforeSuite
    public void startReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("ExtentReports/SuiteReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }
    @BeforeClass
    public void setupClass() {
        // Get class name dynamically and create a parent test for that class
        String className = this.getClass().getSimpleName();
        parentTest = extent.createTest(className);
    }

    @AfterMethod
    public void captureFailure(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail(result.getThrowable());

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenshotPath = "ExtentReports/screenshots/" + result.getName() + ".png";
            File dest = new File(screenshotPath);
            try {
                FileUtils.copyFile(src, dest);
                test.addScreenCaptureFromPath(screenshotPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @AfterSuite
    public void generateReport() {
        if (driverManager!= null&&driver!=null) {
            driverManager.quit();
            driverManager=null;
            driver=null;
            LoggerUtil.info("Closed the browser");
        }
        extent.flush();
        LoggerUtil.info("Test Execution Completed. Report Generated.");
        try {
            Desktop.getDesktop().browse(new File("ExtentReports/SuiteReport.html").toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
