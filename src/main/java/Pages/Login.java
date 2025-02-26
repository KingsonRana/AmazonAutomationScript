package Pages;

import Utils.LoggerUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Login {
    WebDriver driver;
    WebDriverWait wait;
    @FindBy(xpath = "//input[contains(@name, 'email') and contains(@id, 'ap_email') ]")
    private WebElement email;

    @FindBy(xpath = "//input[contains(@name, 'password') and contains(@id, 'ap_password') ]")
    private WebElement password;

    @FindBy(id="continue")
    private WebElement continueBtn;

    @FindBy(id="signInSubmit")
    private WebElement submitButton;


    public Login(WebDriver driver){
        try {
            this.driver = driver;
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            PageFactory.initElements(driver, this);
        }catch (Exception e){
            LoggerUtil.error(e.getMessage());
        }
    }

    public boolean enterEmail(String emailId){
        try {
            LoggerUtil.info("Entering username: " + emailId);
            email.clear();
            email.sendKeys(emailId);
            LoggerUtil.info("Clicking continue button");
            continueBtn.click();
            return true;
        }catch (Exception e){
            LoggerUtil.error(e.getMessage());
            return false;
        }
    }
    public boolean enterPassword(String userPassword){
        try {
            LoggerUtil.info("Entering password: " + userPassword);
            password.clear();
            password.sendKeys(userPassword);
            submitButton.click();
            return true;
        }catch (Exception e){
            LoggerUtil.error(e.getMessage());
            return false;
        }
    }
    public boolean error(String elementPath){
        WebElement errorMessage;
        try{
            System.out.println(elementPath);
             errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementPath)));
            LoggerUtil.info(elementPath);

        }catch(Exception e){
           LoggerUtil.error(e.getMessage());
           return false;
        }
        return errorMessage.isDisplayed();
    }
    public boolean newAccount(){
        WebElement elm;
        try {
            LoggerUtil.info("Called new account check");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Looks like you are new to Amazon')]")));
             elm = driver.findElement(By.xpath("//h1[contains(text(),'Looks like you are new to Amazon')]"));
            if (elm.isDisplayed()) {
                driver.navigate().back();
                return true;
            }
        }catch (Exception e){
            LoggerUtil.error(e.getMessage());
            return false;
        }
        return false;
    }


    public void slowDown(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
  private int count = 0;
    public void solveCaptcha(String text , int attempt){
        if(attempt==3) {
            return;
        }
        if(error(text)){
           slowDown(10);
            System.out.println(++count);
           solveCaptcha(text,++attempt);
        }

    }




}
