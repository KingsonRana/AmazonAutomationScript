package Pages;

import Utils.LoggerUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SearchSortFilterProduct {
    private WebDriver driver;
    private WebDriverWait wait;
    @FindBy(xpath = "//input[contains(@name, 'email') and contains(@id, 'ap_email') ]")
    private WebElement email;

    @FindBy(xpath = "//input[contains(@name, 'password') and contains(@id, 'ap_password') ]")
    private WebElement password;

    @FindBy(id = "continue")
    private WebElement continueBtn;
    @FindBy(id = "signInSubmit")
    private WebElement submitButton;
    @FindBy(xpath = "//input[contains(@id,'twotabsearchtextbox') and contains(@placeholder,'Search Amazon.in')]")
    private WebElement searchBar;

    @FindBy(xpath = "//select[contains(@id,'s-result-sort-select') and contains(@name,'s')]")
    private WebElement selectBtn;

    public SearchSortFilterProduct(WebDriver driver) {
        try {
            this.driver = driver;
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            PageFactory.initElements(driver, this);
        }catch (Exception e){
            LoggerUtil.error(e.getMessage());
        }

    }

    public void enterEmail(String emailId) {
        try {
            LoggerUtil.info("Entering username: " + emailId);
            email.clear();
            email.sendKeys(emailId);
            LoggerUtil.info("Clicking continue button");
            slowDown(1);
            continueBtn.click();
            slowDown(1);
        }catch (Exception e){
            LoggerUtil.error(e.getMessage());
        }
    }

    public void enterPassword(String userPassword) {
        try {
            LoggerUtil.info("Entering password: " + userPassword);
            password.clear();
            password.sendKeys(userPassword);
            slowDown(1);
            submitButton.click();
            slowDown(1);
        }catch (Exception e){
            LoggerUtil.error(e.getMessage());
        }
    }

    public boolean verifyPresence(String locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean search(String[] keyword) {
        try{
        wait.until(ExpectedConditions.visibilityOf(searchBar));
        searchBar.clear();
        String s = String.join(" ", keyword);
        searchBar.sendKeys(s);
        searchBar.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Results')]")));
        StringBuilder path = new StringBuilder("//h2[");
        for (int i = 0; i < keyword.length; i++) {
            if (i > 0) {
                path.append(" or ");
            }
            path.append("contains(@aria-label,'").append(keyword[i]).append("')");
        }
        path.append("]");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(path.toString())));
        List<WebElement> result = driver.findElements(By.xpath(path.toString()));
        LoggerUtil.info("Found " + result.size() + " results");
        LoggerUtil.info("XPath used: " + path);

        if (result.isEmpty()) {
            return false;
        }

        for (WebElement element : result) {
            String elementText = element.findElement(By.tagName("span")).getText().toLowerCase();
            LoggerUtil.info("Checking element text: " + elementText);
            boolean found = false;
            for (String str : keyword) {
                if (elementText.contains(str.toLowerCase())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }

        }}catch (Exception e){
            LoggerUtil.error(e.getMessage());
        }
        return true;
    }

    public boolean sortBy(String[] order) {
        List<WebElement> resultList = new ArrayList<>();

        try {
            wait.until(ExpectedConditions.elementToBeClickable(selectBtn));
            Select select = new Select(selectBtn);
            List<WebElement> options = select.getOptions();
            int index = 0;
            for (WebElement option : options) {
                if (option.getText().equals(order[0])) {
                    break;
                }
                index++;
            }
            select.selectByIndex(index);
            StringBuilder path = new StringBuilder("//div[contains(@data-cy, 'price-recipe')]");
            path.append(order[2]);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path.toString())));
            resultList = driver.findElements(By.xpath(path.toString()));
            LoggerUtil.info("Path " + path);

        }catch (Exception e){
            LoggerUtil.error(e.getMessage());
        }
        return isSorted(resultList, Integer.parseInt(order[1]));
    }

    public boolean isSorted(List<WebElement> resultList, int order) {
        boolean isSorted = true;
        try {
            List<Integer> priceList = new ArrayList<>();

            LoggerUtil.info("number of items" + resultList.size());

            for (WebElement result : resultList) {
                priceList.add(Integer.parseInt(result.getText().replaceAll("[^0-9]", "")));
                LoggerUtil.info(priceList.get(priceList.size() - 1) + "");
            }


            if (order == 0) {
                isSorted = IntStream.range(0, priceList.size() - 1)
                        .allMatch(i -> priceList.get(i) <= priceList.get(i + 1));
            } else if (order == 1) {
                isSorted = IntStream.range(0, priceList.size() - 1)
                        .allMatch(i -> priceList.get(i) >= priceList.get(i + 1));
            } else {
                throw new RuntimeException("Order can either be 0 or 1");
            }

        }catch (Exception e){
            LoggerUtil.error(e.getMessage());
        }
        return isSorted;
    }

    public void slowDown(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
