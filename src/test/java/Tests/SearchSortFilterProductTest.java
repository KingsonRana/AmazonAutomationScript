package Tests;

import Pages.SearchSortFilterProduct;
import Utils.DriverManager;
import Utils.LoggerUtil;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import TestData.TestDataProvider;
import org.testng.asserts.SoftAssert;

public class SearchSortFilterProductTest extends BaseTest {
    private String profileDir = "Profile 1";
    private SearchSortFilterProduct dashboard;

    @BeforeClass
    public void setUp() {
        driverManager = DriverManager.getInstance(profilePath, profileDir, url);
        driver = driverManager.getDriver();
        dashboard = new SearchSortFilterProduct(driver);
    }

    @Test(priority = 1, dataProvider = "validCredentials",dataProviderClass = TestDataProvider.class)
    public void logIn(String email, String password) {
        try {
            LoggerUtil.info("Logging in with Email : " + email + " and Password : " + password);
            test = parentTest.createNode("Valid Login");
            dashboard.enterEmail(email);
            dashboard.enterPassword(password);
            Assert.assertFalse(dashboard.verifyPresence("//span[contains(text(),'Hello, sign in')]"));
            test.pass("Log in successful");
        }catch (Exception e){
            LoggerUtil.error(e.getMessage());
        }

    }
    @Test(priority = 2,dataProvider = "Products",dependsOnMethods = "logIn",dataProviderClass = TestDataProvider.class)
    public void search(String[] product){
        try {
            test = parentTest.createNode("Search for product ");
            LoggerUtil.info("Search for product " + product[0]);
            Assert.assertTrue(dashboard.search(product));
            test.pass("Search passed successfully");
        }catch (Exception e){
            LoggerUtil.error(e.getMessage());
        }
    }

    @Test(priority = 3, dependsOnMethods = "search",dataProvider = "sortBy",dataProviderClass = TestDataProvider.class)
    public void sortBy(String[] sortBy) {
        try{
        test = parentTest.createNode("Sorting by" + sortBy[0]);
        LoggerUtil.info("Sorting by " + sortBy[0]);
        Assert.assertTrue(dashboard.sortBy(sortBy));
        test.pass("Sorting by " + sortBy[0] + "successful");
    }catch (Exception e){
        LoggerUtil.error(e.getMessage());
    }
    }

    @Test(priority = 4, dependsOnMethods = "logIn",dataProvider = "filterBy",dataProviderClass = TestDataProvider.class)
    public void filterBy(String filter, String value ,String filterLocator, String filterOutputLocator ) {

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
