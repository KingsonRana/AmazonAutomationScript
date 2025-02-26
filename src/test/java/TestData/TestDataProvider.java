package TestData;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider(name = "validCredentials")
    public static Object[][] credentials() {
        return new Object[][]{
                {"validCred1@gmail.com", "validPassword"}
        };
    }

    @DataProvider(name = "Products")
    public static Object[][] products(){
        return new Object[][]{
                {new String[]{"Samsung", "S25"}}
        };
    }

    @DataProvider(name = "sortBy")
    public static Object[][] sort(){
        return new Object[][]{
                {new String[]{"Price: Low to High","0","//span[@class='a-price-whole']"}},
                {new String[]{"Price: High to Low","1","//span[@class='a-price-whole']"}}
        };
    }

    @DataProvider(name = "filterBy")
    public static Object[][] filters(){
        return new Object[][]{
                {"Memory","128","//ul[@id='filter-p_n_feature_twenty-nine_browse-bin']","//span[contains(text(),'128GB']"},
                {"Discount","10% Off or more","//ul[@id='filter-p_n_pct-off-with-tax']","//span[contains(text(),'% Off')]"},
                {"RAM","10 GB & Above","//ul[@id='filter-p_n_feature_thirty_browse-bin']","//span[contains(text(),'GB RAM']"}
        };
    }
    @DataProvider(name = "invalidEmails")
    public static Object[][] invalidEmails() {
        return new Object[][] {
                {"invali12347edshb@gmail.com"},
                {"test@wrong.com"},
                {"wrong@domain.com"}
        };
    }
    @DataProvider(name = "validCredential")
    public static Object[][] validCredential() {
        return new Object[][] {
                {"validCred@gmail.com", "gffds33453"},
        };
    }
    @DataProvider(name = "invalidPassword")
    public static Object[][] invalidPassowrds() {
        return new Object[][] {
                {"test@gsdfnd"},
        };
    }
    @DataProvider(name = "invalidEmailFormats")
    public static Object[][] invalidEmailFormats() {
        return new Object[][] {
                {"testemail.com"},
                {"test@.com"},
                {"test@#$.com"},
                {"test @email.com"}
        };
    }
}
