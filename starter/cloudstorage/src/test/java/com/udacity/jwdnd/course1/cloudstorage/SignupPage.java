package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SignupPage {
    @FindBy(id = "inputFirstName")
    private WebElement firstname;

    @FindBy(id = "inputLastName")
    private WebElement lastname;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "signup-button")
    private WebElement signupButton;

    @FindBy(id = "login-link")
    private WebElement loginLink;

    private final WebDriver driver;

    private void waitForElement(WebElement element){
        new WebDriverWait(driver, 100).until(ExpectedConditions.visibilityOf(element));
    }

    private void waitForTitle(String title){
        new WebDriverWait(driver, 100).until(ExpectedConditions.titleContains(title));
    }

    private void waitForButtonClick(WebElement element){
        new WebDriverWait(driver, 100).until(ExpectedConditions.elementToBeClickable(element));
        System.out.println(element.getText() + " is clickable");
        element.click();
    }

    public void isDisplayed(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 90);
            List<WebElement> cccc = driver.findElements(By.xpath("//div[contains(@id,'loadmask')]"));
            boolean pageload = wait.until(ExpectedConditions.invisibilityOfAllElements(cccc));
            // boolean pageload=
            // wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@id,'loadmask-')]")));
            if (pageload) {
                wait.until(ExpectedConditions.elementToBeClickable(element));
            }
        }
        catch (org.openqa.selenium.NoSuchElementException exception) { }
    }


    private void waitForElementSendKeys(WebElement element, String key){
        waitForElement(element);
        element.sendKeys(key);
    }

    public SignupPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void signup(String  inputFirstname, String inputLastname, String inputUsername, String inputPassword) {
        waitForElementSendKeys(firstname, inputFirstname);
        waitForElementSendKeys(lastname,  inputLastname);
        waitForElementSendKeys(username,  inputUsername);
        waitForElementSendKeys(password, inputPassword);
        waitForButtonClick(signupButton);

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //waitForButtonClick(loginLink);
        loginLink.click();
        waitForTitle("Login");
    }
}
