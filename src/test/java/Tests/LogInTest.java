package Tests;

import Pages.Login;
import TestData.TestDataProvider;
import Utils.DriverManager;
import Utils.LoggerUtil;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class LogInTest extends BaseTest {

    private Login loginPage;
    private DriverManager driverManager;
    private String profileDir = "Default";

    @BeforeClass
    public void setUp(){;
        driverManager = DriverManager.getInstance(profilePath,profileDir,url);
        driver = driverManager.getDriver();
        loginPage = new Login(driver);

    }

    @Test(priority = 1,dataProvider = "invalidEmails",dataProviderClass = TestDataProvider.class)
    public void invalidEmail(String invalidEmails){
        try{
        LoggerUtil.info("Invalid Email Test");
        test = parentTest.createNode("Invalid Email Test");
        boolean emailFound = loginPage.enterEmail(invalidEmails);
        if(!emailFound){
            test.fail("Email element not found");
            Assert.fail("Stopping execution: Required element not found!");
        }
        boolean error = loginPage.error("//span[contains(text(),'We cannot find an account with that email address')]");
        if(!error){
            LoggerUtil.info(error+"");
            error = loginPage.newAccount();
        }
        Assert.assertTrue(error);
        loginPage.slowDown(2);
        test.pass("Invalid Email Test Passed");
        }catch (Exception e){
            LoggerUtil.error(e.getMessage());
        }

    }
    @Test(priority = 2, dataProvider = "invalidEmailFormats",dataProviderClass = TestDataProvider.class)
    public void invalidEmailFormat(String email) {
        try {
            LoggerUtil.info("Invalid Email Format Test");
            test = parentTest.createNode("Invalid Email Format Test");
            boolean emailFound = loginPage.enterEmail(email);
            if(!emailFound){
                test.fail("Email element not found");
                Assert.fail("Stopping execution: Required element not found!");
            }
            Assert.assertTrue(loginPage.error("//div[contains(text(),'Wrong or Invalid email address or mobile phone number. Please correct and try again.')]"));
            loginPage.slowDown(2);
            test.pass("Invalid Email Format Test Passed");
        }catch (Exception e){
            LoggerUtil.error(e.getMessage());
        }
    }
    @Test(priority = 3)
    public void emptyEmail() {
        try {
            LoggerUtil.info("Empty Email Test");
            test = parentTest.createNode("Empty Email Test");
            boolean emailFound = loginPage.enterEmail("");
            if(!emailFound){
                test.fail("Email element not found");
                Assert.fail("Stopping execution: Required element not found!");

            }
            Assert.assertTrue(loginPage.error("//div[contains(text(),'Enter your email or mobile phone number')]"));
            test.pass("Empty Email Test");
        }catch (Exception e){
            LoggerUtil.error(e.getMessage());
        }
    }

    @Test(priority = 4,dataProvider = "validCredential",dataProviderClass = TestDataProvider.class)
    public void validEmail(String validEmail, String validPassword) {
        try {
            LoggerUtil.info("Valid Email Test");
            test = parentTest.createNode("Valid Email Test");
            boolean emailFound = loginPage.enterEmail(validEmail);
            if(!emailFound){
                test.fail("Email element not found");
                Assert.fail("Stopping execution: Required element not found!");
            }
            Assert.assertFalse(loginPage.error("//span[contains(text(),'We cannot find an account with that email address')]"));
            loginPage.slowDown(2);
            test.pass("Valid Email Test");
        }catch (Exception e){
            LoggerUtil.error(e.getMessage());
        }
    }
    @Test(priority = 5,dataProvider = "invalidPassword",dataProviderClass = TestDataProvider.class)
    public void inValidPassword(String password){
        try {
            LoggerUtil.info("InValid Password Test");
            test = parentTest.createNode("InValid Password Test");
            boolean passwordFound = loginPage.enterPassword(password);
            if(!passwordFound){
                test.fail("Element not found");
                Assert.fail("Stopping execution: Required element not found!");
            }
            loginPage.solveCaptcha("Solve this puzzle to protect your account", 0);
            Assert.assertTrue(loginPage.error("//span[contains(text(),'Your password is incorrect')]"));
            loginPage.slowDown(3);
            test.pass("InValid Password Test");
        }catch (Exception e){
            LoggerUtil.error(e.getMessage());
        }
    }
    @Test(priority = 6)
    public void emptyPassword() {
        try {
            LoggerUtil.info("Empty Password Test");
            test = parentTest.createNode("Empty Password Test");
            boolean passwordFound = loginPage.enterPassword("");
            if(!passwordFound){
                test.fail("Element not found");
                Assert.fail("Stopping execution: Required element not found!");
            }
            loginPage.solveCaptcha("Solve this puzzle to protect your account", 0);
            Assert.assertTrue(loginPage.error("//div[contains(text(),'Enter your password')]"));
            test.pass("Empty Password Test");
        }catch (Exception e){
            LoggerUtil.error(e.getMessage());
        }
    }
//    @Test(priority = 7,dependsOnMethods = "validEmail")
//    public void accountLockTest() {
//        try {
//            LoggerUtil.info("Testing Account Lock after multiple failed attempts");
//            test = extent.createTest("Testing Account Lock after multiple failed attempts");
//            int attempt = 0;
//            while (attempt < 5 && !loginPage.error("//h1[contains(text(),'Password assistance')]")) {
//                loginPage.enterPassword("WrongPassword123");
//                loginPage.solveCaptcha("//span[contains(text(),'Solve this puzzle to protect your account')]", 0);
//                attempt++;
//            }
//            SoftAssert softAssert = new SoftAssert();
//            softAssert.assertTrue(loginPage.error("//h1[contains(text(),'Password assistance')]"));
//            LoggerUtil.info("Account is locked. Restarting the flow...");
//            test.pass("Testing Account Lock after multiple failed attempts");
//        }catch (Exception e){
//            LoggerUtil.error(e.getMessage());
//        }
//    }
    @Test(priority = 7,dataProvider = "validCredential",dataProviderClass = TestDataProvider.class)
    public void restartAndValidPasswordTest(String email, String password) {
        try {
            LoggerUtil.info("Restarting flow after account lock...");
            test = parentTest.createNode("Valid Input Data Test");
            driverManager.quit();
            driverManager = null;
            setUp();
            boolean emailFound = loginPage.enterEmail(email);
            boolean passwordFound =loginPage.enterPassword(password);
            if(!emailFound||!passwordFound){
                test.fail("Element not found");
                Assert.fail("Stopping execution: Required element not found!");
            }
            loginPage.solveCaptcha("Solve this puzzle to protect your account", 0);
            Assert.assertFalse(loginPage.error("//span[contains(text(),'Your password is incorrect')]"));
            test.pass("Valid Input Data Test");
        }catch (Exception e){
            LoggerUtil.error(e.getMessage());
        }
    }

    @AfterClass
    public void tearDown(){
        if (driverManager != null) {
            driverManager.quit();
            driverManager=null;
            driver=null;
            LoggerUtil.info("Closed the browser");
        }
    }

}
